import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.DropMode;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AboutUsFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AboutUsFrame frame = new AboutUsFrame();
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
	public AboutUsFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutUsFrame.class.getResource("/Images/maze.png")));
		setTitle("ABOUT US");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogIn = new JLabel("MAZE BANK ");
		lblLogIn.setFont(new Font("Aquire Light", Font.BOLD, 42));
		lblLogIn.setBounds(257, 87, 264, 65);
		contentPane.add(lblLogIn);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setIcon(new ImageIcon(AboutUsFrame.class.getResource("/Images/maze.png")));
		lblNewLabel_1_1.setBounds(208, 98, 39, 54);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.setIcon(new ImageIcon(AboutUsFrame.class.getResource("/Images/maze.png")));
		lblNewLabel_1_1_1.setBounds(517, 103, 39, 49);
		contentPane.add(lblNewLabel_1_1_1);
		
		JEditorPane dtrpnAtMazeBank = new JEditorPane();
		dtrpnAtMazeBank.setFont(new Font("Roboto", Font.PLAIN, 18));
		dtrpnAtMazeBank.setBackground(new Color(255, 255, 255));
		dtrpnAtMazeBank.setText("At Maze Bank, we provide personalized loan solutions to meet your financial needs. With auto loans, mortgage loans, personal loans, and investment loans, we help you achieve your goals.\r\n\r\nExpect:\r\n\r\n1.Customized options: We offer diverse loan types for car purchases, home financing, personal expenses, and investments.\r\n\r\n2.Seamless process: Our user-friendly interface makes applying for a loan easy. Our staff provides personalized support.\r\n\r\n3.Competitive rates: We offer affordable interest rates and flexible repayment terms.\r\n\r\n4.Reliable service: Our dedicated customer service team is ready to assist you.\r\n\r\nJoin Maze Bank to unlock financial possibilities. Visit our website or contact our \r\ncustomer service team.\r\n\r\nLet's navigate the maze together!");
		dtrpnAtMazeBank.setBounds(42, 188, 697, 462);
		contentPane.add(dtrpnAtMazeBank);
		
		JButton btnHome = new JButton("HOME");
		btnHome.setBackground(new Color(255, 255, 255));
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Start_Page S=new Start_Page();
				S.setVisible(true);
			}
		});
		btnHome.setFont(new Font("Aquire Light", Font.ITALIC, 27));
		btnHome.setBounds(42, 682, 205, 45);
		contentPane.add(btnHome);
		
		JButton btnContactUs = new JButton("CONTACT US");
		btnContactUs.setBackground(new Color(255, 255, 255));
		btnContactUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				ContactUsFrame F=new ContactUsFrame();
				F.setVisible(true);
			}
		});
		btnContactUs.setFont(new Font("Aquire Light", Font.ITALIC, 27));
		btnContactUs.setBounds(527, 682, 212, 45);
		contentPane.add(btnContactUs);
	}
}
