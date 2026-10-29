import java.awt.*;
import javax.swing.*;

public class Terms_and_Conditions extends JDialog {
  public Terms_and_Conditions() {
    this(null);
  }

  public Terms_and_Conditions(Window owner) {
    super(owner, "Fictional demo information", Dialog.ModalityType.APPLICATION_MODAL);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    JPanel body = new JPanel(new BorderLayout(16, 16));
    body.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
    setContentPane(body);
    JTextArea text =
        new JTextArea(
            "This app demonstrates coursework workflows using fictional information. It is not a"
                + " bank and cannot submit a real loan application or make a credit decision.\n\n"
                + "Do not enter real card numbers, PINs, government identifiers, banking passwords"
                + " or sensitive financial details. Account/staff references must begin DEMO-.\n\n"
                + "The example customer and administrator credentials are deliberately public. Use"
                + " a fresh password created only for this demo. Records are stored in a local H2"
                + " database, not transmitted to a bank. This is not a production security or"
                + " encryption claim.\n\n"
                + "The confirmation checkbox acknowledges these demo instructions; it does not"
                + " enter into a banking contract.");
    text.setEditable(false);
    text.setLineWrap(true);
    text.setWrapStyleWord(true);
    text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    text.setCaretPosition(0);
    body.add(new JScrollPane(text), BorderLayout.CENTER);
    JButton close = new JButton("Close");
    close.addActionListener(e -> dispose());
    body.add(close, BorderLayout.SOUTH);
    setSize(680, 500);
    setLocationRelativeTo(owner);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new Terms_and_Conditions().setVisible(true));
  }
}
