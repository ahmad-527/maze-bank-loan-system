public class AboutUsFrame extends InformationFrame {
  public AboutUsFrame() {
    super(
        "About this project",
        "Maze Bank is an undergraduate Java Swing loan-management coursework project.\n\n"
            + "Customers can register, sign in, submit fictional loan applications and queries, and"
            + " review their own submissions. Demo administrators can review those records.\n\n"
            + "This runnable portfolio edition preserves the principal coursework screens with"
            + " later navigation, authentication, validation and database repairs. The original"
            + " Oracle-oriented source variants are archived separately.\n\n"
            + "This is a local educational simulation. It is not connected to a bank, does not"
            + " transfer money, and does not make or guarantee credit decisions.\n\n"
            + "Includes H2 Database Engine 2.5.252, Copyright 2004-2026 H2 Group, under MPL 2.0."
            + " Full licence and source-availability details accompany the release in"
            + " THIRD_PARTY_NOTICES.txt and licenses/H2-LICENSE.html; copies are also inside the"
            + " JAR.");
  }

  public static void main(String[] args) {
    Ui.launch(AboutUsFrame::new);
  }
}
