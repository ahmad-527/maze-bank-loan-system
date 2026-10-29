import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class AdminLoanReqs extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton btnRegister;
	private JButton btnBack;
	private JLabel lblBackToDashboard;
	private JLabel lblLogIn;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminLoanReqs frame = new AdminLoanReqs();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AdminLoanReqs() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminLoanReqs.class.getResource("/Images/maze.png")));
		setTitle("LOAN REQUESTS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLoanRequests = new JLabel("LOAN REQUESTS");
		lblLoanRequests.setFont(new Font("Roboto", Font.ITALIC, 27));
		lblLoanRequests.setBounds(291, 84, 222, 26);
		contentPane.add(lblLoanRequests);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 120, 724, 443);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Roboto", Font.PLAIN, 13));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Full Name", "Email", "Loan Amount", "Loan Type", "Loan Term", "Loan Purpose Inquiry"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		
		btnRegister = new JButton("");
		btnRegister.setIcon(new ImageIcon(AdminLoanReqs.class.getResource("/Images/Github-Octicons-X-16.32.png")));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnRegister.setFont(new Font("Aquire Light", Font.ITALIC, 27));
		btnRegister.setBackground(Color.WHITE);
		btnRegister.setBounds(719, 10, 57, 41);
		contentPane.add(btnRegister);
		
		btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				AdminDashboard A = new AdminDashboard();
				A.setVisible(true);
			}
		});
		btnBack.setFont(new Font("Roboto", Font.ITALIC, 27));
		btnBack.setBackground(Color.WHITE);
		btnBack.setBounds(334, 592, 117, 45);
		contentPane.add(btnBack);
		
		lblBackToDashboard = new JLabel("Back to Dashboard");
		lblBackToDashboard.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblBackToDashboard.setBounds(322, 573, 146, 21);
		contentPane.add(lblBackToDashboard);
		
		lblLogIn = new JLabel("MAZE BANK ");
		lblLogIn.setFont(new Font("Aquire Light", Font.BOLD, 42));
		lblLogIn.setBounds(268, 9, 264, 65);
		contentPane.add(lblLogIn);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AdminLoanReqs.class.getResource("/Images/maze.png")));
		lblNewLabel.setBounds(219, 21, 39, 45); 
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(AdminLoanReqs.class.getResource("/Images/maze.png")));
		lblNewLabel_1.setBounds(528, 21, 39, 45);
		contentPane.add(lblNewLabel_1);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "YOUR_DB_USER", "YOUR_DB_PASSWORD");
			System.out.println("Connection successful");
			java.sql.Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery("select * from LoanApplications");
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			while (rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String amount = rs.getString(3);
				String type = rs.getString(4);
				String term = rs.getString(5);
				String inquiry = rs.getString(6);
				model.addRow(new Object[] { name, email, amount, type, term, inquiry });
			}
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}
