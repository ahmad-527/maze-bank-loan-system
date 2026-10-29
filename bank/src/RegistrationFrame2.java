import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Window.Type;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class RegistrationFrame2 extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrationFrame2 frame = new RegistrationFrame2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegistrationFrame2() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(RegistrationFrame2.class.getResource("/Images/maze.png")));
		setType(Type.POPUP);
		setTitle("Registration Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRegistration = new JLabel("REGISTRATION FORM");
		lblRegistration.setFont(new Font("Roboto", Font.PLAIN, 30));
		lblRegistration.setBounds(242, 41, 301, 36);
		contentPane.add(lblRegistration);
		
		JLabel lblEnterYourCredentials = new JLabel("Enter your credentials below ");
		lblEnterYourCredentials.setFont(new Font("Roboto", Font.PLAIN, 13));
		lblEnterYourCredentials.setBounds(305, 125, 175, 14);
		contentPane.add(lblEnterYourCredentials);
		
		JLabel lblWelcomeToThe = new JLabel("WELCOME TO");
		lblWelcomeToThe.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblWelcomeToThe.setBounds(265, 93, 122, 36);
		contentPane.add(lblWelcomeToThe);
		
		JLabel lblMazeBank = new JLabel("Maze Bank");
		lblMazeBank.setFont(new Font("Aquire Light", Font.BOLD, 20));
		lblMazeBank.setBounds(389, 92, 128, 36);
		contentPane.add(lblMazeBank);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(UIManager.getColor("CheckBox.light"));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"OM (+968)", "BH (+973)", "KW (+965)", "QA (+974)", "KSA (+966)", "UAE (+971)"}));
		comboBox.setBounds(119, 214, 87, 21);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("What is your mobile number?");
		lblNewLabel.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblNewLabel.setBounds(120, 183, 256, 21);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(216, 214, 190, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("This number should be registered with your account.");
		lblNewLabel_1.setForeground(new Color(220, 20, 60));
		lblNewLabel_1.setFont(new Font("Roboto", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(119, 230, 277, 27);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblWhatIsYour = new JLabel("Enter Employee ID");
		lblWhatIsYour.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblWhatIsYour.setBounds(120, 291, 256, 21);
		contentPane.add(lblWhatIsYour);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(119, 322, 287, 21);
		contentPane.add(textField_1);
		
		JLabel lblCreateAUser = new JLabel("Create a User ID for account login");
		lblCreateAUser.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblCreateAUser.setBounds(119, 359, 256, 21);
		contentPane.add(lblCreateAUser);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(119, 390, 287, 21);
		contentPane.add(textField_2);
		
		JLabel lblCreateYourNew = new JLabel("Create your new Password");
		lblCreateYourNew.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblCreateYourNew.setBounds(120, 435, 256, 21);
		contentPane.add(lblCreateYourNew);
		
		JLabel lblConfirmYourNew = new JLabel("Confirm your new Password");
		lblConfirmYourNew.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblConfirmYourNew.setBounds(119, 509, 256, 21);
		contentPane.add(lblConfirmYourNew);
		
		final JCheckBox chckbxNewCheckBox = new JCheckBox("I accept the ");
		chckbxNewCheckBox.setFont(new Font("Roboto", Font.PLAIN, 11));
		chckbxNewCheckBox.setBounds(119, 580, 87, 21);
		contentPane.add(chckbxNewCheckBox);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(119, 466, 287, 21);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(119, 540, 287, 21);
		contentPane.add(passwordField_1);
		
		JButton btnRegister = new JButton("REGISTER");
		btnRegister.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String usr = textField_2.getText();
		        String num = textField.getText();
		        String id = textField_1.getText();
		        String pass = new String(passwordField.getPassword());
		        String pass2 = new String(passwordField_1.getPassword());
		        if (num.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Phone number is mandatory for Registration");
		        } else if (id.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Employee ID number cannot be empty");
		        } else if (usr.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Username cannot be empty");
		        } else if (pass.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Password field cannot be empty");
		        } else if (pass2.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Confirm Password field cannot be empty");
		        }
		          else if (!chckbxNewCheckBox.isSelected()){
		        	  JOptionPane.showMessageDialog(null, "Please accept the terms and conditions");
		        } else {
		            try {
		                Class.forName("oracle.jdbc.driver.OracleDriver");
		                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "YOUR_DB_USER", "YOUR_DB_PASSWORD");
		                System.out.println("Connection successful");
		                java.sql.Statement stat = con.createStatement();
		                stat.executeUpdate("insert into adRegister values ('" + usr + "','" + num + "','" + id + "','" + pass + "')");
		                //when clicked on register button the following message appears
		                JOptionPane.showMessageDialog(null, "Registered Successfully, Proceed to Login");//shows message
		                setVisible(false);
		                AdminLoginFrame A = new AdminLoginFrame();
		                A.setVisible(true);
		                JOptionPane.showMessageDialog(null, "Success");
		            } catch (SQLException ex) {
		                JOptionPane.showMessageDialog(null, "SQL Exception: " + ex.getMessage());
		            } catch (ClassNotFoundException ex) {
		                JOptionPane.showMessageDialog(null, "Class Not Found Exception: " + ex.getMessage());
		            }
		        }
		    }
		});
		btnRegister.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnRegister.setBounds(73, 628, 171, 45);
		contentPane.add(btnRegister);
		
		btnRegister.setFont(new Font("Aquire Light", Font.ITALIC, 27));
		btnRegister.setBounds(73, 628, 171, 45);
		contentPane.add(btnRegister);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(null);
				 textField_1.setText(null);
				textField_2.setText(null);
				passwordField.setText(null);
				passwordField_1.setText(null);
			}
		});
		btnCancel.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnCancel.setBounds(299, 628, 146, 45);
		contentPane.add(btnCancel);
		
		JButton btnNewButton = new JButton("LOG IN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				AdminLoginFrame A = new AdminLoginFrame();
				A.setVisible(rootPaneCheckingEnabled);
			}
		});
		btnNewButton.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setBounds(221, 706, 118, 36);
		contentPane.add(btnNewButton);
		
		JLabel lblAlreadyAUser = new JLabel("Already a user?");
		lblAlreadyAUser.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblAlreadyAUser.setBounds(230, 683, 109, 21);
		contentPane.add(lblAlreadyAUser);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setIcon(new ImageIcon(RegistrationFrame2.class.getResource("/Images/maze.png")));
		lblNewLabel_1_1.setBounds(515, 84, 39, 45);
		contentPane.add(lblNewLabel_1_1);
		
		JButton btnNewButton_1 = new JButton("terms and conditions");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Terms_and_Conditions T=new Terms_and_Conditions();
				T.setVisible(true);
			}
		});
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEADING);
		btnNewButton_1.setBackground(UIManager.getColor("Button.light"));
		btnNewButton_1.setFont(new Font("Roboto", Font.PLAIN, 11));
		btnNewButton_1.setBounds(204, 580, 146, 21);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.setIcon(new ImageIcon(RegistrationFrame2.class.getResource("/Images/maze.png")));
		lblNewLabel_1_1_1.setBounds(221, 84, 39, 45);
		contentPane.add(lblNewLabel_1_1_1);
		
		JButton btnRegister_1 = new JButton("");
		btnRegister_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnRegister_1.setIcon(new ImageIcon(RegistrationFrame2.class.getResource("/Images/Github-Octicons-X-16.32.png")));
		btnRegister_1.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnRegister_1.setBackground(Color.WHITE);
		btnRegister_1.setBounds(719, 10, 57, 41);
		contentPane.add(btnRegister_1);
	}
}
