package gui;

import connector.ConnectorErrorException;
import connector.GuestConnector;
import gui.utils.DatabaseUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class GuestPromotionPanel extends JPanel {

    private JPanel givePromo;
    private JPanel superPanel;
    private JScrollPane givePromoTableScroll;
    private JTable guestToPromoteTable;
    private JPanel givePromoButtonPanel;
    private JButton refreshButton;
    private JButton promoteAllButton;

    public GuestPromotionPanel() {
        setUpGivePromoPanel();
        this.add(givePromo);
    }

    private void setUpGivePromoPanel() {
        superPanel = this;
        givePromo = new JPanel();
        givePromo.setLayout(new BorderLayout(0, 0));
        givePromoTableScroll = getNewGivePromoTableScroll();
        givePromo.add(givePromoTableScroll, BorderLayout.CENTER);
        givePromoButtonPanel = new JPanel();
        givePromoButtonPanel.setLayout(new BorderLayout(0, 0));
        givePromo.add(givePromoButtonPanel, BorderLayout.SOUTH);

        refreshButton = new JButton();
        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                superPanel.remove(givePromo);
                givePromo.remove(givePromoTableScroll);
                givePromoTableScroll = getNewGivePromoTableScroll();
                givePromo.add(givePromoTableScroll, BorderLayout.CENTER);
                superPanel.add(givePromo);
                givePromo.revalidate();
                givePromo.repaint();
                superPanel.revalidate();
                superPanel.repaint();
            }
        });
        givePromoButtonPanel.add(refreshButton, BorderLayout.NORTH);

        promoteAllButton = new JButton();
        promoteAllButton.setText("Give Promotion to All (not yet implemented)");
        givePromoButtonPanel.add(promoteAllButton, BorderLayout.SOUTH);
    }

    private JScrollPane getNewGivePromoTableScroll() {
        JScrollPane givePromoTableScroll = new JScrollPane();
        givePromoTableScroll.setVerticalScrollBarPolicy(22);
        ResultSet guestToPromoteRes = null;
        try {
            guestToPromoteRes = GuestConnector.getGuestUsedAllServiceTypes();
        } catch (ConnectorErrorException e) {
            JOptionPane.showMessageDialog(null, "Connection error","CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
        }
        guestToPromoteTable = DatabaseUtil.buildTableFromResultSet(guestToPromoteRes);
        guestToPromoteTable.setAutoCreateRowSorter(true);
        guestToPromoteTable.setFillsViewportHeight(false);
        givePromoTableScroll.setViewportView(guestToPromoteTable);
        return givePromoTableScroll;
    }
}
