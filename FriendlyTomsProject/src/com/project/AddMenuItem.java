package com.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class AddMenuItem extends TemplateGui {
    private JPanel panel1;
    private JButton btnSubmit;
    private JTextField txtname;
    private JTextField txtURL;
    private JTextField txtDescription;
    private JTextField txtPrice;
    private JComboBox cmbCategory;
    private JSpinner spnStock;
    private JSpinner spnCalories;

    private JPanel panelMain = new JPanel(new BorderLayout());


    public AddMenuItem() {
        super("Menu", "Back", "ManageStock");

    }

    public void DisplayAddMenuItem() {
        frame.add(panel1, BorderLayout.CENTER);
        DisplayGenericElements();

        String[] categories = new String[] {"Starter", "Main", "Dessert", "Drink"};
        cmbCategory.setModel(new DefaultComboBoxModel(categories));

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            WriteToDB();
            }
        });
    }

    private void WriteToDB(){
        try {

            String name = txtname.get;


            database.prepared_write_query("INSERT INTO menu (name, description, calories, category, stock, price, webaddress) VALUES (?,?,?)", date, getUser(), foodItem);
            database.prepared_write_query("UPDATE menu SET stock = stock - 1 WHERE menu_id = ?", foodItem);
        }

    catch (Exception a){System.out.println("Something failed at 1");}
    }//input_errors
}


