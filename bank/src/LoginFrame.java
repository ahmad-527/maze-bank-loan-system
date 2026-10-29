import java.awt.BorderLayout;
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
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.Toolkit;
import java.awt.Color;

public class LoginFrame extends JFrame {

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
					LoginFrame frame = new LoginFrame();
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
	public LoginFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginFrame.class.getResource("/Images/maze.png")));
		setTitle("USER LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 802, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setBounds(204, 214, 82, 32);
		lblNewLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(204, 275, 82, 20);
		lblPassword.setFont(new Font("Roboto", Font.PLAIN, 16));
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
		lblLogIn.setFont(new Font("Aquire Light", Font.BOLD, 42));
		contentPane.add(lblLogIn);
		
		JLabel lblWithYourExisting = new JLabel("with your existing account");
		lblWithYourExisting.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblWithYourExisting.setBounds(293, 141, 216, 32);
		contentPane.add(lblWithYourExisting);
		
		JButton btnNewButton = new JButton("LOG IN");
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String id = textField.getText();
		        String pass = passwordField.getText();

		        if (id.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please enter your Username");
		        } else if (pass.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please enter your password");
		        } else {
		            try {
		                // Establishing  connection to database
		                Class.forName("oracle.jdbc.driver.OracleDriver");
		                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "YOUR_DB_USER", "YOUR_DB_PASSWORD");
		                System.out.println("Connection successful");

		                // Creating a statement and executing the query
		                Statement stat = con.createStatement();
		                ResultSet rs = stat.executeQuery("select * from Register where uname = '" + id + "' and pass = '" + pass + "'");

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
		                    UserDashboard r = new UserDashboard();
		                    r.setVisible(true);

		                
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

		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnNewButton.setBounds(233, 340, 146, 45);
		contentPane.add(btnNewButton);

		JLabel lblLogIn_2 = new JLabel("LOG IN");
		lblLogIn_2.setFont(new Font("Roboto", Font.PLAIN, 26));
		lblLogIn_2.setBounds(347, 108, 92, 39);
		contentPane.add(lblLogIn_2);

		
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
		contentPane.add(btnCancel);
		
		JButton btnRegister = new JButton("REGISTER");
		btnRegister.setBackground(Color.WHITE);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				RegistrationFrame A=new RegistrationFrame();
				A.setVisible(true);
			}
		});
		btnRegister.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnRegister.setBounds(307, 446, 171, 45);
		contentPane.add(btnRegister);
		
		JLabel lblNewUser = new JLabel("New User?");
		lblNewUser.setFont(new Font("Roboto", Font.PLAIN, 16));
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
		btnRegister_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnRegister_1.setIcon(new ImageIcon(LoginFrame.class.getResource("/Images/Github-Octicons-X-16.32.png")));
		btnRegister_1.setFont(new Font("Aquire Light", Font.ITALIC, 27));
		btnRegister_1.setBackground(Color.WHITE);
		btnRegister_1.setBounds(721, 10, 57, 41);
		contentPane.add(btnRegister_1);
		
		JLabel lblBackToHome = new JLabel("Back to home");
		lblBackToHome.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblBackToHome.setBounds(341, 533, 105, 32);
		contentPane.add(lblBackToHome);
		
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
		btnBackToHome.setBounds(335, 560, 117, 45);
		contentPane.add(btnBackToHome);
	}
}
