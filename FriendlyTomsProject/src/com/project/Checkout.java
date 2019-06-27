package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;


public class Checkout extends TemplateGui {
    private JList lstUnit;
    private JList lstPrice;
    private JLabel costLabel;
    private JComboBox cboTable;
    private JTextField txtCVV;
    private JTextField txtCardNumber;
    private JTextField txtName;
    private JComboBox cboExpiry;
    private JButton btnPay;
    private JPanel panel1;
    private static final int TIME_VISIBLE = 10000;

    public Checkout() {
        super("Checkout", "Back", "Basket");

    }

    public void displayCheckout() {
        // table.setModel(getListItems());
        // Dimension dimension = new Dimension();
        // dimension.setSize(500, 250);
        // table.setPreferredScrollableViewportSize(dimension);
        //pane = new JScrollPane(table);

        //costLabel.setText("The cost of this basket is: £" + getTotalCost());

        frame.add(panel1, BorderLayout.CENTER);
        DisplayGenericElements();

        btnPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                JOptionPane pane = new JOptionPane("Payment Processing Please Wait.........", JOptionPane.INFORMATION_MESSAGE);
                JDialog dialog = pane.createDialog(null, "Payment Processing");
                dialog.setModal(false);
                dialog.setVisible(true);
                dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

                new Timer(TIME_VISIBLE, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.setVisible(false);
                    }
                }).start();

            }
        });


    }
}


