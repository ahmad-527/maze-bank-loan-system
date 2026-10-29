import java.awt.*;
import javax.swing.*;

/** Customers see their own persisted queries; admins review all demo queries. */
public class CustomerQueries extends JFrame {
  private final JTable table = new JTable();
  private final JTextField txtUserName = new JTextField();
  private final JTextArea txtDescription = new JTextArea(3, 40);
  private final JLabel status = new JLabel("Loading saved queries…");
  private final JButton refresh = new JButton("Refresh");

  public static void main(String[] args) {
    Ui.launch(CustomerQueries::new);
  }

  public CustomerQueries() {
    Session.Principal principal = Session.require();
    setTitle(
        principal.role() == Session.Role.ADMIN ? "Review customer queries" : "My customer queries");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    JPanel body = new JPanel(new BorderLayout(12, 12));
    body.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    setContentPane(body);
    JLabel heading = new JLabel(getTitle());
    heading.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
    body.add(heading, BorderLayout.NORTH);
    table.setName("query.rows");
    body.add(new JScrollPane(table), BorderLayout.CENTER);
    JPanel footer = new JPanel();
    footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
    if (principal.role() == Session.Role.CUSTOMER) {
      txtUserName.setText(principal.username());
      txtUserName.setEditable(false);
      txtUserName.setName("query.username");
      footer.add(new JLabel("Signed in customer"));
      footer.add(txtUserName);
      footer.add(new JLabel("Description (fictional information only)"));
      txtDescription.setLineWrap(true);
      txtDescription.setWrapStyleWord(true);
      txtDescription.setName("query.description");
      footer.add(new JScrollPane(txtDescription));
      JButton submit = new JButton("Submit");
      submit.setName("query.submit");
      footer.add(submit);
      submit.addActionListener(
          e -> {
            String message = txtDescription.getText();
            Ui.background(
                this,
                submit,
                () -> BankService.get().submitQuery(message),
                id -> {
                  txtDescription.setText("");
                  reload();
                });
          });
    }
    footer.add(status);
    JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
    refresh.addActionListener(e -> reload());
    buttons.add(refresh);
    JButton back = new JButton("Back to dashboard");
    back.setName("query.back");
    back.addActionListener(e -> Ui.open(this, Ui.dashboard()));
    buttons.add(back);
    footer.add(buttons);
    footer.add(new JLabel("Local coursework demo · no message is sent outside this app"));
    body.add(footer, BorderLayout.SOUTH);
    Rectangle usable = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    setSize(Math.min(850, usable.width - 40), Math.min(700, usable.height - 50));
    setLocationRelativeTo(null);
    reload();
  }

  private void reload() {
    status.setText("Loading saved queries…");
    Ui.background(
        this,
        refresh,
        () -> BankService.get().queries(),
        rows -> {
          Ui.table(table, new String[] {"ID", "Customer", "Description", "Submitted"}, rows);
          status.setText(rows.size() + " saved queries");
        });
  }
}
