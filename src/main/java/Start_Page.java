import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Start_Page extends JFrame {

  private JPanel contentPane;

  /** Launch the application. */
  public static void main(String[] args) {
    Ui.launch(Start_Page::new);
  }

  /** Create the frame. */
  public Start_Page() {
    Session.clear();

    setTitle("HOMEPAGE");
    setIconImage(
        Toolkit.getDefaultToolkit().getImage(Start_Page.class.getResource("/Images/maze.png")));
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
    lblLogIn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 42));
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
    lblWelcomeToThe.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
    contentPane.add(lblWelcomeToThe);

    JLabel lblChooseWhatYou =
        new JLabel("Choose customer registration or login to explore the demo.");
    lblChooseWhatYou.setBounds(27, 244, 707, 21);
    lblChooseWhatYou.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
    contentPane.add(lblChooseWhatYou);

    JLabel lblWeAreHere = new JLabel("Local educational demo — fictional information only.");
    lblWeAreHere.setBounds(27, 266, 707, 21);
    lblWeAreHere.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
    contentPane.add(lblWeAreHere);

    JLabel lblAlreadyAUser = new JLabel("Already a User?");
    lblAlreadyAUser.setBounds(118, 360, 118, 21);
    lblAlreadyAUser.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    contentPane.add(lblAlreadyAUser);

    JLabel lblNewToOur = new JLabel("New to our platform?");
    lblNewToOur.setBounds(413, 360, 159, 21);
    lblNewToOur.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    contentPane.add(lblNewToOur);

    JButton btnRegister = new JButton("REGISTER");
    btnRegister.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Ui.open(Start_Page.this, new RegistrationFrame());
          }
        });
    btnRegister.setBounds(401, 381, 171, 45);
    btnRegister.setBackground(Color.WHITE);
    btnRegister.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    contentPane.add(btnRegister);

    JButton btnAboutUs = new JButton("ABOUT US");
    btnAboutUs.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Ui.open(Start_Page.this, new AboutUsFrame());
          }
        });
    btnAboutUs.setBounds(93, 528, 191, 45);
    btnAboutUs.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnAboutUs.setBackground(Color.WHITE);
    contentPane.add(btnAboutUs);

    JLabel lblLearnMoreAbout = new JLabel("About this project");
    lblLearnMoreAbout.setBounds(93, 506, 204, 21);
    lblLearnMoreAbout.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    contentPane.add(lblLearnMoreAbout);

    JLabel lblHaveQueries = new JLabel("Need help?");
    lblHaveQueries.setBounds(460, 506, 88, 21);
    lblHaveQueries.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    contentPane.add(lblHaveQueries);

    JButton btnContactUs = new JButton("CONTACT US");
    btnContactUs.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Ui.open(Start_Page.this, new ContactUsFrame());
          }
        });
    btnContactUs.setBounds(397, 528, 211, 45);
    btnContactUs.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    btnContactUs.setBackground(Color.WHITE);
    contentPane.add(btnContactUs);

    JMenuBar menuBar = new JMenuBar();
    menuBar.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
    menuBar.setBorderPainted(false);
    menuBar.setBounds(105, 381, 146, 45);
    contentPane.add(menuBar);

    JMenu mnNewMenu = new JMenu("  LOG IN ");
    mnNewMenu.setHorizontalAlignment(SwingConstants.CENTER);
    mnNewMenu.setBackground(UIManager.getColor("MenuBar.highlight"));
    mnNewMenu.setForeground(new Color(0, 0, 0));
    mnNewMenu.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    menuBar.add(mnNewMenu);

    JMenuItem AdminLogin = new JMenuItem("Admin Login");
    AdminLogin.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
    AdminLogin.addActionListener(
        new ActionListener() {
          // linking to the admin login page
          public void actionPerformed(ActionEvent e) {
            Ui.open(Start_Page.this, new AdminLoginFrame());
          }
        });
    mnNewMenu.add(AdminLogin);

    JMenuItem UserLogin = new JMenuItem("User Login");
    UserLogin.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
    UserLogin.addActionListener(
        new ActionListener() {
          // linking to the login frame
          public void actionPerformed(ActionEvent e) {
            Ui.open(Start_Page.this, new LoginFrame());
          }
        });
    mnNewMenu.add(UserLogin);

    JButton exit = new JButton("");
    exit.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            System.exit(0);
          }
        });
    exit.setIcon(
        new ImageIcon(Start_Page.class.getResource("/Images/Github-Octicons-X-16.32.png")));
    exit.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 27));
    exit.setBackground(Color.WHITE);
    exit.setBounds(699, 23, 57, 41);
    contentPane.add(exit);

    JButton demo = new JButton("Demo accounts and local-data information");
    demo.setBounds(210, 680, 380, 36);
    contentPane.add(demo);
    demo.addActionListener(
        e ->
            JOptionPane.showMessageDialog(
                this,
                "Fictional local walkthrough accounts:\n"
                    + "Customer: demo-user / DemoUser!2026\n"
                    + "Admin: demo-admin / DemoAdmin!2026\n\n"
                    + "Use fictional information only. This app does not connect to a bank or make"
                    + " credit decisions. Data stays in the local demo folder.",
                "Maze Bank demo",
                JOptionPane.INFORMATION_MESSAGE));
    Ui.finish(this);
  }

  private static void addPopup(Component component, final JPopupMenu popup) {
    component.addMouseListener(
        new MouseAdapter() {
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
