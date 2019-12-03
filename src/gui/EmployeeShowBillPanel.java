package gui;

import connector.BookingConnector;
import connector.ConnectorErrorException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;

public class EmployeeShowBillPanel extends JPanel implements PropertyChangeListener {

    private JPanel viewBillPanel;
    private String bookingNum;
    private JFormattedTextField bookingNumField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField amountField;
    private JTextField billNumField;
    private JTextField dateGeneratedField;
    private JLabel bookingNumLabel;
    private JLabel billNumLabel;
    private JLabel dateGeneratedLabel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel amountLabel;
    private JButton showBillButton;

    public EmployeeShowBillPanel() {
        setUpEmployeeShowBillPanel();
        this.add(viewBillPanel);
    }

    private void setUpEmployeeShowBillPanel() {
        viewBillPanel = new JPanel();
        viewBillPanel.setLayout(new GridBagLayout());

        bookingNum = "BK00000000";
        bookingNumField = new JFormattedTextField(gui.utils.DatabaseUtil.bookingNumFormat);
        bookingNumField.setValue(new String(bookingNum));
        bookingNumField.setColumns(10);
        bookingNumField.addPropertyChangeListener("value", this);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 100;
        viewBillPanel.add(bookingNumField, gbc);

        firstNameField = new JTextField();
        firstNameField.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        viewBillPanel.add(firstNameField, gbc);

        lastNameField = new JTextField();
        lastNameField.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        viewBillPanel.add(lastNameField, gbc);

        amountField = new JTextField();
        amountField.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        viewBillPanel.add(amountField, gbc);

        billNumField = new JTextField();
        billNumField.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        viewBillPanel.add(billNumField, gbc);

        dateGeneratedField = new JTextField();
        dateGeneratedField.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        viewBillPanel.add(dateGeneratedField, gbc);

        bookingNumLabel = new JLabel();
        bookingNumLabel.setText("Enter a booking #: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        viewBillPanel.add(bookingNumLabel, gbc);
        billNumLabel = new JLabel();
        billNumLabel.setText("Bill #:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        viewBillPanel.add(billNumLabel, gbc);
        firstNameLabel = new JLabel();
        firstNameLabel.setText("First name: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        viewBillPanel.add(firstNameLabel, gbc);
        lastNameLabel = new JLabel();
        lastNameLabel.setText("Last name:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        viewBillPanel.add(lastNameLabel, gbc);
        amountLabel = new JLabel();
        amountLabel.setText("Amount: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        viewBillPanel.add(amountLabel, gbc);
        dateGeneratedLabel = new JLabel();
        dateGeneratedLabel.setText("Date generated: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        viewBillPanel.add(dateGeneratedLabel, gbc);

        showBillButton = new JButton();
        showBillButton.setText("Show Bill");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        showBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String requestedBookingNum = bookingNumField.getText();
                ResultSet answer = null;
                try {
                    answer = BookingConnector.getbillsInfo(requestedBookingNum);
                }
                catch (ConnectorErrorException e1) {
                    JOptionPane.showMessageDialog(null, "Connection error", "CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
                }

                try {
                    if (answer.next()) {
                        billNumField.setText(answer.getString(1));
                        amountField.setText(answer.getString(2));
                        firstNameField.setText(answer.getString(3));
                        lastNameField.setText(answer.getString(4));

                        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String date = formatter.format(answer.getDate(5));
                        dateGeneratedField.setText(date);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Invalid booking#", "INVALID INPUT", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch(SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Failed to retrieve data", "DATA RETRIEVAL ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        viewBillPanel.add(showBillButton, gbc);

        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        viewBillPanel.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        viewBillPanel.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        viewBillPanel.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.VERTICAL;
        viewBillPanel.add(spacer4, gbc);
        bookingNumLabel.setLabelFor(bookingNumField);
        billNumLabel.setLabelFor(billNumField);
        firstNameLabel.setLabelFor(firstNameField);
        lastNameLabel.setLabelFor(lastNameField);
        amountLabel.setLabelFor(amountField);
        dateGeneratedLabel.setLabelFor(dateGeneratedField);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();
        if (source == bookingNumField) {
            bookingNum = (String) bookingNumField.getValue();
        }
    }
}
