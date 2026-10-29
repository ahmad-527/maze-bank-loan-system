import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class Start_Page extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start_Page frame = new Start_Page();
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
	public Start_Page() {
		setTitle("HOMEPAGE");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Start_Page.class.getResource("/Images/maze.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Button.background"));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogIn = new JLabel("MAZE BANK ");
		lblLogIn.setBounds(261, 62, 264, 65);
		lblLogIn.setFont(new Font("Aquire Light", Font.BOLD, 42));
		contentPane.add(lblLogIn);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setBounds(212, 71, 39, 45);
		lblNewLabel_1_1.setIcon(new ImageIcon(Start_Page.class.getResource("/Images/maze.png")));
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.setBounds(522, 71, 39, 45);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("");
		lblNewLabel_1_1_2.setBounds(522, 71, 39, 45);
		lblNewLabel_1_1_2.setIcon(new ImageIcon(Start_Page.class.getResource("/Images/maze.png")));
		contentPane.add(lblNewLabel_1_1_2);
		
		JLabel lblWelcomeToThe = new JLabel("Welcome to the MAZE Bank");
		lblWelcomeToThe.setBounds(27, 201, 311, 21);
		lblWelcomeToThe.setFont(new Font("Roboto", Font.PLAIN, 24));
		contentPane.add(lblWelcomeToThe);
		
		JLabel lblChooseWhatYou = new JLabel("Simply click on the relevant button to continue and access the desired section.");
		lblChooseWhatYou.setBounds(27, 244, 707, 21);
		lblChooseWhatYou.setFont(new Font("Roboto", Font.PLAIN, 20));
		contentPane.add(lblChooseWhatYou);
		
		JLabel lblWeAreHere = new JLabel("We are here to provide you with a seamless and user-friendly experience.");
		lblWeAreHere.setBounds(27, 266, 707, 21);
		lblWeAreHere.setFont(new Font("Roboto", Font.PLAIN, 20));
		contentPane.add(lblWeAreHere);
		
		JLabel lblAlreadyAUser = new JLabel("Already a User?");
		lblAlreadyAUser.setBounds(118, 360, 118, 21);
		lblAlreadyAUser.setFont(new Font("Roboto", Font.PLAIN, 16));
		contentPane.add(lblAlreadyAUser);
		
		JLabel lblNewToOur = new JLabel("New to our platform?");
		lblNewToOur.setBounds(413, 360, 159, 21);
		lblNewToOur.setFont(new Font("Roboto", Font.PLAIN, 16));
		contentPane.add(lblNewToOur);
		
		JButton btnRegister = new JButton("REGISTER");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				RegistrationFrame R=new RegistrationFrame();
				R.setVisible(true);
			}
		});
		btnRegister.setBounds(401, 381, 171, 45);
		btnRegister.setBackground(Color.WHITE);
		btnRegister.setFont(new Font("Roboto", Font.ITALIC, 27));
		contentPane.add(btnRegister);
		
		JButton btnAboutUs = new JButton("ABOUT US");
		btnAboutUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				AboutUsFrame A=new AboutUsFrame();
				A.setVisible(true);
			}
		});
		btnAboutUs.setBounds(93, 528, 191, 45);
		btnAboutUs.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnAboutUs.setBackground(Color.WHITE);
		contentPane.add(btnAboutUs);
		
		JLabel lblLearnMoreAbout = new JLabel("Learn more about our bank");
		lblLearnMoreAbout.setBounds(93, 506, 204, 21);
		lblLearnMoreAbout.setFont(new Font("Roboto", Font.PLAIN, 16));
		contentPane.add(lblLearnMoreAbout);
		
		JLabel lblHaveQueries = new JLabel("Need help?");
		lblHaveQueries.setBounds(460, 506, 88, 21);
		lblHaveQueries.setFont(new Font("Roboto", Font.PLAIN, 16));
		contentPane.add(lblHaveQueries);
		
		JButton btnContactUs = new JButton("CONTACT US");
		btnContactUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				ContactUsFrame C=new ContactUsFrame();
				C.setVisible(true);
			}
		});
		btnContactUs.setBounds(397, 528, 211, 45);
		btnContactUs.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnContactUs.setBackground(Color.WHITE);
		contentPane.add(btnContactUs);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Roboto", Font.PLAIN, 12));
		menuBar.setBorderPainted(false);
		menuBar.setBounds(105, 381, 146, 45);
		contentPane.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("  LOG IN ");
		mnNewMenu.setHorizontalAlignment(SwingConstants.CENTER);
		mnNewMenu.setBackground(UIManager.getColor("MenuBar.highlight"));
		mnNewMenu.setForeground(new Color(0, 0, 0));
		mnNewMenu.setFont(new Font("Roboto", Font.ITALIC, 27));
		menuBar.add(mnNewMenu);
		
		JMenuItem AdminLogin = new JMenuItem("Admin Login");
		AdminLogin.setFont(new Font("Roboto", Font.ITALIC, 12));
		AdminLogin.addActionListener(new ActionListener() {
			//linking to the admin login page
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				AdminLoginFrame L = new AdminLoginFrame();
				L.setVisible(true);
			}
		});
		mnNewMenu.add(AdminLogin);
		
		JMenuItem UserLogin = new JMenuItem("User Login");
		UserLogin.setFont(new Font("Roboto", Font.ITALIC, 12));
		UserLogin.addActionListener(new ActionListener() {
			//linking to the login frame
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				LoginFrame L=new LoginFrame();
				L.setVisible(true);
				
			}
		});
		mnNewMenu.add(UserLogin);
		
		JButton exit = new JButton("");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exit.setIcon(new ImageIcon(Start_Page.class.getResource("/Images/Github-Octicons-X-16.32.png")));
		exit.setFont(new Font("Roboto", Font.ITALIC, 27));
		exit.setBackground(Color.WHITE);
		exit.setBounds(699, 23, 57, 41);
		contentPane.add(exit);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
