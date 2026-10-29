import java.sql.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserDashboard extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserDashboard frame = new UserDashboard();
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
	public UserDashboard() {
		setTitle("User Dashboard");
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserDashboard.class.getResource("/Images/maze.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogIn = new JLabel("MAZE BANK ");
		lblLogIn.setFont(new Font("Aquire Light", Font.BOLD, 42));
		lblLogIn.setBounds(261, 88, 264, 65);
		contentPane.add(lblLogIn);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(UserDashboard.class.getResource("/Images/maze.png")));
		lblNewLabel_1.setBounds(208, 97, 39, 45);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setIcon(new ImageIcon(UserDashboard.class.getResource("/Images/maze.png")));
		lblNewLabel_1_1.setBounds(535, 97, 39, 45);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblWelcomeToThe = new JLabel("Welcome to the MAZE Bank User Dashboard");
		lblWelcomeToThe.setFont(new Font("Roboto", Font.PLAIN, 20));
		lblWelcomeToThe.setBounds(177, 152, 431, 21);
		contentPane.add(lblWelcomeToThe);
		
		JButton btnCustomerQueries = new JButton("CUSTOMER QUERIES");
		btnCustomerQueries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				CustomerQueries L=new CustomerQueries();
				L.setVisible(true);
			}
		});
		btnCustomerQueries.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnCustomerQueries.setBackground(Color.WHITE);
		btnCustomerQueries.setBounds(237, 437, 311, 45);
		contentPane.add(btnCustomerQueries);
		
		JButton btnLoanApplication = new JButton("REQUEST LOAN");
		btnLoanApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				LoanApplicationFrame L=new LoanApplicationFrame();
				L.setVisible(true);
			}
		});
		btnLoanApplication.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnLoanApplication.setBackground(Color.WHITE);
		btnLoanApplication.setBounds(237, 315, 311, 45);
		contentPane.add(btnLoanApplication);
		
		JLabel lblToApplyFor = new JLabel("To apply for a Loan");
		lblToApplyFor.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblToApplyFor.setBounds(320, 295, 145, 21);
		contentPane.add(lblToApplyFor);
		
		JLabel lblAnyDifficultiesYou = new JLabel("Have questions? Let us know");
		lblAnyDifficultiesYou.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblAnyDifficultiesYou.setBounds(286, 418, 214, 21);
		contentPane.add(lblAnyDifficultiesYou);
		
		JButton btnRegister_1 = new JButton("");
		btnRegister_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnRegister_1.setIcon(new ImageIcon(UserDashboard.class.getResource("/Images/Github-Octicons-X-16.32.png")));
		btnRegister_1.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnRegister_1.setBackground(Color.WHITE);
		btnRegister_1.setBounds(719, 10, 57, 41);
		contentPane.add(btnRegister_1);
		
		JButton btnSignOut = new JButton("SIGN OUT");
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Start_Page S =new Start_Page();
				S.setVisible(true);
			}
		});
		btnSignOut.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnSignOut.setBackground(Color.WHITE);
		btnSignOut.setBounds(294, 559, 206, 45);
		contentPane.add(btnSignOut);
		
		JLabel lblApprovedisapproveLoansRequested_1_1 = new JLabel("Back to home");
		lblApprovedisapproveLoansRequested_1_1.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblApprovedisapproveLoansRequested_1_1.setBounds(347, 538, 107, 21);
		contentPane.add(lblApprovedisapproveLoansRequested_1_1);
	}
}
