package gui;

import connector.ConnectorErrorException;
import connector.EmployeeConnector;
import gui.utils.DatabaseUtil;

import javax.swing.*;
import java.awt.*;

public class EmployeeWelcomePanel extends JPanel{

    public EmployeeWelcomePanel() {
        JPanel welcome = new JPanel();

        welcome.setLayout(new GridBagLayout());

        JLabel welcomeLabel = new JLabel("Welcome! ");
        JLabel employeeName = new JLabel();
        try {
            employeeName.setText(EmployeeConnector.getEmployeeName(EmployeeConnector.currentEmployee));
        } catch (
                ConnectorErrorException e) {
            JOptionPane.showMessageDialog(null, "Connection error","CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
        }

        Font welcomeFont = DatabaseUtil.getFont(null, -1, 30,  welcomeLabel.getFont());
        if (welcomeFont != null) {
            welcomeLabel.setFont(welcomeFont);
            employeeName.setFont(welcomeFont);
        }
        welcome.add(welcomeLabel);
        welcome.add(employeeName);
        this.add(welcome);
    }
}
