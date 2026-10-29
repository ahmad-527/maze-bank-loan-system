import java.math.BigDecimal;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;

/** Validated persistence and role boundaries for the educational local workflow. */
final class BankService {
  static final List<String> TYPES =
      List.of("Personal loan", "Auto loan", "Mortgage loan", "Investment loan");
  static final List<Integer> TERMS = List.of(6, 12, 18, 24, 30, 36, 42, 48);
  private final Database database;
  private static volatile BankService instance;

  BankService(Database database) {
    this.database = database;
  }

  static BankService get() {
    if (instance == null)
      synchronized (BankService.class) {
        if (instance == null) {
          String configured = System.getenv("MAZEBANK_DATA_DIR");
          Path directory =
              configured == null || configured.isBlank()
                  ? Path.of(System.getProperty("user.home"), ".maze-bank-demo")
                  : Path.of(configured);
          instance = new BankService(new Database(directory));
        }
      }
    return instance;
  }

  // Test fixture injection is package-private and never changes the user's default directory.
  static void useForTests(BankService service) {
    instance = service;
  }

  Session.Principal authenticate(String username, char[] password, Session.Role expectedRole) {
    try {
      String user = username == null ? "" : username.strip().toLowerCase(Locale.ROOT);
      if (user.length() > 30 || password == null || password.length < 1 || password.length > 128)
        throw new IllegalArgumentException("Invalid username or password.");
      try (Connection connection = database.open();
          PreparedStatement query =
              connection.prepareStatement(
                  "SELECT id,username,password_hash,role FROM users WHERE username=?")) {
        query.setString(1, user);
        try (ResultSet result = query.executeQuery()) {
          if (!result.next()
              || !PasswordHasher.verify(password, result.getString("password_hash"))
              || !result.getString("role").equals(expectedRole.name()))
            throw new IllegalArgumentException("Invalid username or password for this login.");
          return new Session.Principal(
              result.getLong("id"), result.getString("username"), expectedRole);
        }
      } catch (SQLException failure) {
        throw unavailable(failure);
      }
    } finally {
      if (password != null) Arrays.fill(password, '\0');
    }
  }

  void register(
      String username,
      String mobile,
      String reference,
      char[] password,
      char[] confirm,
      boolean accepted) {
    registerAccount(
        username, mobile, reference, password, confirm, accepted, Session.Role.CUSTOMER);
  }

  void registerAdmin(
      String username,
      String mobile,
      String reference,
      char[] password,
      char[] confirm,
      boolean accepted) {
    try {
      Session.require(Session.Role.ADMIN);
      registerAccount(username, mobile, reference, password, confirm, accepted, Session.Role.ADMIN);
    } finally {
      if (password != null) Arrays.fill(password, '\0');
      if (confirm != null) Arrays.fill(confirm, '\0');
    }
  }

  private void registerAccount(
      String username,
      String mobile,
      String reference,
      char[] password,
      char[] confirm,
      boolean accepted,
      Session.Role role) {
    try {
      String user = required(username, "Username", 30).toLowerCase(Locale.ROOT);
      if (!user.matches("[a-z0-9][a-z0-9_.-]{2,29}"))
        throw new IllegalArgumentException(
            "Use 3–30 letters, digits, dots, underscores or hyphens for the user ID.");
      String phone = required(mobile, "Fictional mobile", 30);
      if (!phone.matches("[+0-9 ()-]{7,30}"))
        throw new IllegalArgumentException(
            "Use a fictional mobile number of 7–30 allowed characters.");
      String ref = required(reference, "Demo reference", 30);
      if (!ref.matches("DEMO-[A-Za-z0-9-]{4,16}"))
        throw new IllegalArgumentException(
            "Use a fictional reference beginning DEMO-, for example DEMO-1234. Do not enter a real"
                + " card or ID.");
      if (password == null || password.length < 10 || password.length > 128)
        throw new IllegalArgumentException("Choose a password of 10–128 characters.");
      if (!Arrays.equals(password, confirm))
        throw new IllegalArgumentException("The passwords do not match.");
      if (!accepted)
        throw new IllegalArgumentException(
            "Confirm that you are using fictional demo information.");
      String hash = PasswordHasher.hash(password);
      try (Connection connection = database.open();
          PreparedStatement insert =
              connection.prepareStatement(
                  "INSERT INTO users(username,mobile,demo_reference,password_hash,role)"
                      + " VALUES(?,?,?,?,?)")) {
        insert.setString(1, user);
        insert.setString(2, phone);
        insert.setString(3, ref);
        insert.setString(4, hash);
        insert.setString(5, role.name());
        insert.executeUpdate();
      } catch (SQLException failure) {
        if ("23505".equals(failure.getSQLState()))
          throw new IllegalArgumentException(
              "That user ID already exists. Choose another or log in.");
        throw unavailable(failure);
      }
    } finally {
      if (password != null) Arrays.fill(password, '\0');
      if (confirm != null) Arrays.fill(confirm, '\0');
    }
  }

