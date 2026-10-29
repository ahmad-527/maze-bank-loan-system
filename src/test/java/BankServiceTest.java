import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

class BankServiceTest {
  @TempDir Path directory;
  Database database;
  BankService bank;

  @BeforeEach
  void setup() {
    Session.clear();
    database = new Database(directory);
    bank = new BankService(database);
  }

  @AfterEach
  void logout() {
    Session.clear();
  }

  void customer() {
    Session.start(
        bank.authenticate("demo-user", "DemoUser!2026".toCharArray(), Session.Role.CUSTOMER));
  }

  void admin() {
    Session.start(
        bank.authenticate("demo-admin", "DemoAdmin!2026".toCharArray(), Session.Role.ADMIN));
  }

  void register(String user) {
    bank.register(
        user,
        "+968 00000000",
        "DEMO-1234",
        "A demo password!".toCharArray(),
        "A demo password!".toCharArray(),
        true);
  }

  int count(String table) throws Exception {
    assert Set.of("users", "loan_applications", "questions").contains(table);
    try (Connection c = database.open();
        Statement s = c.createStatement();
        ResultSet r = s.executeQuery("SELECT COUNT(*) FROM " + table)) {
      r.next();
      return r.getInt(1);
    }
  }

  @Test
  void seededAccountsAuthenticateToCorrectRoleAndDoNotDuplicateOnReopen() throws Exception {
    customer();
    assertEquals(1, bank.loans().size());
    assertEquals(1, bank.queries().size());
    assertThrows(
        IllegalArgumentException.class,
        () -> bank.authenticate("demo-user", "DemoUser!2026".toCharArray(), Session.Role.ADMIN));
    admin();
    assertEquals(1, bank.loans().size());
    new Database(directory);
    assertEquals(2, count("users"));
    assertEquals(1, count("loan_applications"));
  }

