package gui;

import javax.swing.*;
import java.awt.*;

public class GuestPanel extends JPanel{
    private JPanel parentCard;

    public GuestPanel(JPanel parent) {
        parentCard = parent;
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(650, 600));

        // WELCOME TAB
        JComponent welcome = new GuestWelcomePanel();
        tabbedPane.addTab("Welcome!",null, welcome, "welcome");

        this.add(tabbedPane);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
}
