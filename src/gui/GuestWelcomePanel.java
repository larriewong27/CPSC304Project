package gui;

import connector.ConnectorErrorException;
import connector.EmployeeConnector;
import connector.GuestConnector;
import gui.utils.DatabaseUtil;

import javax.swing.*;
import java.awt.*;

public class GuestWelcomePanel extends JPanel {

    public GuestWelcomePanel() {
        JPanel welcome = new JPanel();
        welcome.setLayout(new GridBagLayout());

        JLabel welcomeLabel = new JLabel("Welcome! ");
        JLabel guestName = new JLabel();
        try {
            guestName.setText(GuestConnector.getGuestName(GuestConnector.currentGuest));
        } catch (
                ConnectorErrorException e) {
            JOptionPane.showMessageDialog(null, "Connection error","CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
        }

        Font welcomeFont = DatabaseUtil.getFont(null, -1, 30,  welcomeLabel.getFont());
        if (welcomeFont != null) {
            welcomeLabel.setFont(welcomeFont);
            guestName.setFont(welcomeFont);
        }
        welcome.add(welcomeLabel);
        welcome.add(guestName);
        this.add(welcome);
    }
}
