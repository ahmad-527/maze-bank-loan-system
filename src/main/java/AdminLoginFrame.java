import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AdminLoginFrame extends JFrame {

  private JPanel contentPane;
  private JTextField textField;
  private JPasswordField passwordField;

  /** Launch the application. */
  public static void main(String[] args) {
    Ui.launch(AdminLoginFrame::new);
  }

  /** Create the frame. */
  public AdminLoginFrame() {

    setIconImage(
        Toolkit.getDefaultToolkit()
            .getImage(AdminLoginFrame.class.getResource("/Images/maze.png")));
    setTitle("ADMIN LOGIN");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 802, 700);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    setContentPane(contentPane);
    contentPane.setLayout(null);

    JPanel contentPane_1 = contentPane;

    JLabel lblAdminUsername = new JLabel("Admin Username:");
    lblAdminUsername.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    lblAdminUsername.setBounds(150, 214, 136, 32);
    contentPane_1.add(lblAdminUsername);

    JLabel lblAdminPassword = new JLabel("Admin Password:");
    lblAdminPassword.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    lblAdminPassword.setBounds(150, 275, 136, 20);
    contentPane_1.add(lblAdminPassword);

    textField = new JTextField();
    textField.setColumns(10);
    textField.setBounds(296, 221, 286, 23);
    contentPane_1.add(textField);

    passwordField = new JPasswordField();
    passwordField.setBounds(296, 276, 286, 23);
    contentPane_1.add(passwordField);

    JLabel lblLogIn = new JLabel("MAZE BANK ");
    lblLogIn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 42));
    lblLogIn.setBounds(274, 27, 264, 65);
    contentPane_1.add(lblLogIn);

    JLabel lblWithYourExisting = new JLabel("with your existing account");
    lblWithYourExisting.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
    lblWithYourExisting.setBounds(293, 141, 216, 32);
    contentPane_1.add(lblWithYourExisting);

    JButton btnNewButton = new JButton("LOG IN");
    btnNewButton.addActionListener(
        e -> {
          String user = textField.getText();
          char[] password = passwordField.getPassword();
          Ui.background(
              AdminLoginFrame.this,
              btnNewButton,
              () -> BankService.get().authenticate(user, password, Session.Role.ADMIN),
              principal -> {
                Session.start(principal);
                passwordField.setText("");
                Ui.open(AdminLoginFrame.this, new AdminDashboard());
              });
        });
    btnNewButton.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnNewButton.setBackground(Color.WHITE);
    btnNewButton.setBounds(233, 340, 146, 45);
    contentPane_1.add(btnNewButton);

    JLabel lblLogIn_2 = new JLabel("LOG IN");
    lblLogIn_2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 26));
    lblLogIn_2.setBounds(347, 108, 92, 39);
    contentPane_1.add(lblLogIn_2);

    JButton btnCancel = new JButton("CLEAR");
    btnCancel.setForeground(Color.RED);
    btnCancel.setBackground(Color.WHITE);
    btnCancel.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            textField.setText(null);
            passwordField.setText(null);
          }
        });
    btnCancel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnCancel.setBounds(412, 340, 146, 45);
    contentPane_1.add(btnCancel);

    JButton btnRegister = new JButton("DEMO HELP");
    btnRegister.setBackground(Color.WHITE);
    btnRegister.addActionListener(
        e -> {
          JOptionPane.showMessageDialog(
              AdminLoginFrame.this,
              "The local demo administrator is demo-admin / DemoAdmin!2026. New admins can be"
                  + " created only after an admin login.");
        });
    btnRegister.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnRegister.setBounds(307, 446, 171, 45);
    contentPane_1.add(btnRegister);

    JLabel lblNewUser = new JLabel("Admin access");
    lblNewUser.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    lblNewUser.setBounds(352, 416, 82, 32);
    contentPane_1.add(lblNewUser);

    JLabel lblNewLabel_1 = new JLabel("");
    lblNewLabel_1.setBounds(235, 37, 39, 45);
    contentPane_1.add(lblNewLabel_1);

    JLabel lblNewLabel_1_1 = new JLabel("");
    lblNewLabel_1_1.setBounds(530, 37, 39, 45);
    contentPane_1.add(lblNewLabel_1_1);

    JLabel lblNewLabel_1_2 = new JLabel("");
    lblNewLabel_1_2.setIcon(new ImageIcon(AdminLoginFrame.class.getResource("/Images/maze.png")));
    lblNewLabel_1_2.setBounds(233, 37, 39, 45);
    contentPane_1.add(lblNewLabel_1_2);

    JLabel lblNewLabel_1_3 = new JLabel("");
    lblNewLabel_1_3.setIcon(new ImageIcon(AdminLoginFrame.class.getResource("/Images/maze.png")));
    lblNewLabel_1_3.setBounds(530, 37, 39, 45);
    contentPane_1.add(lblNewLabel_1_3);

    JButton btnRegister_1 = new JButton("");
    btnRegister_1.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            System.exit(0);
          }
        });
    btnRegister_1.setIcon(
        new ImageIcon(AdminLoginFrame.class.getResource("/Images/Github-Octicons-X-16.32.png")));
    btnRegister_1.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnRegister_1.setBackground(Color.WHITE);
    btnRegister_1.setBounds(710, 10, 57, 41);
    contentPane_1.add(btnRegister_1);

    JButton btnBackToHome = new JButton("HOME");
    btnBackToHome.setBackground(Color.WHITE);
    btnBackToHome.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Ui.open(AdminLoginFrame.this, new Start_Page());
          }
        });
    btnBackToHome.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnBackToHome.setBounds(335, 555, 117, 45);
    contentPane_1.add(btnBackToHome);

    JLabel lblBackToHome = new JLabel("Back to home");
    lblBackToHome.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    lblBackToHome.setBounds(347, 525, 105, 32);
    contentPane_1.add(lblBackToHome);

    textField.setName("login.username");
    passwordField.setName("login.password");
    btnNewButton.setName("login.submit");
    Ui.finish(this);
  }
}
