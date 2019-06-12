package com.project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public void DisplayStock() {
        ResultSet stockItems = database.prepared_read_query("SELECT * FROM menu");
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();

        try {
            while (stockItems.next()) {
                String name = stockItems.getString("name");
                cboItem.addItem(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        cboItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dbItem = (cboItem.getSelectedItem()).toString();
                System.out.println(dbItem);
                ResultSet matchingItem = database.prepared_read_query("SELECT stock FROM menu WHERE name =?", dbItem);
                try {
                    while (matchingItem.next()) {
                        labAmount.setText(String.valueOf(matchingItem.getInt("stock")));
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        btnAddStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStock();
            }
        });
    }

    public void updateStock(){

        boolean write_query = database.prepared_write_query("UPDATE menu SET stock =? WHERE name = ?", txtAddStock.getText(), (cboItem.getSelectedItem()).toString());
        if(write_query = true){System.out.println("DONE");};

    }

}