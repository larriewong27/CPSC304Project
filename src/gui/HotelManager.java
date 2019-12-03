package gui;

import gui.utils.DatabaseUtil;

import javax.swing.*;
import java.awt.*;

public class HotelManager {
    private JFrame mainFrame;
    private JPanel mainPanel;

    public void runHotelManager() {
        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout());
        final JLabel title = new JLabel();

        Font titleFont = DatabaseUtil.getFont(null, -1, 20, title.getFont());
        if (titleFont != null)
            title.setFont(titleFont);
        title.setText("Hotel Manager");

        mainFrame = new JFrame("Hotel Manager");
        mainFrame.setPreferredSize(new Dimension(700,700));
        mainPanel = new JPanel(new CardLayout());

        mainPanel.add(new Login(mainPanel), "LOGIN");

        pan.add(title, BorderLayout.PAGE_START);
        pan.add(mainPanel, BorderLayout.CENTER);

        mainFrame.add(pan);
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension d = mainFrame.getToolkit().getScreenSize();
        Rectangle r = mainFrame.getBounds();
        mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        JDialog logInDialog = new DatabaseLoginDialog();
        logInDialog.setVisible(true);
    }
}
