package com.project;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.sql.Array;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is where the basket is viewed and the user has the option to complete their order
 */
public class Basket extends TemplateGui {
    private JPanel panel2;
    private JButton btnOrder;
    private JTable table;
    private JLabel costLabel;
    private JButton btnDelete;
    private ResultSet nameOfItems;
    private JScrollPane pane;
    private int totalCost;


    public Basket(){
        super("Basket", "Back", "FoodOrder");

    }

    /**
     * A typical class that inherits the generic elements from the template gui.
     */
    public void displayElements(){

        table.setModel(getListItems());
        Dimension dimension = new Dimension();
        dimension.setSize(500, 250);
        table.setPreferredScrollableViewportSize(dimension);
        pane = new JScrollPane(table);
        costLabel.setText("The cost of this basket is: £"+getTotalCost());
        panelMainBorder.add(pane, BorderLayout.NORTH);
        panelMainBorder.add(costLabel, BorderLayout.WEST);
        panelMainBorder.add(btnOrder, BorderLayout.SOUTH);
        panelMainBorder.add(btnDelete, BorderLayout.EAST);
        frame.add(panelMainBorder, BorderLayout.CENTER);

        DisplayGenericElements();
        btnOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (totalCost > 0)
                {
                    Checkout checkout = new Checkout();
                    checkout.displayCheckout();
                    frame.dispose();
                }
                else
                {
                    JOptionPane pane = new JOptionPane("Basket is empty, please add an item first.", JOptionPane.WARNING_MESSAGE);
                    JDialog dialog = pane.createDialog(null, "Error");
                    dialog.setModal(false);
                    dialog.setVisible(true);
                }



            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Object[] options = {"Yes, delete",
                            "No, thanks"};
                    int n = JOptionPane.showOptionDialog(frame,
                            "Are you sure you wish to delete this item?",
                            "Delete Item",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[1]);
                    if(n == 0) {
                        deleteItem();
                        table.setModel(getListItems());
                        costLabel.setText("The cost of this basket is: £"+getTotalCost());

                    }
                }catch (ArrayIndexOutOfBoundsException a ){
                    JOptionPane.showMessageDialog(null, "Please fill select a row");
                }

            }
        });
    }

    /**
     * Seeing as the items in the "basket" table in the database only uses the foodID and not the name of the food this method
     * gets the items and names from the correct places in the database. It then adds this to a list model so that it can be
     * added to a JList.
     *
     * @return
     * A ListModel is returned with all the food names.
     */
    public DefaultTableModel getListItems(){

        int overallTotalCost;
        totalCost = 0;
        DefaultTableModel model = new DefaultTableModel();
        Object[] columns = {"FoodName","Price"};
        model.setColumnIdentifiers(columns);
        DefaultListModel JListItems = new DefaultListModel();
        ResultSet listItems = database.prepared_read_query("SELECT itemID FROM basket WHERE userID=?", getUser());

        try {
            while(listItems.next()) {
                String columnValue = listItems.getString("itemID");
                nameOfItems = database.prepared_read_query("SELECT name, price FROM menu where menu_id=?", columnValue);//Take this out the while loop and make it an array
                try {
                    while(nameOfItems.next()) {
                        String columnNameValue = nameOfItems.getString("name");
                        String prices = nameOfItems.getString("price");
                        overallTotalCost= totalCost + Integer.parseInt(prices);
                        setTotalCost(overallTotalCost);
                        String user_info[] = {columnNameValue, "£"+prices};
                        model.addRow(user_info);
                    }
                }
                catch (Exception a){System.out.println("Something failed at 2" + a);}//try
            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try

        return model;
    }


    /**
     * This gets and sets for total cost because this var is used elsewhere in the program.
     * @param cost
     */
    public void setTotalCost(int cost){
        totalCost = cost;
    }

    public int getTotalCost(){
        return totalCost;
    }

    private void deleteItem(){
        int row = table.getSelectedRow();
        String itemID= null;
        ResultSet itemQuery = database.prepared_read_query("SELECT menu_id FROM menu WHERE name=? ",
                table.getValueAt(row,0));
        try{
            while(itemQuery.next()){
                itemID = itemQuery.getString("menu_id");
            }
        }
        catch(Exception a){}

        boolean delete = database.prepared_write_query("delete from basket WHERE itemID = ? LIMIT 1", itemID);

    }
}
