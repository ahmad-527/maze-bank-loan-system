import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class LoanApplicationFrame extends JFrame {

  private JPanel contentPane;
  private JTextField name;
  private JTextField mail;
  private JTextField amount;
  private JTextField inquiry;

  public static void main(String[] args) {
    Ui.launch(LoanApplicationFrame::new);
  }

  public LoanApplicationFrame() {
    Session.require(Session.Role.CUSTOMER);

    setIconImage(
        Toolkit.getDefaultToolkit()
            .getImage(LoanApplicationFrame.class.getResource("/Images/maze.png")));
    setTitle("Loan Application Form");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 800, 800);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblNewLabel = new JLabel("LOAN APPLICATION FORM");
    lblNewLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    lblNewLabel.setBounds(230, 65, 332, 26);
    contentPane.add(lblNewLabel);

    JLabel lblPleaseProvideThe =
        new JLabel("Please provide the following information to apply for a loan.");
    lblPleaseProvideThe.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    lblPleaseProvideThe.setBounds(44, 165, 404, 21);
    contentPane.add(lblPleaseProvideThe);

    JLabel lblFullName = new JLabel("Full Name:");
    lblFullName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    lblFullName.setBounds(100, 210, 71, 21);
    contentPane.add(lblFullName);

    JLabel lblEmail = new JLabel("Email:");
    lblEmail.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    lblEmail.setBounds(124, 258, 47, 21);
    contentPane.add(lblEmail);

    JLabel lblFullName_1_1 = new JLabel("Loan Amount:");
    lblFullName_1_1.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    lblFullName_1_1.setBounds(77, 305, 94, 21);
    contentPane.add(lblFullName_1_1);

    JLabel lblFullName_1_2 = new JLabel("Loan Type:");
    lblFullName_1_2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    lblFullName_1_2.setBounds(93, 352, 78, 21);
    contentPane.add(lblFullName_1_2);

    name = new JTextField();
    name.setToolTipText("Enter Full name as per your ID");
    name.setColumns(10);
    name.setBounds(181, 211, 287, 21);
    contentPane.add(name);

    mail = new JTextField();
    mail.setToolTipText("Email linked with your account");
    mail.setColumns(10);
    mail.setBounds(181, 259, 287, 21);
    contentPane.add(mail);

    amount = new JTextField();
    amount.setToolTipText("Loan Amount (OMR)");
    amount.setColumns(10);
    amount.setBounds(181, 306, 287, 21);
    contentPane.add(amount);

    final JComboBox<String> comboBox = new JComboBox<String>();
    comboBox.setModel(
        new DefaultComboBoxModel<String>(
            new String[] {"Personal loan", "Auto loan", "Mortgage loan", "Investment loan"}));
    comboBox.setBounds(181, 353, 287, 21);
    contentPane.add(comboBox);

    JLabel lblNewLabel_1_1 = new JLabel("");
    lblNewLabel_1_1.setIcon(
        new ImageIcon(LoanApplicationFrame.class.getResource("/Images/maze.png")));
    lblNewLabel_1_1.setBounds(220, 10, 39, 45);
    contentPane.add(lblNewLabel_1_1);

    JLabel lblFullName_1_2_1 = new JLabel("Loan Term:");
    lblFullName_1_2_1.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    lblFullName_1_2_1.setBounds(93, 392, 78, 21);
    contentPane.add(lblFullName_1_2_1);

    final JComboBox<String> comboBox_1 = new JComboBox<String>();
    comboBox_1.setModel(
        new DefaultComboBoxModel<String>(
            new String[] {
              "6 months",
              "12 months",
              "18 months",
              "24 months",
              "30 months",
              "36 months",
              "42 months",
              "48 months"
            }));
    comboBox_1.setBounds(181, 393, 287, 21);
    contentPane.add(comboBox_1);

    JLabel lblFullName_1_2_1_1 = new JLabel("Loan Purpose \r\nInquiry:");
    lblFullName_1_2_1_1.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    lblFullName_1_2_1_1.setBounds(8, 524, 163, 21);
    contentPane.add(lblFullName_1_2_1_1);

    inquiry = new JTextField();
    inquiry.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
    inquiry.setToolTipText("Discuss the purpose of your Loan");
    inquiry.setHorizontalAlignment(SwingConstants.LEFT);
    inquiry.setBounds(181, 453, 287, 166);
    contentPane.add(inquiry);
    inquiry.setColumns(10);

    JButton btnSubmit = new JButton("SUBMIT");
    btnSubmit.setBackground(Color.WHITE);
    btnSubmit.addActionListener(
        e -> {
          String fullName = name.getText(),
              email = mail.getText(),
              value = amount.getText(),
              purpose = inquiry.getText();
          String type = (String) comboBox.getSelectedItem();
          int term = Integer.parseInt(comboBox_1.getSelectedItem().toString().split(" ")[0]);
          Ui.background(
              LoanApplicationFrame.this,
              btnSubmit,
              () -> BankService.get().submitLoan(fullName, email, value, type, term, purpose),
              id -> {
                JOptionPane.showMessageDialog(
                    LoanApplicationFrame.this,
                    "Application #"
                        + id
                        + " saved locally. This is not a credit decision or bank submission.");
                Ui.open(LoanApplicationFrame.this, new UserDashboard());
              });
        });
    btnSubmit.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 15));
    btnSubmit.setBounds(255, 629, 97, 26);
    contentPane.add(btnSubmit);

    JLabel lblLogIn = new JLabel("MAZE BANK ");
    lblLogIn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 42));
    lblLogIn.setBounds(261, -2, 264, 65);
    contentPane.add(lblLogIn);

    JLabel lblNewLabel_1_1_1 = new JLabel("");
    lblNewLabel_1_1_1.setIcon(
        new ImageIcon(LoanApplicationFrame.class.getResource("/Images/maze.png")));
    lblNewLabel_1_1_1.setBounds(526, 10, 39, 45);
    contentPane.add(lblNewLabel_1_1_1);

    JButton btnRegister_1 = new JButton("");
    btnRegister_1.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            System.exit(0);
          }
        });
    btnRegister_1.setIcon(
        new ImageIcon(
            LoanApplicationFrame.class.getResource("/Images/Github-Octicons-X-16.32.png")));
    btnRegister_1.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnRegister_1.setBackground(Color.WHITE);
    btnRegister_1.setBounds(719, 10, 57, 41);
    contentPane.add(btnRegister_1);

    name.setName("loan.name");
    mail.setName("loan.email");
    amount.setName("loan.amount");
    comboBox.setName("loan.type");
    comboBox_1.setName("loan.term");
    inquiry.setName("loan.purpose");
    btnSubmit.setName("loan.submit");
    JButton back = new JButton("BACK TO DASHBOARD");
    back.setBounds(465, 630, 265, 30);
    contentPane.add(back);
    back.addActionListener(e -> Ui.open(LoanApplicationFrame.this, new UserDashboard()));
    Ui.finish(this);
  }
}