  long submitLoan(String name, String email, String amount, String type, int term, String purpose) {
    Session.Principal principal = Session.require(Session.Role.CUSTOMER);
    String fullName = required(name, "Full name", 120),
        mail = required(email, "Email", 200),
        reason = required(purpose, "Loan purpose", 2000);
    if (!mail.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+"))
      throw new IllegalArgumentException(
          "Enter an email address in the usual name@example.invalid form.");
    BigDecimal value;
    try {
      if (amount == null || amount.length() > 40) throw new NumberFormatException();
      value = new BigDecimal(amount.strip());
    } catch (RuntimeException invalid) {
      throw new IllegalArgumentException("Enter a valid positive OMR amount.");
    }
    if (value.scale() > 3 || value.signum() <= 0 || value.compareTo(new BigDecimal("1000000")) > 0)
      throw new IllegalArgumentException(
          "Use a positive amount up to 1,000,000 OMR, with at most 3 decimal places. This is a demo"
              + " input limit.");
    if (!TYPES.contains(type) || !TERMS.contains(term))
      throw new IllegalArgumentException("Choose a listed loan type and term.");
    try (Connection connection = database.open()) {
      connection.setAutoCommit(false);
      try (PreparedStatement insert =
          connection.prepareStatement(
              "INSERT INTO"
                  + " loan_applications(user_id,full_name,email,amount,loan_type,term_months,purpose)"
                  + " VALUES(?,?,?,?,?,?,?)",
              Statement.RETURN_GENERATED_KEYS)) {
        insert.setLong(1, principal.id());
        insert.setString(2, fullName);
        insert.setString(3, mail);
        insert.setBigDecimal(4, value);
        insert.setString(5, type);
        insert.setInt(6, term);
        insert.setString(7, reason);
        insert.executeUpdate();
        if (!Session.isCurrent(principal)) {
          connection.rollback();
          throw new SecurityException("The login changed. Log in again before submitting.");
        }
        try (ResultSet keys = insert.getGeneratedKeys()) {
          keys.next();
          long id = keys.getLong(1);
          connection.commit();
          return id;
        }
      } catch (SQLException | RuntimeException failure) {
        connection.rollback();
        throw failure;
      }
    } catch (SQLException failure) {
      throw unavailable(failure);
    }
  }

  long submitQuery(String description) {
    Session.Principal principal = Session.require(Session.Role.CUSTOMER);
    String message = required(description, "Description", 2000);
    try (Connection connection = database.open()) {
      connection.setAutoCommit(false);
      try (PreparedStatement insert =
          connection.prepareStatement(
              "INSERT INTO questions(user_id,description) VALUES(?,?)",
              Statement.RETURN_GENERATED_KEYS)) {
        insert.setLong(1, principal.id());
        insert.setString(2, message);
        insert.executeUpdate();
        if (!Session.isCurrent(principal)) {
          connection.rollback();
          throw new SecurityException("The login changed. Log in again before submitting.");
        }
        try (ResultSet keys = insert.getGeneratedKeys()) {
          keys.next();
          long id = keys.getLong(1);
          connection.commit();
          return id;
        }
      } catch (SQLException | RuntimeException failure) {
        connection.rollback();
        throw failure;
      }
    } catch (SQLException failure) {
      throw unavailable(failure);
    }
  }

  List<Object[]> loans() {
    Session.Principal principal = Session.require();
    String sql =
        "SELECT"
            + " l.id,u.username,l.full_name,l.email,l.amount,l.loan_type,l.term_months,l.purpose,l.created_at"
            + " FROM loan_applications l JOIN users u ON u.id=l.user_id"
            + (principal.role() == Session.Role.CUSTOMER ? " WHERE l.user_id=?" : "")
            + " ORDER BY l.id DESC";
    List<Object[]> rows = new ArrayList<>();
    try (Connection connection = database.open();
        PreparedStatement query = connection.prepareStatement(sql)) {
      if (principal.role() == Session.Role.CUSTOMER) query.setLong(1, principal.id());
      try (ResultSet result = query.executeQuery()) {
        while (result.next())
          rows.add(
              new Object[] {
                result.getLong(1),
                result.getString(2),
                result.getString(3),
                result.getString(4),
                result.getBigDecimal(5).toPlainString(),
                result.getString(6),
                result.getInt(7) + " months",
                result.getString(8),
                result.getTimestamp(9).toString()
              });
      }
      if (!Session.isCurrent(principal))
        throw new SecurityException("The login changed. Reopen the screen.");
      return rows;
    } catch (SQLException failure) {
      throw unavailable(failure);
    }
  }

  List<Object[]> queries() {
    Session.Principal principal = Session.require();
    String sql =
        "SELECT q.id,u.username,q.description,q.created_at FROM questions q JOIN users u ON"
            + " u.id=q.user_id"
            + (principal.role() == Session.Role.CUSTOMER ? " WHERE q.user_id=?" : "")
            + " ORDER BY q.id DESC";
    List<Object[]> rows = new ArrayList<>();
    try (Connection connection = database.open();
        PreparedStatement query = connection.prepareStatement(sql)) {
      if (principal.role() == Session.Role.CUSTOMER) query.setLong(1, principal.id());
      try (ResultSet result = query.executeQuery()) {
        while (result.next())
          rows.add(
              new Object[] {
                result.getLong(1),
                result.getString(2),
                result.getString(3),
                result.getTimestamp(4).toString()
              });
      }
      if (!Session.isCurrent(principal))
        throw new SecurityException("The login changed. Reopen the screen.");
      return rows;
    } catch (SQLException failure) {
      throw unavailable(failure);
    }
  }

  private static String required(String text, String label, int max) {
    if (text == null || text.strip().isEmpty() || text.strip().length() > max)
      throw new IllegalArgumentException(label + " is required (up to " + max + " characters).");
    return text.strip();
  }

  private static IllegalStateException unavailable(SQLException cause) {
    return new IllegalStateException(
        "The local database operation could not complete. No success is confirmed; retry or check"
            + " the data folder.",
        cause);
  }
}