  @Test
  void registrationChecksConfirmationAcknowledgmentAndFictionalReference() throws Exception {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            bank.register(
                "alice",
                "00000000",
                "DEMO-1234",
                "A demo password!".toCharArray(),
                "Different password!".toCharArray(),
                true));
    assertThrows(
        IllegalArgumentException.class,
        () ->
            bank.register(
                "alice",
                "00000000",
                "DEMO-1234",
                "A demo password!".toCharArray(),
                "A demo password!".toCharArray(),
                false));
    assertThrows(
        IllegalArgumentException.class,
        () ->
            bank.register(
                "alice",
                "00000000",
                "1234567890123456",
                "A demo password!".toCharArray(),
                "A demo password!".toCharArray(),
                true));
    assertEquals(2, count("users"));
    register("alice");
    assertEquals(3, count("users"));
  }

  @Test
  void registrationNormalisesUserIdAndDuplicateCannotOverwritePassword() {
    register("Alice");
    assertThrows(IllegalArgumentException.class, () -> register("ALICE"));
    assertEquals(
        "alice",
        bank.authenticate(" ALICE ", "A demo password!".toCharArray(), Session.Role.CUSTOMER)
            .username());
  }

  @Test
  void passwordHashesAreSaltedAndPlaintextIsNeverStored() throws Exception {
    register("alice");
    register("bob");
    List<String> hashes = new ArrayList<>();
    try (Connection c = database.open();
        PreparedStatement q =
            c.prepareStatement("SELECT password_hash FROM users WHERE username IN (?,?)")) {
      q.setString(1, "alice");
      q.setString(2, "bob");
      try (ResultSet r = q.executeQuery()) {
        while (r.next()) hashes.add(r.getString(1));
      }
    }
    assertEquals(2, hashes.size());
    assertNotEquals(hashes.get(0), hashes.get(1));
    for (String hash : hashes) {
      assertFalse(hash.contains("A demo password!"));
      assertTrue(PasswordHasher.verify("A demo password!".toCharArray(), hash));
      assertFalse(PasswordHasher.verify("Wrong password!".toCharArray(), hash));
    }
  }

  @Test
  void loginRejectsSqlInjectionAndClearsSuppliedPasswordArray() {
    char[] password = "DemoUser!2026".toCharArray();
    assertThrows(
        IllegalArgumentException.class,
        () -> bank.authenticate("' OR '1'='1", password, Session.Role.CUSTOMER));
    assertArrayEquals(new char[password.length], password);
  }

  @Test
  void malformedHashesNeverAuthenticate() {
    for (String value :
        Arrays.asList(
            null,
            "",
            "plain-password",
            "pbkdf2-sha256$1$bad$bad",
            "pbkdf2-sha256$999999999$bad$bad",
            "pbkdf2-sha256$600000$not-base64$bad"))
      assertFalse(PasswordHasher.verify("DemoUser!2026".toCharArray(), value));
  }

  @Test
  void unauthenticatedAccessAndSelfGrantedAdminAreRejected() throws Exception {
    assertThrows(SecurityException.class, () -> bank.loans());
    assertThrows(SecurityException.class, () -> bank.queries());
    assertThrows(SecurityException.class, () -> bank.submitQuery("A fictional query"));
    assertThrows(
        SecurityException.class,
        () ->
            bank.submitLoan(
                "Demo", "demo@example.invalid", "10", "Personal loan", 12, "Fictional"));
    char[] password = "A demo password!".toCharArray();
    assertThrows(
        SecurityException.class,
        () ->
            bank.registerAdmin(
                "intruder",
                "00000000",
                "DEMO-STAFF",
                password,
                "A demo password!".toCharArray(),
                true));
    assertArrayEquals(new char[password.length], password);
    assertEquals(2, count("users"));
  }

  @Test
  void customerCannotCreateAdminAndAdminCannotSubmitAsCustomer() {
    customer();
    assertThrows(
        SecurityException.class,
        () ->
            bank.registerAdmin(
                "new-admin",
                "00000000",
                "DEMO-STAFF",
                "A demo password!".toCharArray(),
                "A demo password!".toCharArray(),
                true));
    admin();
    assertThrows(SecurityException.class, () -> bank.submitQuery("Do not impersonate a customer"));
    assertThrows(
        SecurityException.class,
        () ->
            bank.submitLoan(
                "Demo", "demo@example.invalid", "10", "Personal loan", 12, "Fictional"));
  }

  @Test
  void authenticatedAdminCanCreateAnotherDemoAdmin() {
    admin();
    bank.registerAdmin(
        "new-admin",
        "00000000",
        "DEMO-STAFF2",
        "A demo password!".toCharArray(),
        "A demo password!".toCharArray(),
        true);
    assertEquals(
        Session.Role.ADMIN,
        bank.authenticate("new-admin", "A demo password!".toCharArray(), Session.Role.ADMIN)
            .role());
    assertThrows(
        IllegalArgumentException.class,
        () ->
            bank.authenticate(
                "new-admin", "A demo password!".toCharArray(), Session.Role.CUSTOMER));
  }

  @Test
  void apostrophesAndSqlTextAreSavedAsLiteralValues() throws Exception {
    customer();
    String purpose = "Client's course demo'); DROP TABLE users; --";
    long id =
        bank.submitLoan(
            "O'Neil", "demo+test@example.invalid", "12.345", "Personal loan", 12, purpose);
    Object[] row = bank.loans().stream().filter(x -> x[0].equals(id)).findFirst().orElseThrow();
    assertEquals("O'Neil", row[2]);
    assertEquals(new BigDecimal("12.345"), new BigDecimal(row[4].toString()));
    assertEquals(purpose, row[7]);
    assertEquals(2, count("users"));
    bank.submitQuery(purpose);
    assertEquals(purpose, bank.queries().get(0)[2]);
  }

  @Test
  void invalidAmountTypeAndTermDoNotCreateRows() throws Exception {
    customer();
    int before = count("loan_applications");
    for (String amount : new String[] {"0", "-1", "NaN", "1.2345", "1000001", "1e100", ""})
      assertThrows(
          IllegalArgumentException.class,
          () ->
              bank.submitLoan(
                  "Demo", "demo@example.invalid", amount, "Personal loan", 12, "Fictional"));
    assertThrows(
        IllegalArgumentException.class,
        () ->
            bank.submitLoan(
                "Demo", "demo@example.invalid", "100", "Unsupported loan", 12, "Fictional"));
    assertThrows(
        IllegalArgumentException.class,
        () ->
            bank.submitLoan(
                "Demo", "demo@example.invalid", "100", "Personal loan", 7, "Fictional"));
    assertEquals(before, count("loan_applications"));
  }

  @Test
  void requiredAndOverlongFieldsDoNotCreateRows() throws Exception {
    customer();
    int before = count("loan_applications");
    assertThrows(
        IllegalArgumentException.class,
        () ->
            bank.submitLoan(" ", "demo@example.invalid", "100", "Personal loan", 12, "Fictional"));
    assertThrows(
        IllegalArgumentException.class,
        () -> bank.submitLoan("Demo", "not-an-email", "100", "Personal loan", 12, "Fictional"));
    assertThrows(
        IllegalArgumentException.class,
        () ->
            bank.submitLoan(
                "Demo", "demo@example.invalid", "100", "Personal loan", 12, "x".repeat(2001)));
    assertThrows(IllegalArgumentException.class, () -> bank.submitQuery(" "));
    assertEquals(before, count("loan_applications"));
  }

  @Test
  void customersSeeOnlyOwnRecordsWhileAdminSeesAll() {
    for (String name : new String[] {"alice", "bob"}) {
      register(name);
      Session.start(
          bank.authenticate(name, "A demo password!".toCharArray(), Session.Role.CUSTOMER));
      bank.submitLoan(name, "demo@example.invalid", "100", "Auto loan", 24, "Fictional " + name);
      bank.submitQuery("Fictional " + name);
      assertEquals(1, bank.loans().size());
      assertEquals(name, bank.loans().get(0)[1]);
      assertEquals(1, bank.queries().size());
    }
    admin();
    assertEquals(3, bank.loans().size());
    assertEquals(3, bank.queries().size());
  }

  @Test
  void logoutInvalidatesPriorIdentityAndFutureReads() {
    customer();
    Session.Principal first = Session.require();
    Session.clear();
    assertFalse(Session.isCurrent(first));
    assertThrows(SecurityException.class, () -> bank.loans());
    customer();
    assertFalse(Session.isCurrent(first));
  }

  @Test
  void savedRecordsSurviveFreshDatabaseAndServiceInstances() {
    customer();
    long id =
        bank.submitLoan(
            "Demo",
            "demo@example.invalid",
            "250.125",
            "Investment loan",
            48,
            "Fictional persistence check");
    BankService reopened = new BankService(new Database(directory));
    assertTrue(
        reopened.loans().stream().anyMatch(row -> row[0].equals(id) && row[4].equals("250.125")));
  }
}
