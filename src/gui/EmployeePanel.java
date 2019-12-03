package gui;

import javax.swing.*;
import java.awt.*;

public class EmployeePanel extends JPanel {
    private JPanel parentCard;

    public EmployeePanel(JPanel parent) {
        parentCard = parent;
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(650, 600));

        // WELCOME TAB
        JComponent welcome = new EmployeeWelcomePanel();
        tabbedPane.addTab("Welcome!",null, welcome, "welcome");

        // VIEW GUEST INFO TAB
        JComponent viewGuestInfo = new EmployeeViewGuestInfoPanel();
        tabbedPane.addTab("View Guest", null, viewGuestInfo, "view guest");

        // VIEW BILL TAB
        JComponent viewBill = new EmployeeShowBillPanel();
        tabbedPane.addTab("View Bill", null, viewBill, "view bill");

        // GIVE PROMOTION TAB
        JComponent givePromo = new GuestPromotionPanel();
        tabbedPane.addTab("Give Promo", null, givePromo, "give promo");

        // BILL STAT TAB
        JComponent billStatByDate = new BillStatsPanel();
        tabbedPane.addTab("View Bill Stats By Date", null, billStatByDate, "view bill by date");

        // BILL STAT BY ROOM TYPE TAB
        JComponent billStatByRoomType = new BillStatsByRoomTypePanel();
        tabbedPane.addTab("View Bill Stats By RoomType", null ,billStatByRoomType, "view bill by room type");

        // MANAGE GUEST TAB
        JComponent manageGuest = new ManageGuestPanel();
        manageGuest.setBackground(Color.WHITE);
        tabbedPane.addTab("Manage Guest", null, manageGuest, "manage guest");

        this.add(tabbedPane);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
}
