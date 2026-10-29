import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JEditorPane;
import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ContactUsFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ContactUsFrame frame = new ContactUsFrame();
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
	public ContactUsFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ContactUsFrame.class.getResource("/Images/maze.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 802, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel contentPane_1 = new JPanel();
		contentPane_1.setLayout(null);
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane_1.setBackground(UIManager.getColor("Button.background"));
		contentPane_1.setBounds(0, 0, 788, 663);
		contentPane.add(contentPane_1);
		
		JLabel lblLogIn = new JLabel("MAZE BANK ");
		lblLogIn.setBackground(UIManager.getColor("Button.light"));
		lblLogIn.setFont(new Font("Aquire Light", Font.BOLD, 42));
		lblLogIn.setBounds(257, 87, 264, 65);
		contentPane_1.add(lblLogIn);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setBounds(208, 98, 39, 54);
		contentPane_1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.setBounds(517, 103, 39, 49);
		contentPane_1.add(lblNewLabel_1_1_1);
		
		JButton btnHome = new JButton("HOME");
		btnHome.setFont(new Font("Aquire Light", Font.ITALIC, 27));
		btnHome.setBounds(42, 682, 205, 45);
		contentPane_1.add(btnHome);
		
		JButton btnContactUs = new JButton("CONTACT US");
		btnContactUs.setFont(new Font("Aquire Light", Font.ITALIC, 27));
		btnContactUs.setBounds(534, 682, 205, 45);
		contentPane_1.add(btnContactUs);
		
		JButton btnBackToHome = new JButton("BACK TO HOME");
		btnBackToHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Start_Page S=new Start_Page();
				S.setVisible(true);
			}
		});
		btnBackToHome.setFont(new Font("Roboto", Font.ITALIC, 15));
		btnBackToHome.setBounds(294, 609, 203, 26);
		contentPane_1.add(btnBackToHome);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("");
		lblNewLabel_1_1_2.setIcon(new ImageIcon(ContactUsFrame.class.getResource("/Images/maze.png")));
		lblNewLabel_1_1_2.setBounds(208, 96, 39, 45);
		contentPane_1.add(lblNewLabel_1_1_2);
		
		JLabel lblNewLabel_1_1_3 = new JLabel("");
		lblNewLabel_1_1_3.setIcon(new ImageIcon(ContactUsFrame.class.getResource("/Images/maze.png")));
		lblNewLabel_1_1_3.setBounds(517, 96, 39, 45);
		contentPane_1.add(lblNewLabel_1_1_3);
		
		JEditorPane dtrpnContactUsFor = new JEditorPane();
		dtrpnContactUsFor.setText("Contact Us:\r\nFor any inquiries or assistance, please feel free to reach out to us:\r\n\r\nIT Support Staff:\r\nAhmad Siddiqui\r\nPhone: [PRIVATE PHONE REMOVED]\r\nEmail: [PRIVATE STUDENT EMAIL REMOVED]\r\n\r\nCustomer Support Staff:\r\nBassam Al Bulushi\r\nPhone: [PRIVATE PHONE REMOVED]\r\nEmail: [PRIVATE STUDENT EMAIL REMOVED]\r\n\r\nWe are here to help and provide support regarding your bank loan inquiries and any related concerns.");
		dtrpnContactUsFor.setFont(new Font("Roboto", Font.PLAIN, 18));
		dtrpnContactUsFor.setBackground(UIManager.getColor("Button.background"));
		dtrpnContactUsFor.setBounds(42, 151, 736, 440);
		contentPane_1.add(dtrpnContactUsFor);
	}
}
