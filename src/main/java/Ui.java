import java.awt.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

final class Ui {
  static void open(JFrame previous, JFrame next) {
    previous.dispose();
    next.setVisible(true);
  }

  static void signOut(JFrame previous) {
    Session.clear();
    open(previous, new Start_Page());
  }

  static JFrame dashboard() {
    return Session.require().role() == Session.Role.ADMIN
        ? new AdminDashboard()
        : new UserDashboard();
  }

  static void error(Component owner, Throwable failure) {
    String message = failure.getMessage();
    JOptionPane.showMessageDialog(
        owner,
        message == null ? "Could not complete the action." : message,
        "Maze Bank demo",
        JOptionPane.ERROR_MESSAGE);
  }

  static <T> void background(JFrame owner, JButton button, Callable<T> action, Consumer<T> done) {
    if (button != null) button.setEnabled(false);
    new SwingWorker<T, Void>() {
      protected T doInBackground() throws Exception {
        return action.call();
      }

      protected void done() {
        if (!owner.isDisplayable()) return;
        if (button != null) button.setEnabled(true);
        try {
          done.accept(get());
        } catch (Exception failure) {
          Throwable cause =
              failure instanceof java.util.concurrent.ExecutionException
                  ? failure.getCause()
                  : failure;
          error(owner, cause);
        }
      }
    }.execute();
  }

  static void table(JTable table, String[] columns, java.util.List<Object[]> rows) {
    DefaultTableModel model =
        new DefaultTableModel(columns, 0) {
          public boolean isCellEditable(int r, int c) {
            return false;
          }
        };
    for (Object[] row : rows) model.addRow(row);
    table.setModel(model);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    javax.swing.table.DefaultTableCellRenderer renderer =
        new javax.swing.table.DefaultTableCellRenderer();
    renderer.putClientProperty("html.disable", Boolean.TRUE);
    table.setDefaultRenderer(Object.class, renderer);
    for (int i = 0; i < columns.length; i++)
      table.getColumnModel().getColumn(i).setPreferredWidth(i == columns.length - 2 ? 270 : 135);
    table.setRowHeight(26);
  }

  static void finish(JFrame frame) {
    JPanel content = (JPanel) frame.getContentPane();
    int width = 780, height = 700;
    for (Component component : content.getComponents()) {
      width = Math.max(width, component.getX() + component.getWidth() + 16);
      height = Math.max(height, component.getY() + component.getHeight() + 16);
    }
    content.setPreferredSize(new Dimension(width, height));
    JScrollPane scroll = new JScrollPane(content);
    scroll.getVerticalScrollBar().setUnitIncrement(18);
    frame.setContentPane(scroll);
    Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    int available = Math.min(width, screen.width - 80);
    if (available < width) {
      double scale = available / (double) width;
      for (Component component : content.getComponents()) {
        Rectangle bounds = component.getBounds();
        component.setBounds(
            (int) (bounds.x * scale),
            bounds.y,
            Math.max(20, (int) (bounds.width * scale)),
            bounds.height);
      }
      width = available;
    }
    for (Component component : content.getComponents()) {
      if (component instanceof JLabel label
          && label.getText() != null
          && !label.getText().isBlank()) {
        Font font = label.getFont();
        while (font.getSize() > 11
            && label.getFontMetrics(font).stringWidth(label.getText()) > label.getWidth() - 4)
          font = font.deriveFont((float) font.getSize() - 1);
        label.setFont(font);
      }
      if (component instanceof AbstractButton button
          && button.getText() != null
          && !button.getText().isBlank()) {
        button.setMargin(new Insets(3, 7, 3, 7));
        Font font = button.getFont();
        while (font.getSize() > 13
            && button.getFontMetrics(font).stringWidth(button.getText()) > button.getWidth() - 22)
          font = font.deriveFont((float) font.getSize() - 1);
        button.setFont(font);
      }
    }
    content.setPreferredSize(new Dimension(width, height));
    frame.setSize(
        Math.min(width + 24, screen.width - 40), Math.min(height + 60, screen.height - 50));
    frame.setLocationRelativeTo(null);
    frame.setResizable(true);
    JLabel status = new JLabel("Local coursework demo · use fictional information only");
    status.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
    status.setForeground(new Color(75, 75, 95));
    status.setBounds(20, height - 2, width - 40, 20);
    content.add(status);
    content.setPreferredSize(new Dimension(width, height + 25));
  }

  static void launch(java.util.function.Supplier<JFrame> factory) {
    EventQueue.invokeLater(
        () -> {
          try {
            factory.get().setVisible(true);
          } catch (RuntimeException failure) {
            error(null, failure);
          }
        });
  }
}
