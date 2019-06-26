package com.project;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    DefaultTableModel model = new DefaultTableModel();
    private JTextField txtAddStock;
    private JButton btnAddStock;
    private JLabel labAmount;
    private JButton btnAddMenuItem;
    private JTable table;
    private JScrollPane pane;
    private JButton btnUpdate;
    private ArrayList<String> main_items = new ArrayList<String>();
    private JPanel panelMain = new JPanel(new BorderLayout());

    public ManageStock() {
        super("Manage Stock", "Main Menu", "AdminMenu");

    }

    /**
     * This is the typical method of adding action listeners and Gui settings.
     */
    public void DisplayStock() {
        setJtable();
        Dimension dimension = new Dimension();
        dimension.setSize(500, 275);
        table.setPreferredScrollableViewportSize(dimension);
        pane = new JScrollPane(table);
        panelMain.add(pane, BorderLayout.CENTER);
        panelMain.add(btnUpdate, BorderLayout.SOUTH);
        panelMain.add(btnAddMenuItem, BorderLayout.PAGE_END);

        frame.add(panelMain, BorderLayout.CENTER);
        DisplayGenericElements();

        btnAddMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMenuItem addMenuItem = new AddMenuItem();
                addMenuItem.DisplayAddMenuItem();
                frame.dispose();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
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

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "Are you sure you wish to update the changed items?", "Update Stock confirmation", dialogButton);
            if (dialogResult == 0) {
                databaseLogic();
            }

    }

    public void databaseLogic(){
        int row = table.getSelectedRow();
        String nameVar = table.getValueAt(row, 0).toString();
        int stockVar = Integer.parseInt(table.getValueAt(row, 1).toString());
        boolean write_query = database.prepared_write_query("UPDATE menu SET stock =? WHERE name = ?",stockVar, nameVar );
        setJtable();
    }

    public void setJtable(){

        Object[] columns = {"Food", "Stock"};
        model.setColumnIdentifiers(columns);
        ResultSet read_query = database.prepared_read_query("SELECT * FROM menu");

        try
        {
            while (read_query.next())
            {
                String foodName = read_query.getString("name");
                String stock = read_query.getString("stock");
                String user_info[] = {foodName, stock};
                model.addRow(user_info);
            }
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! EXITING ");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        // set the model to the table
        table.setModel(model);

    }

}