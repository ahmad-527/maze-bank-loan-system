public class ContactUsFrame extends InformationFrame {
  public ContactUsFrame() {
    super(
        "Project support",
        "To try the customer-query workflow, log in as a demo customer and choose CUSTOMER QUERIES."
            + " Your query is saved locally and can be reviewed through the demo administrator"
            + " account.\n\n"
            + "No email or external message is sent. Use fictional information only.\n\n"
            + "Project source, setup, changes and limitations:\n"
            + "https://github.com/ahmad-527/maze-bank-loan-system\n\n"
            + "For a public bug report, include the app version, Java/operating-system version and"
            + " steps. Keep personal details, passwords and database files out of public issues.");
  }

  public static void main(String[] args) {
    Ui.launch(ContactUsFrame::new);
  }
}
