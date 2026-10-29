import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JSpinner;
import java.awt.Toolkit;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Terms_and_Conditions extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Terms_and_Conditions frame = new Terms_and_Conditions();
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
	public Terms_and_Conditions() {
		setTitle("Terms and Conditions");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Terms_and_Conditions.class.getResource("/Images/maze.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Button.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setBounds(208, 125, 39, 45);
		lblNewLabel_1_1.setIcon(new ImageIcon(Terms_and_Conditions.class.getResource("/Images/maze.png")));
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.setBounds(519, 125, 39, 45);
		lblNewLabel_1_1_1.setIcon(new ImageIcon(Terms_and_Conditions.class.getResource("/Images/maze.png")));
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblLogIn = new JLabel("MAZE BANK ");
		lblLogIn.setBounds(261, 113, 264, 65);
		lblLogIn.setFont(new Font("Aquire Light", Font.BOLD, 42));
		contentPane.add(lblLogIn);
		
		JEditorPane dtrpnTermsAndConditions = new JEditorPane();
		dtrpnTermsAndConditions.setText("Terms and Conditions:\r\n\r\nUser Registration: Users must register with a valid phone number and debit card number.\r\n\r\nLoan Application: Users must provide accurate and complete information in the loan application form.\r\n\r\nPrivacy and Data Security: User data will be handled securely and confidentially.\r\n\r\nLoan Approval and Disbursement: Loan approval is subject to the bank's criteria, and disbursement follows bank procedures.\r\n\r\nRepayment and Interest: Users are responsible for timely loan repayment based on agreed terms.\r\n\r\nSystem Availability and Liability: The bank strives for uninterrupted service but is not liable for system downtime or unauthorized access.");
		dtrpnTermsAndConditions.setFont(new Font("Roboto", Font.PLAIN, 18));
		dtrpnTermsAndConditions.setBackground(UIManager.getColor("Button.background"));
		dtrpnTermsAndConditions.setBounds(10, 205, 766, 440);
		contentPane.add(dtrpnTermsAndConditions);
		
		JButton btnBackToHome = new JButton("BACK TO HOME");
		btnBackToHome.setBackground(Color.WHITE);
		btnBackToHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Start_Page S=new Start_Page();
				S.setVisible(true);
			}
		});
		btnBackToHome.setFont(new Font("Roboto", Font.ITALIC, 15));
		btnBackToHome.setBounds(291, 677, 203, 26);
		contentPane.add(btnBackToHome);
	}
}
