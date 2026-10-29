import java.awt.*;
import javax.swing.*;

class InformationFrame extends JFrame {
  InformationFrame(String title, String information) {
    setTitle(title);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    JPanel body = new JPanel(new BorderLayout(18, 18));
    body.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
    setContentPane(body);
    JLabel heading =
        new JLabel(
            "MAZE BANK · " + title,
            new ImageIcon(getClass().getResource("/Images/maze.png")),
            SwingConstants.LEFT);
    heading.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
    body.add(heading, BorderLayout.NORTH);
    JTextArea text = new JTextArea(information);
    text.setEditable(false);
    text.setLineWrap(true);
    text.setWrapStyleWord(true);
    text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
    text.setBackground(body.getBackground());
    text.setCaretPosition(0);
    body.add(new JScrollPane(text), BorderLayout.CENTER);
    JButton home = new JButton("Back to home");
    home.addActionListener(e -> Ui.signOut(this));
    body.add(home, BorderLayout.SOUTH);
    Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    setSize(Math.min(780, screen.width - 40), Math.min(650, screen.height - 50));
    setLocationRelativeTo(null);
  }
}
