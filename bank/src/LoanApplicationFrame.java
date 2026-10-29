import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class LoanApplicationFrame extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JTextField mail;
	private JTextField amount;
	private JTextField inquiry;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoanApplicationFrame frame = new LoanApplicationFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoanApplicationFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoanApplicationFrame.class.getResource("/Images/maze.png")));
		setTitle("Loan Application Form");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LOAN APPLICATION FORM");
		lblNewLabel.setFont(new Font("Roboto", Font.ITALIC, 27));
		lblNewLabel.setBounds(230, 65, 332, 26);
		contentPane.add(lblNewLabel);
		
		JLabel lblPleaseProvideThe = new JLabel("Please provide the following information to apply for a loan.");
		lblPleaseProvideThe.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblPleaseProvideThe.setBounds(44, 165, 404, 21);
		contentPane.add(lblPleaseProvideThe);
		
		JLabel lblFullName = new JLabel("Full Name:");
		lblFullName.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblFullName.setBounds(100, 210, 71, 21);
		contentPane.add(lblFullName);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblEmail.setBounds(124, 258, 47, 21);
		contentPane.add(lblEmail);
		
		JLabel lblFullName_1_1 = new JLabel("Loan Amount:");
		lblFullName_1_1.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblFullName_1_1.setBounds(77, 305, 94, 21);
		contentPane.add(lblFullName_1_1);
		
		JLabel lblFullName_1_2 = new JLabel("Loan Type:");
		lblFullName_1_2.setFont(new Font("Roboto", Font.PLAIN, 15));
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
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Personal loan", "Auto loan", "Mortgage loan", "Investment loan"}));
		comboBox.setBounds(181, 353, 287, 21);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setIcon(new ImageIcon(LoanApplicationFrame.class.getResource("/Images/maze.png")));
		lblNewLabel_1_1.setBounds(220, 10, 39, 45);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblFullName_1_2_1 = new JLabel("Loan Term:");
		lblFullName_1_2_1.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblFullName_1_2_1.setBounds(93, 392, 78, 21);
		contentPane.add(lblFullName_1_2_1);
		
		final JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setModel(new DefaultComboBoxModel<String>(new String[] {"6 months", "12 months", "18 months", "24 months", "30 months", "36 months", "42 months", "48 months"}));
		comboBox_1.setBounds(181, 393, 287, 21);
		contentPane.add(comboBox_1);
		
		JLabel lblFullName_1_2_1_1 = new JLabel("Loan Purpose \r\nInquiry:");
		lblFullName_1_2_1_1.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblFullName_1_2_1_1.setBounds(8, 524, 163, 21);
		contentPane.add(lblFullName_1_2_1_1);
		
		inquiry = new JTextField();
		inquiry.setFont(new Font("Roboto", Font.PLAIN, 10));
		inquiry.setToolTipText("Discuss the purpose of your Loan");
		inquiry.setHorizontalAlignment(SwingConstants.LEFT);
		inquiry.setBounds(181, 453, 287, 166);
		contentPane.add(inquiry);
		inquiry.setColumns(10);
		
		JButton btnSubmit = new JButton("SUBMIT");
		btnSubmit.setBackground(Color.WHITE);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nam = name.getText();
				String email = mail.getText();
				String amt = amount.getText();
				String inquire = inquiry.getText();
				
				Object selectedValue1 = comboBox.getSelectedItem();
				Object selectedValue2 = comboBox_1.getSelectedItem();
				
				String value1 = selectedValue1.toString();
				String value2 = selectedValue2.toString();
				
				if (nam.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Full Nsme is mandatory for Loan Application");
				} else if (email.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Email cannot be empty");
				} else if (amt.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Mention the loan amount in OMR");
				} else if (inquire.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill Loan Purpose Inquiry");
				} else {
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "YOUR_DB_USER", "YOUR_DB_PASSWORD");
						System.out.println("Connection successful");
						java.sql.Statement stat = con.createStatement();
						stat.executeUpdate("insert into LoanApplications values ('" + nam + "', '" + email + "', '" + amt + "', '" + value1 + "', '" + value2 + "', '" + inquire + "')");
						JOptionPane.showMessageDialog(null, "Your request has been submitted successfully");
						con.close();
						setVisible(false);
						UserDashboard S =new UserDashboard();
						S.setVisible(true);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "SQL Exception occurred");
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(null, "Class Not Found Exception occurred");
						e1.printStackTrace();
					}
				}
			}
		});
		btnSubmit.setFont(new Font("Roboto", Font.ITALIC, 15));
		btnSubmit.setBounds(255, 629, 97, 26);
		contentPane.add(btnSubmit);
		
		JLabel lblLogIn = new JLabel("MAZE BANK ");
		lblLogIn.setFont(new Font("Aquire Light", Font.BOLD, 42));
		lblLogIn.setBounds(261, -2, 264, 65);
		contentPane.add(lblLogIn);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.setIcon(new ImageIcon(LoanApplicationFrame.class.getResource("/Images/maze.png")));
		lblNewLabel_1_1_1.setBounds(526, 10, 39, 45);
		contentPane.add(lblNewLabel_1_1_1);
		
		JButton btnRegister_1 = new JButton("");
		btnRegister_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnRegister_1.setIcon(new ImageIcon(LoanApplicationFrame.class.getResource("/Images/Github-Octicons-X-16.32.png")));
		btnRegister_1.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnRegister_1.setBackground(Color.WHITE);
		btnRegister_1.setBounds(719, 10, 57, 41);
		contentPane.add(btnRegister_1);
	}
}
