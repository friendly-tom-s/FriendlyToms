package com.project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This is accessed by the admins to monitor the stock that they use. They can add stock from here.
 */

public class ManageStock extends TemplateGui {
    private JPanel panel2;
    private JComboBox cboItem;
    private JTextField txtAddStock;
    private JButton btnAddStock;
    private JLabel labAmount;
    private ArrayList<String> main_items = new ArrayList<String>();

    public ManageStock() {
        super("Manage Stock", "Main Menu", "AdminMenu");
    }

    /**
     * This is the typical method of adding action listeners and Gui settings.
     */
    public void DisplayStock() {
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();
        setInitialStock();

        cboItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStockAmountOnPress();
            }
        });

        btnAddStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStock();
            }
        });
    }

    /**
     * This will send the write query to the database class.
     *
     * It will update the stock with the new amount.
     */
    public void updateStock(){
        boolean write_query = database.prepared_write_query("UPDATE menu SET stock =? WHERE name = ?", txtAddStock.getText(), (cboItem.getSelectedItem()).toString());
    }

    /**
     * When the class is called this method is run to update the dropdown list with all the food items.
     */
    public void setInitialStock(){
        ResultSet stockItems = database.prepared_read_query("SELECT * FROM menu");
        try {
            while (stockItems.next()) {
                String name = stockItems.getString("name");
                cboItem.addItem(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    /**
     * When a food item is selected this gets and updates the amount of stock in the label.
     */
    public void updateStockAmountOnPress(){
        String dbItem = (cboItem.getSelectedItem()).toString();
        ResultSet matchingItem = database.prepared_read_query("SELECT stock FROM menu WHERE name =?", dbItem);
        try {
            while (matchingItem.next()) {
                labAmount.setText(String.valueOf(matchingItem.getInt("stock")));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}