package gui;

import connector.ConnectorErrorException;
import connector.EmployeeConnector;
import connector.GuestConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JPanel{
    private JPanel parentCard;

    public Login(JPanel parent) {
        parentCard = parent;

        JLabel login = new JLabel("User ID");
        JTextField loginField = new JTextField(10);
        JButton loginButton = new JButton("Login");

        this.add(login);
        this.add(loginField);
        this.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userid = loginField.getText();
                try {
                    if (GuestConnector.guestExist(userid)) {
                        GuestConnector.setCurrentGuest(userid);
                        parentCard.add(new GuestPanel(parentCard), "GUESTWELCOME");
                        CardLayout cardLayout = (CardLayout) parentCard.getLayout();
                        cardLayout.show(parentCard, "GUESTWELCOME");
                    }
                    else if (EmployeeConnector.employeeExist(userid)) {
                        EmployeeConnector.setCurrentEmployee(userid);
                        parentCard.add(new EmployeePanel(parentCard), "EMPLOYEEWELCOME");
                        CardLayout cardLayout = (CardLayout) parentCard.getLayout();
                        cardLayout.show(parentCard, "EMPLOYEEWELCOME");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "INCORRECT USER ID");
                    }
                } catch (ConnectorErrorException e1) {
                    JOptionPane.showMessageDialog(null, "LOGIN FAILED. PLEASE TRY AGAIN.", "Connection Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
