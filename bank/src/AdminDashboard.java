import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminDashboard extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminDashboard frame = new AdminDashboard();
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
	public AdminDashboard() {
		setTitle("Admin Dashboard");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminDashboard.class.getResource("/Images/maze.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setIcon(new ImageIcon(AdminDashboard.class.getResource("/Images/maze.png")));
		lblNewLabel_1_1.setBounds(202, 93, 39, 45);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.setIcon(new ImageIcon(AdminDashboard.class.getResource("/Images/maze.png")));
		lblNewLabel_1_1_1.setBounds(539, 93, 39, 45);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblLogIn = new JLabel("MAZE BANK ");
		lblLogIn.setFont(new Font("Aquire Light", Font.BOLD, 42));
		lblLogIn.setBounds(265, 81, 264, 65);
		contentPane.add(lblLogIn);
		
		JLabel lblWelcomeToThe = new JLabel("Welcome to the MAZE Bank  Admin Dashboard");
		lblWelcomeToThe.setFont(new Font("Roboto", Font.PLAIN, 20));
		lblWelcomeToThe.setBounds(187, 148, 431, 21);
		contentPane.add(lblWelcomeToThe);
		
		JLabel lblPleaseSelectA = new JLabel("Please select a task to execute using the buttons given below:");
		lblPleaseSelectA.setFont(new Font("Roboto", Font.PLAIN, 20));
		lblPleaseSelectA.setBounds(10, 223, 567, 31);
		contentPane.add(lblPleaseSelectA);
		
		JButton btnLoanRequests = new JButton("LOAN REQUESTS");
		btnLoanRequests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				AdminLoanReqs A=new AdminLoanReqs();
				A.setVisible(true);
				
			}
		});
		btnLoanRequests.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnLoanRequests.setBackground(Color.WHITE);
		btnLoanRequests.setBounds(261, 328, 264, 45);
		contentPane.add(btnLoanRequests);
		
		JLabel lblApprovedisapproveLoansRequested = new JLabel("Review Loan applications");
		lblApprovedisapproveLoansRequested.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblApprovedisapproveLoansRequested.setBounds(290, 305, 206, 21);
		contentPane.add(lblApprovedisapproveLoansRequested);
		
		JButton btnCustomerQueries = new JButton("CUSTOMER QUERIES");
		btnCustomerQueries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				CustomerQueries A= new CustomerQueries();
				A.setVisible(true);
			}
		});
		btnCustomerQueries.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnCustomerQueries.setBackground(Color.WHITE);
		btnCustomerQueries.setBounds(237, 471, 311, 45);
		contentPane.add(btnCustomerQueries);
		
		JLabel lblApprovedisapproveLoansRequested_1 = new JLabel("Review customer queries");
		lblApprovedisapproveLoansRequested_1.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblApprovedisapproveLoansRequested_1.setBounds(290, 452, 206, 21);
		contentPane.add(lblApprovedisapproveLoansRequested_1);
		
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
		btnSignOut.setBounds(290, 607, 206, 45);
		contentPane.add(btnSignOut);
		
		JLabel lblApprovedisapproveLoansRequested_1_1 = new JLabel("Back to home");
		lblApprovedisapproveLoansRequested_1_1.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblApprovedisapproveLoansRequested_1_1.setBounds(339, 587, 107, 21);
		contentPane.add(lblApprovedisapproveLoansRequested_1_1);
		
		JButton btnRegister_1 = new JButton("");
		btnRegister_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnRegister_1.setIcon(new ImageIcon(AdminDashboard.class.getResource("/Images/Github-Octicons-X-16.32.png")));
		btnRegister_1.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnRegister_1.setBackground(Color.WHITE);
		btnRegister_1.setBounds(719, 10, 57, 41);
		contentPane.add(btnRegister_1);
	}
}
