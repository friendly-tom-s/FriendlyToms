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
    private JLabel costLabel;
    private JComboBox cboTable;
    private JTextField txtCVV;
    private JTextField txtCardNumber;
    private JTextField txtName;
    private JComboBox cboExpiry;
    private JButton btnPay;
    private JPanel panel1;
    private static final int TIME_VISIBLE = 3000;

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


                JOptionPane pane = new JOptionPane("Payment processing, please wait.........", JOptionPane.INFORMATION_MESSAGE);

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


                int delay = 4000; //milliseconds
                ActionListener taskPerformer = new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        //...Perform a task...
                        Object[] options = {"Yes, print receipt",
                                "No, thanks"};
                        int n = JOptionPane.showOptionDialog(frame,
                                "Order has been made, would you like to print the receipt? ",
                                "Order Completed",
                                JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[1]);
                    }
                };
                Timer myTimer = new Timer(delay, taskPerformer);
                myTimer.setRepeats(false);
                myTimer.start();

            }
        });
        makeOrder();
    }
    /**
     * When the user confirms that everything in the basket is what they want to order, the confirm order button gets everything from
     * the basket table and adds them to the order table.
     *
     * The stock level is also reduced by however many items have been ordered.
     *
     * The basket is not cleared because this is already done when the user logs in.
     */
    private void makeOrder(){
        ResultSet listItems = database.prepared_read_query("SELECT * FROM basket");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        try {
            while(listItems.next()) {
                String foodItem = listItems.getString("itemID");
                database.prepared_write_query("INSERT INTO orders (order_date, userID, foodItem) VALUES (?,?,?)", date, getUser(), foodItem);
                database.prepared_write_query("UPDATE menu SET stock = stock - 1 WHERE menu_id = ?", foodItem);
            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try
    }
}


