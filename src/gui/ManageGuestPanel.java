package gui;

import connector.ConnectorErrorException;
import connector.GuestConnector;
import gui.utils.DatabaseUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class ManageGuestPanel extends JPanel{
    private JTable guestTable;
    private JPanel manageGuestPanel;
    private JScrollPane guestTableScroll;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JPanel buttonPanel;

    public ManageGuestPanel() {
        JPanel superPanel = this;
        manageGuestPanel = new JPanel();
        manageGuestPanel.setLayout(new BorderLayout(0, 0));
        guestTableScroll = getNewGuestTableScroll();
        manageGuestPanel.add(guestTableScroll, BorderLayout.CENTER);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout(0, 0));
        manageGuestPanel.add(buttonPanel, BorderLayout.SOUTH);
        updateButton = new JButton();
        updateButton.setText("Update Guest");
        buttonPanel.add(updateButton, BorderLayout.PAGE_START);
        deleteButton = new JButton();
        deleteButton.setText("Delete Guest");
        buttonPanel.add(deleteButton, BorderLayout.CENTER);
        refreshButton = new JButton();
        refreshButton.setText("Refresh Table");
        buttonPanel.add(refreshButton, BorderLayout.PAGE_END);


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog updateDialog = new UpdateGuestDialog();
                updateDialog.setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog deleteDialog = new DeleteGuestDialog();
                deleteDialog.setVisible(true);
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                superPanel.remove(manageGuestPanel);
                manageGuestPanel.remove(guestTableScroll);
                guestTableScroll = getNewGuestTableScroll();
                manageGuestPanel.add(guestTableScroll, BorderLayout.CENTER);
                superPanel.add(manageGuestPanel);
                manageGuestPanel.revalidate();
                superPanel.revalidate();
                manageGuestPanel.repaint();
                superPanel.repaint();
            }
        });
        superPanel.add(manageGuestPanel);
    }

    private JScrollPane getNewGuestTableScroll() {
        JScrollPane guestTableScroll = new JScrollPane();
        guestTableScroll.setVerticalScrollBarPolicy(22);
        ResultSet allGuestRes = null;
        try {
            allGuestRes = GuestConnector.getALLGuestInfo();
        } catch (ConnectorErrorException e) {
            JOptionPane.showMessageDialog(null, "CONNECTION ERROR","CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
        }
        guestTable = DatabaseUtil.buildTableFromResultSet(allGuestRes);
        guestTable.setAutoCreateRowSorter(true);
        guestTable.setFillsViewportHeight(false);
        guestTableScroll.setViewportView(guestTable);
        return guestTableScroll;
    }
}
