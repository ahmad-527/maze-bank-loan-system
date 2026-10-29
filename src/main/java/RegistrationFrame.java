import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class RegistrationFrame extends JFrame {

  private JPanel contentPane;
  private JTextField textField;
  private JTextField textField_1;
  private JTextField textField_2;
  private JPasswordField passwordField;
  private JPasswordField passwordField_1;

  /** Launch the application. */
  public static void main(String[] args) {
    Ui.launch(RegistrationFrame::new);
  }

  /** Create the frame. */
  public RegistrationFrame() {

    setIconImage(
        Toolkit.getDefaultToolkit()
            .getImage(RegistrationFrame.class.getResource("/Images/maze.png")));

    setTitle("Registration Page");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 800, 800);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblRegistration = new JLabel("REGISTRATION FORM");
    lblRegistration.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
    lblRegistration.setBounds(242, 41, 301, 36);
    contentPane.add(lblRegistration);

    JLabel lblEnterYourCredentials = new JLabel("Enter your credentials below ");
    lblEnterYourCredentials.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
    lblEnterYourCredentials.setBounds(305, 125, 175, 14);
    contentPane.add(lblEnterYourCredentials);

    JLabel lblWelcomeToThe = new JLabel("WELCOME TO");
    lblWelcomeToThe.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
    lblWelcomeToThe.setBounds(265, 93, 122, 36);
    contentPane.add(lblWelcomeToThe);

    JLabel lblMazeBank = new JLabel("Maze Bank");
    lblMazeBank.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
    lblMazeBank.setBounds(389, 92, 128, 36);
    contentPane.add(lblMazeBank);

    JComboBox comboBox = new JComboBox();
    comboBox.setBackground(UIManager.getColor("CheckBox.light"));
    comboBox.setModel(
        new DefaultComboBoxModel(
            new String[] {
              "OM (+968)", "BH (+973)", "KW (+965)", "QA (+974)", "KSA (+966)", "UAE (+971)"
            }));
    comboBox.setBounds(119, 214, 87, 21);
    contentPane.add(comboBox);

    JLabel lblNewLabel = new JLabel("What is your mobile number?");
    lblNewLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    lblNewLabel.setBounds(120, 183, 256, 21);
    contentPane.add(lblNewLabel);

    textField = new JTextField();
    textField.setBounds(216, 214, 190, 21);
    contentPane.add(textField);
    textField.setColumns(10);

    JLabel lblNewLabel_1 = new JLabel("Fictional demo number; never use real banking details.");
    lblNewLabel_1.setForeground(new Color(220, 20, 60));
    lblNewLabel_1.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
    lblNewLabel_1.setBounds(119, 230, 277, 27);
    contentPane.add(lblNewLabel_1);

    JLabel lblWhatIsYour = new JLabel("Fictional account reference (DEMO-1234)");
    lblWhatIsYour.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    lblWhatIsYour.setBounds(120, 291, 480, 24);
    contentPane.add(lblWhatIsYour);

    textField_1 = new JTextField();
    textField_1.setColumns(10);
    textField_1.setBounds(119, 322, 287, 21);
    contentPane.add(textField_1);

    JLabel lblCreateAUser = new JLabel("Create a User ID for account login");
    lblCreateAUser.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    lblCreateAUser.setBounds(119, 359, 256, 21);
    contentPane.add(lblCreateAUser);

    textField_2 = new JTextField();
    textField_2.setColumns(10);
    textField_2.setBounds(119, 390, 287, 21);
    contentPane.add(textField_2);

    JLabel lblCreateYourNew = new JLabel("Create your new Password");
    lblCreateYourNew.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    lblCreateYourNew.setBounds(120, 435, 256, 21);
    contentPane.add(lblCreateYourNew);

    JLabel lblConfirmYourNew = new JLabel("Confirm your new Password");
    lblConfirmYourNew.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    lblConfirmYourNew.setBounds(119, 509, 256, 21);
    contentPane.add(lblConfirmYourNew);

    final JCheckBox chckbxNewCheckBox = new JCheckBox("I confirm");
    chckbxNewCheckBox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
    chckbxNewCheckBox.setBounds(119, 580, 87, 21);
    contentPane.add(chckbxNewCheckBox);

    passwordField = new JPasswordField();
    passwordField.setBounds(119, 466, 287, 21);
    contentPane.add(passwordField);

    passwordField_1 = new JPasswordField();
    passwordField_1.setBounds(119, 540, 287, 21);
    contentPane.add(passwordField_1);

    JButton btnRegister = new JButton("REGISTER");
    btnRegister.setBackground(Color.WHITE);
    btnRegister.addActionListener(
        e -> {
          String user = textField_2.getText(),
              phone =
                  comboBox.getSelectedItem().toString().replaceAll("[^+0-9]", "")
                      + " "
                      + textField.getText(),
              reference = textField_1.getText();
          char[] password = passwordField.getPassword(),
              confirmation = passwordField_1.getPassword();
          boolean accepted = chckbxNewCheckBox.isSelected();
          Ui.background(
              RegistrationFrame.this,
              btnRegister,
              () -> {
                BankService.get()
                    .register(user, phone, reference, password, confirmation, accepted);
                return true;
              },
              saved -> {
                JOptionPane.showMessageDialog(
                    RegistrationFrame.this,
                    "Registered successfully. " + "Proceed to customer login.");
                Ui.open(RegistrationFrame.this, new LoginFrame());
              });
        });
    btnRegister.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnRegister.setBounds(455, 540, 171, 45);
    contentPane.add(btnRegister);

    btnRegister.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnRegister.setBounds(73, 628, 171, 45);
    contentPane.add(btnRegister);

    JButton btnCancel = new JButton("CLEAR");
    btnCancel.setBackground(Color.WHITE);
    btnCancel.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            textField.setText(null);
            textField_1.setText(null);
            textField_2.setText(null);
            passwordField.setText(null);
            passwordField_1.setText(null);
            chckbxNewCheckBox.setSelected(false);
          }
        });
    btnCancel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnCancel.setBounds(299, 628, 146, 45);
    contentPane.add(btnCancel);

    JButton btnNewButton = new JButton("LOG IN");
    btnNewButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Ui.open(RegistrationFrame.this, new LoginFrame());
          }
        });
    btnNewButton.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnNewButton.setBackground(Color.WHITE);
    btnNewButton.setBounds(221, 706, 118, 36);
    contentPane.add(btnNewButton);

    JLabel lblAlreadyAUser = new JLabel("Already a user?");
    lblAlreadyAUser.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    lblAlreadyAUser.setBounds(230, 683, 109, 21);
    contentPane.add(lblAlreadyAUser);

    JLabel lblNewLabel_1_1 = new JLabel("");
    lblNewLabel_1_1.setIcon(new ImageIcon(RegistrationFrame.class.getResource("/Images/maze.png")));
    lblNewLabel_1_1.setBounds(515, 84, 39, 45);
    contentPane.add(lblNewLabel_1_1);

    JButton btnNewButton_1 = new JButton("fictional demo information");
    btnNewButton_1.addActionListener(
        e -> {
          new Terms_and_Conditions(RegistrationFrame.this).setVisible(true);
        });
    btnNewButton_1.setHorizontalAlignment(SwingConstants.LEADING);
    btnNewButton_1.setBackground(UIManager.getColor("Button.light"));
    btnNewButton_1.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
    btnNewButton_1.setBounds(204, 580, 270, 24);
    contentPane.add(btnNewButton_1);

    JLabel lblNewLabel_1_1_1 = new JLabel("");
    lblNewLabel_1_1_1.setIcon(
        new ImageIcon(RegistrationFrame.class.getResource("/Images/maze.png")));
    lblNewLabel_1_1_1.setBounds(221, 84, 39, 45);
    contentPane.add(lblNewLabel_1_1_1);

    JButton btnRegister_1 = new JButton("");
    btnRegister_1.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            System.exit(0);
          }
        });
    btnRegister_1.setIcon(
        new ImageIcon(RegistrationFrame.class.getResource("/Images/Github-Octicons-X-16.32.png")));
    btnRegister_1.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnRegister_1.setBackground(Color.WHITE);
    btnRegister_1.setBounds(719, 10, 57, 41);
    contentPane.add(btnRegister_1);

    textField.setName("register.mobile");
    textField_1.setName("register.reference");
    textField_2.setName("register.username");
    passwordField.setName("register.password");
    passwordField_1.setName("register.confirm");
    chckbxNewCheckBox.setName("register.fictional");
    btnRegister.setName("register.submit");
    Ui.finish(this);
  }
}
