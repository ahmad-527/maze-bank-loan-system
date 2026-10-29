import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AdminLoanReqs extends JFrame {

  private JPanel contentPane;
  private JTable table;
  private JButton btnRegister;
  private JButton btnBack;
  private JLabel lblBackToDashboard;
  private JLabel lblLogIn;
  private JLabel lblNewLabel;
  private JLabel lblNewLabel_1;

  public static void main(String[] args) {
    Ui.launch(AdminLoanReqs::new);
  }

  public AdminLoanReqs() {
    Session.require();

    setIconImage(
        Toolkit.getDefaultToolkit().getImage(AdminLoanReqs.class.getResource("/Images/maze.png")));
    setTitle("LOAN REQUESTS");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 800, 700);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblLoanRequests = new JLabel("LOAN REQUESTS");
    lblLoanRequests.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    lblLoanRequests.setBounds(291, 84, 222, 26);
    contentPane.add(lblLoanRequests);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(34, 120, 724, 443);
    contentPane.add(scrollPane);

    table = new JTable();
    table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
    table.setModel(
        new DefaultTableModel(
            new Object[][] {},
            new String[] {
              "Full Name", "Email", "Loan Amount", "Loan Type", "Loan Term", "Loan Purpose Inquiry"
            }) {
          Class[] columnTypes =
              new Class[] {
                String.class, String.class, String.class, String.class, String.class, String.class
              };

          public Class getColumnClass(int columnIndex) {
            return columnTypes[columnIndex];
          }

          boolean[] columnEditables = new boolean[] {false, false, false, false, false, false};

          public boolean isCellEditable(int row, int column) {
            return columnEditables[column];
          }
        });
    scrollPane.setViewportView(table);

    btnRegister = new JButton("");
    btnRegister.setIcon(
        new ImageIcon(AdminLoanReqs.class.getResource("/Images/Github-Octicons-X-16.32.png")));
    btnRegister.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            System.exit(0);
          }
        });
    btnRegister.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnRegister.setBackground(Color.WHITE);
    btnRegister.setBounds(719, 10, 57, 41);
    contentPane.add(btnRegister);

    btnBack = new JButton("BACK");
    btnBack.addActionListener(
        e -> {
          Ui.open(AdminLoanReqs.this, Ui.dashboard());
        });
    btnBack.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnBack.setBackground(Color.WHITE);
    btnBack.setBounds(334, 592, 117, 45);
    contentPane.add(btnBack);

    lblBackToDashboard = new JLabel("Back to Dashboard");
    lblBackToDashboard.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    lblBackToDashboard.setBounds(322, 573, 146, 21);
    contentPane.add(lblBackToDashboard);

    lblLogIn = new JLabel("MAZE BANK ");
    lblLogIn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 42));
    lblLogIn.setBounds(268, 9, 264, 65);
    contentPane.add(lblLogIn);

    lblNewLabel = new JLabel("");
    lblNewLabel.setIcon(new ImageIcon(AdminLoanReqs.class.getResource("/Images/maze.png")));
    lblNewLabel.setBounds(219, 21, 39, 45);
    contentPane.add(lblNewLabel);

    lblNewLabel_1 = new JLabel("");
    lblNewLabel_1.setIcon(new ImageIcon(AdminLoanReqs.class.getResource("/Images/maze.png")));
    lblNewLabel_1.setBounds(528, 21, 39, 45);
    contentPane.add(lblNewLabel_1);

    Ui.background(
        this,
        null,
        () -> BankService.get().loans(),
        rows ->
            Ui.table(
                table,
                new String[] {
                  "ID",
                  "Customer",
                  "Full name",
                  "Email",
                  "Amount (OMR)",
                  "Loan type",
                  "Term",
                  "Purpose",
                  "Submitted"
                },
                rows));

    table.setName("loan.rows");
    if (Session.require().role() == Session.Role.CUSTOMER) {
      setTitle("My loan applications");
      lblLoanRequests.setText("MY APPLICATIONS");
    }
    Ui.finish(this);
  }
}
