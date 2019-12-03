package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import connector.BillConnector;
import connector.ConnectorErrorException;
import gui.utils.DatabaseUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class BillStatsByRoomTypePanel extends JPanel {
    private JPanel superPanel;

    private JPanel billByRoomTypePanel;
    private JPanel optionsPanel;
    private JPanel tablePanel;
    private JScrollPane billByRTTableScroll;
    private JTable billByRTTable;
    private JLabel descriptionLabel;
    private JPanel querySelectionPanel;
    private JComboBox aggrOpCombobox;
    private ButtonGroup buttonGroup;
    private JRadioButton sumRadioButton;
    private JRadioButton minRadioButton;
    private JRadioButton maxRadioButton;
    private JTextField resultField;
    private JLabel resultLabel;
    private JButton viewButton;

    public BillStatsByRoomTypePanel() {
        superPanel = this;
        setUpBillByRoomTypePanel();
        superPanel.add(billByRoomTypePanel);
    }

    private void setUpBillByRoomTypePanel() {
        billByRoomTypePanel = new JPanel();
        billByRoomTypePanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BorderLayout(0, 0));
        billByRoomTypePanel.add(optionsPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        descriptionLabel = new JLabel();
        descriptionLabel.setText("Select query type to view Bill Stats by RoomType:");
        optionsPanel.add(descriptionLabel, BorderLayout.NORTH);
        querySelectionPanel = new JPanel();
        querySelectionPanel.setLayout(new GridLayoutManager(4, 6, new Insets(0, 0, 0, 0), -1, -1));
        optionsPanel.add(querySelectionPanel, BorderLayout.CENTER);
        sumRadioButton = new JRadioButton();
        sumRadioButton.setText("sum");
        querySelectionPanel.add(sumRadioButton, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        minRadioButton = new JRadioButton();
        minRadioButton.setText("min");
        querySelectionPanel.add(minRadioButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resultField = new JTextField();
        resultField.setEditable(false);
        querySelectionPanel.add(resultField, new GridConstraints(2, 3, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        resultLabel = new JLabel();
        resultLabel.setText("Result:");
        querySelectionPanel.add(resultLabel, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        querySelectionPanel.add(spacer1, new GridConstraints(2, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        querySelectionPanel.add(spacer2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        maxRadioButton = new JRadioButton();
        maxRadioButton.setText("max");
        querySelectionPanel.add(maxRadioButton, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        viewButton = new JButton();
        viewButton.setText("View");
        createActionListenerForView();
        querySelectionPanel.add(viewButton, new GridConstraints(3, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aggrOpCombobox = new JComboBox();
        aggrOpCombobox.addItem("avg");
        aggrOpCombobox.addItem("min");
        aggrOpCombobox.addItem("max");
        querySelectionPanel.add(aggrOpCombobox, new GridConstraints(0, 1, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        billByRoomTypePanel.add(tablePanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        billByRTTableScroll = getNewBillByRTTableScroll();
        tablePanel.add(billByRTTableScroll, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(sumRadioButton);
        buttonGroup.add(minRadioButton);
        buttonGroup.add(maxRadioButton);
    }

    private void createActionListenerForView() {
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = "";
                DecimalFormat df = new DecimalFormat("#.00");

                ResultSet res;
                String aggrOp = (String) aggrOpCombobox.getSelectedItem();
                try {
                    if (minRadioButton.isSelected()) {
                        res = BillConnector.getMBillForEachRoomType(aggrOp,"<=");
                        if(res.next())
                            result = df.format(res.getDouble(2));
                    } else if (maxRadioButton.isSelected()) {
                        res = BillConnector.getMBillForEachRoomType(aggrOp,">=");
                        if(res.next())
                            result = df.format(res.getDouble(2));
                    } else {
                        res = BillConnector.getSBillForEachRoomType(aggrOp);
                        if(res.next())
                            result = df.format(res.getDouble(1));
                    }
                } catch (ConnectorErrorException e1) {
                    JOptionPane.showMessageDialog(null, "Connection error", "CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Connection error", "CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
                }
                resultField.setText(result);
            }
        });
    }

    private JScrollPane getNewBillByRTTableScroll() {
        JScrollPane billByRTTableScroll = new JScrollPane();
        billByRTTable = new JTable();
        billByRTTableScroll.setVerticalScrollBarPolicy(22);

        ResultSet billByRT = null;
        try {
            billByRT = BillConnector.getBillForEachRoomType();
        } catch (ConnectorErrorException e) {
            JOptionPane.showMessageDialog(null, "Connection error", "CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
        }
        billByRTTable = DatabaseUtil.buildTableFromResultSet(billByRT);
        billByRTTable.getColumnModel().getColumn(1).setCellRenderer(new gui.utils.DatabaseUtil.DecimalFormatRenderer());
        billByRTTable.setAutoCreateRowSorter(true);
        billByRTTable.setAutoResizeMode(0);
        billByRTTableScroll.setViewportView(billByRTTable);
        return billByRTTableScroll;
    }
}
