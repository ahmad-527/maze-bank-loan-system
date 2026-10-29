/** In-process role context. The local demo is not a multi-user banking server. */
final class Session {
  enum Role {
    CUSTOMER,
    ADMIN
  }

  record Principal(long id, String username, Role role) {}

  private static volatile Principal current;

  static void start(Principal principal) {
    current = java.util.Objects.requireNonNull(principal);
  }

  static void clear() {
    current = null;
  }

  static Principal require() {
    Principal value = current;
    if (value == null) throw new SecurityException("Please log in to continue.");
    return value;
  }

  static Principal require(Role role) {
    Principal value = require();
    if (value.role() != role)
      throw new SecurityException(
          "This screen requires a " + role.name().toLowerCase() + " login.");
    return value;
  }

  static boolean isCurrent(Principal principal) {
    return current == principal;
  }
}
