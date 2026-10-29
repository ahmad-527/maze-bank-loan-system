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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame {

  private JPanel contentPane;
  private JTextField textField;
  private JPasswordField passwordField;

  /** Launch the application. */
  public static void main(String[] args) {
    Ui.launch(LoginFrame::new);
  }

  /** Create the frame. */
  public LoginFrame() {

    setIconImage(
        Toolkit.getDefaultToolkit().getImage(LoginFrame.class.getResource("/Images/maze.png")));
    setTitle("USER LOGIN");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 802, 700);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblNewLabel = new JLabel("Username:");
    lblNewLabel.setBounds(204, 214, 82, 32);
    lblNewLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    contentPane.add(lblNewLabel);

    JLabel lblPassword = new JLabel("Password:");
    lblPassword.setBounds(204, 275, 82, 20);
    lblPassword.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    contentPane.add(lblPassword);

    textField = new JTextField();
    textField.setBounds(296, 221, 286, 23);
    contentPane.add(textField);
    textField.setColumns(10);

    passwordField = new JPasswordField();
    passwordField.setBounds(296, 276, 286, 23);
    contentPane.add(passwordField);

    JLabel lblLogIn = new JLabel("MAZE BANK ");
    lblLogIn.setBounds(274, 27, 264, 65);
    lblLogIn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 42));
    contentPane.add(lblLogIn);

    JLabel lblWithYourExisting = new JLabel("with your existing account");
    lblWithYourExisting.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
    lblWithYourExisting.setBounds(293, 141, 216, 32);
    contentPane.add(lblWithYourExisting);

    JButton btnNewButton = new JButton("LOG IN");
    btnNewButton.addActionListener(
        e -> {
          String user = textField.getText();
          char[] password = passwordField.getPassword();
          Ui.background(
              LoginFrame.this,
              btnNewButton,
              () -> BankService.get().authenticate(user, password, Session.Role.CUSTOMER),
              principal -> {
                Session.start(principal);
                passwordField.setText("");
                Ui.open(LoginFrame.this, new UserDashboard());
              });
        });

    btnNewButton.setBackground(new Color(255, 255, 255));
    btnNewButton.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnNewButton.setBounds(233, 340, 146, 45);
    contentPane.add(btnNewButton);

    JLabel lblLogIn_2 = new JLabel("LOG IN");
    lblLogIn_2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 26));
    lblLogIn_2.setBounds(347, 108, 92, 39);
    contentPane.add(lblLogIn_2);

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
    contentPane.add(btnCancel);

    JButton btnRegister = new JButton("REGISTER");
    btnRegister.setBackground(Color.WHITE);
    btnRegister.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Ui.open(LoginFrame.this, new RegistrationFrame());
          }
        });
    btnRegister.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnRegister.setBounds(307, 446, 171, 45);
    contentPane.add(btnRegister);

    JLabel lblNewUser = new JLabel("New User?");
    lblNewUser.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    lblNewUser.setBounds(352, 416, 82, 32);
    contentPane.add(lblNewUser);

    JLabel lblNewLabel_1 = new JLabel("");
    lblNewLabel_1.setIcon(new ImageIcon(LoginFrame.class.getResource("/Images/maze.png")));
    lblNewLabel_1.setBounds(235, 37, 39, 45);
    contentPane.add(lblNewLabel_1);

    JLabel lblNewLabel_1_1 = new JLabel("");
    lblNewLabel_1_1.setIcon(new ImageIcon(LoginFrame.class.getResource("/Images/maze.png")));
    lblNewLabel_1_1.setBounds(530, 37, 39, 45);
    contentPane.add(lblNewLabel_1_1);

    JButton btnRegister_1 = new JButton("");
    btnRegister_1.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            System.exit(0);
          }
        });
    btnRegister_1.setIcon(
        new ImageIcon(LoginFrame.class.getResource("/Images/Github-Octicons-X-16.32.png")));
    btnRegister_1.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnRegister_1.setBackground(Color.WHITE);
    btnRegister_1.setBounds(721, 10, 57, 41);
    contentPane.add(btnRegister_1);

    JLabel lblBackToHome = new JLabel("Back to home");
    lblBackToHome.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    lblBackToHome.setBounds(341, 533, 105, 32);
    contentPane.add(lblBackToHome);

    JButton btnBackToHome = new JButton("HOME");
    btnBackToHome.setBackground(Color.WHITE);
    btnBackToHome.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Ui.open(LoginFrame.this, new Start_Page());
          }
        });
    btnBackToHome.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnBackToHome.setBounds(335, 560, 117, 45);
    contentPane.add(btnBackToHome);

    textField.setName("login.username");
    passwordField.setName("login.password");
    btnNewButton.setName("login.submit");
    Ui.finish(this);
  }
}
