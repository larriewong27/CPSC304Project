package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import connector.ConnectorErrorException;
import connector.GuestConnector;
import gui.utils.DatabaseUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeViewGuestInfoPanel extends JPanel {
    private JPanel superPanel;
    private JPanel viewGuestPanel;
    private JScrollPane guestInfoTableScroll;
    private JPanel selectConditionPanel;
    private JLabel filterLabel;
    private JPanel filterConditionPanel;
    private JComboBox attrComboBox;
    private JComboBox opComboBox;
    private JTextField filterValueField;
    private JLabel projectLabel;
    private JPanel projectionPanel;
    private JCheckBox guest_numCheckBox;
    private JCheckBox first_nameCheckBox;
    private JCheckBox last_nameCheckBox;
    private JCheckBox IDCheckBox;
    private JCheckBox creditCard_numCheckBox;
    private JCheckBox addressCheckBox;
    private JCheckBox phoneCheckBox;
    private List<JCheckBox> checkBoxes;
    private JButton viewGuestButton;
    private JButton clearButton;
    private JTable resultTable;

    public EmployeeViewGuestInfoPanel() {
        superPanel = this;
        setUpViewGuestPanel();
        this.add(viewGuestPanel);
    }

    private void setUpViewGuestPanel() {
        viewGuestPanel = new JPanel();
        viewGuestPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        selectConditionPanel = new JPanel();
        selectConditionPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        viewGuestPanel.add(selectConditionPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        filterLabel = new JLabel();
        filterLabel.setText("Filter guest:");
        selectConditionPanel.add(filterLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        filterConditionPanel = new JPanel();
        filterConditionPanel.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        selectConditionPanel.add(filterConditionPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        attrComboBox = new JComboBox();
        attrComboBox.addItem("Guest_num");
        attrComboBox.addItem("First_name");
        attrComboBox.addItem("Last_name");
        attrComboBox.addItem("ID");
        attrComboBox.addItem("CreditCard_num");
        attrComboBox.addItem("Address");
        attrComboBox.addItem("Phone");
        filterConditionPanel.add(attrComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        filterConditionPanel.add(spacer1, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        opComboBox = new JComboBox();
        opComboBox.addItem("=");
        opComboBox.addItem("<>");
        opComboBox.addItem("LIKE");
        filterConditionPanel.add(opComboBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        filterValueField = new JTextField();
        filterConditionPanel.add(filterValueField, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer2 = new Spacer();
        filterConditionPanel.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        projectionPanel = new JPanel();
        projectionPanel.setLayout(new GridLayoutManager(2, 7, new Insets(0, 0, 0, 0), -1, -1));
        selectConditionPanel.add(projectionPanel, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        projectLabel = new JLabel();
        projectLabel.setText("Show only:");
        projectionPanel.add(projectLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        projectionPanel.add(spacer3, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));

        // Create checkboxes for selecting attributes to project
        checkBoxes = new ArrayList<JCheckBox>();
        guest_numCheckBox = new JCheckBox();
        guest_numCheckBox.setText("Guest_num");
        projectionPanel.add(guest_numCheckBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxes.add(guest_numCheckBox);
        first_nameCheckBox = new JCheckBox();
        first_nameCheckBox.setText("First_name");
        projectionPanel.add(first_nameCheckBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxes.add(first_nameCheckBox);
        last_nameCheckBox = new JCheckBox();
        last_nameCheckBox.setText("Last_name");
        projectionPanel.add(last_nameCheckBox, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxes.add(last_nameCheckBox);
        IDCheckBox = new JCheckBox();
        IDCheckBox.setText("ID");
        checkBoxes.add(IDCheckBox);
        projectionPanel.add(IDCheckBox, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        creditCard_numCheckBox = new JCheckBox();
        creditCard_numCheckBox.setText("CreditCard_num");
        projectionPanel.add(creditCard_numCheckBox, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxes.add(creditCard_numCheckBox);
        addressCheckBox = new JCheckBox();
        addressCheckBox.setText("Address");
        projectionPanel.add(addressCheckBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxes.add(addressCheckBox);
        phoneCheckBox = new JCheckBox();
        phoneCheckBox.setText("Phone");
        projectionPanel.add(phoneCheckBox, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxes.add(phoneCheckBox);
        viewGuestButton = new JButton();
        viewGuestButton.setText("View");
        createViewButtonActionListener();
        projectionPanel.add(viewGuestButton, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        clearButton = new JButton();
        clearButton.setText("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(JCheckBox checkBox: checkBoxes)
                    checkBox.setSelected(false);
            }
        });
        projectionPanel.add(clearButton, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        guestInfoTableScroll = new JScrollPane();
        viewGuestPanel.add(guestInfoTableScroll, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        resultTable = new JTable();
        resultTable.setAutoCreateRowSorter(true);
        resultTable.setFillsViewportHeight(false);
        guestInfoTableScroll.setViewportView(resultTable);
    }

    private void createViewButtonActionListener() {
        viewGuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewGuestPanel.remove(guestInfoTableScroll);
                guestInfoTableScroll = getNewGuestTableScroll();
                viewGuestPanel.add(guestInfoTableScroll, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
                viewGuestPanel.revalidate();
                viewGuestPanel.repaint();
            }
        });
    }

    private JScrollPane getNewGuestTableScroll() {
        JScrollPane newScrollPane = new JScrollPane();
        newScrollPane.setVerticalScrollBarPolicy(22);

        // Build selection string
        String selectionString = "where ";
        selectionString += attrComboBox.getSelectedItem() + " ";
        selectionString += opComboBox.getSelectedItem() + " ";
        selectionString += "'" + filterValueField.getText() + "'";

        // Build projection string
        String projectionAttr = buildProjectionString();

        ResultSet res = null;
        try {
            res = GuestConnector.getGuestInfo(selectionString, projectionAttr);
        } catch (ConnectorErrorException e1) {
            JOptionPane.showMessageDialog(null, "Connection error. Please try again.", "CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
        }

        resultTable = DatabaseUtil.buildTableFromResultSet(res);
        resultTable.setAutoCreateRowSorter(true);
        resultTable.setFillsViewportHeight(false);
        newScrollPane.setViewportView(resultTable);
        return newScrollPane;
    }

    private String buildProjectionString() {
        List<String> projectAttrList = new ArrayList<>();

        for(JCheckBox checkBox: checkBoxes) {
            if (checkBox.isSelected())
                projectAttrList.add(checkBox.getText());
        }
        if(projectAttrList.isEmpty())
            return "*";
        else {
            String projectionAttr = "";
            for (int i = 0; i < projectAttrList.size() - 1; i++) {
                projectionAttr += projectAttrList.get(i) + ", ";
            }
            projectionAttr += projectAttrList.get(projectAttrList.size() - 1);
            return projectionAttr;
        }
    }
}
