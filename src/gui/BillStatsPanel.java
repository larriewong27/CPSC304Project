package gui;

import connector.BillConnector;
import connector.ConnectorErrorException;
import connector.GuestConnector;
import connector.InvalidInputException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;

/**
 * Created by katelyndu on 2018-06-16.
 */
public class BillStatsPanel extends JPanel {

    public BillStatsPanel() {
        JPanel basePanel = new JPanel();

        JPanel jp1=new JPanel();
        JPanel jp2=new JPanel();
        JPanel jp3=new JPanel();
        JPanel jp4=new JPanel();
        JPanel jp5=new JPanel();
        JButton jb1 = new JButton("Submit");
        JLabel label1 = new JLabel("Result");
        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
        JRadioButton sumjrb = new JRadioButton("Sum");
        JRadioButton minjrb = new JRadioButton("Min");
        JRadioButton maxjrb = new JRadioButton("Max");
        ButtonGroup bg = new ButtonGroup();
        bg.add(sumjrb);
        bg.add(minjrb);
        bg.add(maxjrb);
        sumjrb.setSelected(true);
        JTextField fromField = new JTextField(8);
        fromField.setText("MM/DD/YY");
        fromField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (fromField.getText().trim().equals("MM/DD/YY")) {
                    JTextField source = (JTextField) e.getComponent();
                    source.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (fromField.getText().trim().equals("")) {
                    JTextField source = (JTextField) e.getComponent();
                    source.setText("MM/DD/YY");
                }
            }
        });

        JTextField toField = new JTextField(8);
        toField.setText("MM/DD/YY");
        toField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (toField.getText().trim().equals("MM/DD/YY")) {
                    JTextField source = (JTextField) e.getComponent();
                    source.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (toField.getText().trim().equals("")) {
                    JTextField source = (JTextField) e.getComponent();
                    source.setText("MM/DD/YY");
                }
            }
        });

        jp1.add(new JLabel("From"));
        jp1.add(fromField);
        jp2.add(new JLabel("To"));
        jp2.add(toField);
        jp3.add(sumjrb);
        jp3.add(minjrb);
        jp3.add(maxjrb);
        jp4.add(jb1);
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // BillConnector.aggregateBill()
                String opt = "sum";
                if (minjrb.isSelected()){
                    opt = "min";
                }
                else if(maxjrb.isSelected()){
                    opt = "max";
                }
                try {
                    double value = BillConnector.aggregateBill(fromField.getText(),toField.getText(),opt);
                    label1.setText(String.valueOf(value));
                } catch (ParseException e1) {
                    JOptionPane.showMessageDialog(null, "Invalid date format","PARSE ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (ConnectorErrorException e1) {
                    JOptionPane.showMessageDialog(null, "Connection failed","CONNECTION ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidInputException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(),"INPUT ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        jp5.add(label1);

        basePanel.add(jp1);
        basePanel.add(jp2);
        basePanel.add(jp3);
        basePanel.add(jp4);
        basePanel.add(jp5);
        this.add(basePanel);
    }
}