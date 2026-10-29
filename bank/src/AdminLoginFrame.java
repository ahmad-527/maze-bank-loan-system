import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class AdminLoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminLoginFrame frame = new AdminLoginFrame();
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
	public AdminLoginFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminLoginFrame.class.getResource("/Images/maze.png")));
		setTitle("ADMIN LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 802, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel contentPane_1 = new JPanel();
		contentPane_1.setLayout(null);
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane_1.setBounds(0, 0, 788, 663);
		contentPane.add(contentPane_1);
		
		JLabel lblAdminUsername = new JLabel("Admin Username:");
		lblAdminUsername.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblAdminUsername.setBounds(150, 214, 136, 32);
		contentPane_1.add(lblAdminUsername);
		
		JLabel lblAdminPassword = new JLabel("Admin Password:");
		lblAdminPassword.setFont(new Font("Roboto", Font.PLAIN, 16));
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
		lblLogIn.setFont(new Font("Aquire Light", Font.BOLD, 42));
		lblLogIn.setBounds(274, 27, 264, 65);
		contentPane_1.add(lblLogIn);
		
		JLabel lblWithYourExisting = new JLabel("with your existing account");
		lblWithYourExisting.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblWithYourExisting.setBounds(293, 141, 216, 32);
		contentPane_1.add(lblWithYourExisting);
		
		JButton btnNewButton = new JButton("LOG IN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id =  textField.getText();
				String pass= passwordField.getText();
				
				
				if (id.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter your Username");
				}
				else if  (pass.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter your password");
				}
				

			    else {  try {
	                // Establishing  connection to database
	                Class.forName("oracle.jdbc.driver.OracleDriver");
	                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "YOUR_DB_USER", "YOUR_DB_PASSWORD");
	                System.out.println("Connection successful");

	                // Creating a statement and executing the query
	                Statement stat = con.createStatement();
	                ResultSet rs = stat.executeQuery("select * from adRegister where adname = '" + id + "' and pass = '" + pass + "'");

	                boolean found = false;
	                while (rs.next()) {
	                    String dbuname = rs.getString(1);
	                    String dbpass = rs.getString(2);
	                    if (id.equals(dbuname) && pass.equals(dbpass)) {
	                        found = true;
	                        break;
	                    }
	                }
	                if (found) {
	                    // Displaying welcome message and showing the UserDashboard
	                    JOptionPane.showMessageDialog(null, "Welcome " + id + "!" + "\n" + "Click OK to proceed");
	               

	                	
				    	
				    	setVisible(false);
					    AdminDashboard A=new AdminDashboard();
					    A.setVisible(true);
	                } else {
	                    // Displaying an error message for invalid username or password
	                    JOptionPane.showMessageDialog(null, "Invalid User name or password. Please try again");
	                }
	            } catch (Exception ev) {
	                // Handle any exceptions that occur during the login process
	                System.out.println("Login failed");
	            }
			    
				}
			}
		});
		btnNewButton.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(233, 340, 146, 45);
		contentPane_1.add(btnNewButton);
		
		JLabel lblLogIn_2 = new JLabel("LOG IN");
		lblLogIn_2.setFont(new Font("Roboto", Font.PLAIN, 26));
		lblLogIn_2.setBounds(347, 108, 92, 39);
		contentPane_1.add(lblLogIn_2);
		
		JButton btnCancel = new JButton("CLEAR");
		btnCancel.setForeground(Color.RED);
		btnCancel.setBackground(Color.WHITE);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(null);
				passwordField.setText(null);
			}
		});
		btnCancel.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnCancel.setBounds(412, 340, 146, 45);
		contentPane_1.add(btnCancel);
		
		JButton btnRegister = new JButton("REGISTER");
		btnRegister.setBackground(Color.WHITE);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				RegistrationFrame2 A=new RegistrationFrame2();
				A.setVisible(true);
			}
		});
		btnRegister.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnRegister.setBounds(307, 446, 171, 45);
		contentPane_1.add(btnRegister);
		
		JLabel lblNewUser = new JLabel("New User?");
		lblNewUser.setFont(new Font("Roboto", Font.PLAIN, 16));
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
		btnRegister_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		btnRegister_1.setIcon(new ImageIcon(AdminLoginFrame.class.getResource("/Images/Github-Octicons-X-16.32.png")));
		btnRegister_1.setFont(new Font("Aquire Light", Font.ITALIC, 27));
		btnRegister_1.setBackground(Color.WHITE);
		btnRegister_1.setBounds(710, 10, 57, 41);
		contentPane_1.add(btnRegister_1);
		
		JButton btnBackToHome = new JButton("HOME");
		btnBackToHome.setBackground(Color.WHITE);
		btnBackToHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Start_Page S = new Start_Page();
				S.setVisible(true);
			}
		});
		btnBackToHome.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnBackToHome.setBounds(335, 555, 117, 45);
		contentPane_1.add(btnBackToHome);
		
		JLabel lblBackToHome = new JLabel("Back to home");
		lblBackToHome.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblBackToHome.setBounds(347, 525, 105, 32);
		contentPane_1.add(lblBackToHome);
	}
}
