import java.sql.*;
import java.awt.EventQueue;
import java.awt.Window;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class CustomerQueries extends JFrame {

  private JPanel contentPane;
  private JTextField txtUserName;
  private JTextField txtDescription;
  private JTable table;

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          CustomerQueries frame = new CustomerQueries();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  public CustomerQueries() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 800, 600);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(10, 10, 764, 358);
    contentPane.add(scrollPane);

    table = new JTable();
    table.setModel(new DefaultTableModel(
      new Object[][] {},
      new String[] { "User Name", "Description" }
    ));

    // Adjust the width of the "User Name" column
    TableColumn userNameColumn = table.getColumnModel().getColumn(0);
    userNameColumn.setPreferredWidth(100); // Set the desired width here

    scrollPane.setViewportView(table);


    JLabel lblUserName = new JLabel("User Name:");
    lblUserName.setFont(new Font("Roboto", Font.PLAIN, 12));
    lblUserName.setBounds(10, 390, 80, 14);
    contentPane.add(lblUserName);

    txtUserName = new JTextField();
    txtUserName.setBounds(100, 387, 200, 20);
    contentPane.add(txtUserName);
    txtUserName.setColumns(10);

    JLabel lblDescription = new JLabel("Description:");
    lblDescription.setFont(new Font("Roboto", Font.PLAIN, 12));
    lblDescription.setBounds(10, 420, 80, 14);
    contentPane.add(lblDescription);

    txtDescription = new JTextField();
    txtDescription.setBounds(100, 417, 500, 20);
    contentPane.add(txtDescription);
    txtDescription.setColumns(10);

    JButton btnSubmit = new JButton("Submit");
    btnSubmit.setFont(new Font("Roboto", Font.ITALIC, 12));
    btnSubmit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String userName = txtUserName.getText();
        String description = txtDescription.getText();

        if (userName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Username is mandatory");
		} else if (description.isEmpty()) {
			JOptionPane.showMessageDialog(null, "description cannot be empty");
		}
		else {// Save the data to the database
	        
	        try {
	          Class.forName("oracle.jdbc.driver.OracleDriver");
	          Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "YOUR_DB_USER", "YOUR_DB_PASSWORD");
	          System.out.println("Connection successful");
	          java.sql.Statement stat = con.createStatement();
	          stat.executeUpdate("INSERT INTO Questions VALUES ('" + userName + "', '" + description + "')");
	          con.close();
	        } catch (SQLException ex) {
	          ex.printStackTrace();
	        } catch (ClassNotFoundException ex) {
	          ex.printStackTrace();
	        }
			

	        // Update the table with the new query
	        DefaultTableModel model = (DefaultTableModel) table.getModel();
	        model.addRow(new Object[] { userName, description });

	        // Clear the input fields
	        txtUserName.setText("");
	        txtDescription.setText("");
	      }
	    }});
	    btnSubmit.setBounds(620, 416, 89, 23);
	    contentPane.add(btnSubmit);

	    // Retrieve data from the database and display in the table
	    try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "YOUR_DB_USER", "YOUR_DB_PASSWORD");
	        System.out.println("Connection successful");
	        java.sql.Statement stat = con.createStatement();
	        ResultSet rs = stat.executeQuery("SELECT * FROM Questions");

	        DefaultTableModel model = (DefaultTableModel) table.getModel();
	        while (rs.next()) {
	            String userName = rs.getString("uname");
	            String description = rs.getString("description");
	            model.addRow(new Object[]{userName, description});
	        }

	        con.close();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    } catch (ClassNotFoundException ex) {
	        ex.printStackTrace();
	    }
	    
    }
	}
        
        