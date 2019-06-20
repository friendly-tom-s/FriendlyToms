package com.project;
import javax.swing.*;
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


public class Basket extends TemplateGui {
    private JList list1;
    private JPanel panel2;
    private JButton btnOrder;
    private ResultSet nameOfItems;

    public Basket(){super("Basket", "Back", "FoodOrder");}

    /**
     * A typical class that inherits the generic elements from the template gui.
     */
    public void displayElements(){
        frame.add(panel2, BorderLayout.CENTER);
        list1.setModel(getListItems());
        DisplayGenericElements();
        btnOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeOrder();
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
                if(n == 0){printUserReceipt();}
                database.prepared_write_query("DELETE FROM basket");

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
    public DefaultListModel getListItems(){
        DefaultListModel JListItems = new DefaultListModel();
        ResultSet listItems = database.prepared_read_query("SELECT itemID FROM basket");

        try {
            while(listItems.next()) {
                String columnValue = listItems.getString("itemID");
                nameOfItems = database.prepared_read_query("SELECT name FROM menu where menu_id=?", columnValue);//Take this out the while loop and make it an array
                try {
                    while(nameOfItems.next()) {
                        String columnNameValue = nameOfItems.getString("name");
                        System.out.println(columnNameValue);
                        JListItems.addElement(columnNameValue);
                    }
                }
                catch (Exception a){System.out.println("Something failed at 2");}//try
            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try

        return JListItems;
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

    private void printUserReceipt(){
        File file = new File("C:/Users/jake.bowles/desktop/abc.txt");

        // if file doesnt exists, then create it
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ResultSet listItems = database.prepared_read_query("SELECT itemID FROM basket");
        String foodItems= "";

        try {
            while(listItems.next()) {
                String columnValue = listItems.getString("itemID");
                nameOfItems = database.prepared_read_query("SELECT name FROM menu where menu_id=?", columnValue);//Take this out the while loop and make it an array
                try {
                    while(nameOfItems.next()) {
                        String columnNameValue = nameOfItems.getString("name");
                        foodItems = foodItems + System.lineSeparator() + columnNameValue;
                    }
                }
                catch (Exception a){System.out.println("Something failed at 2");}//try
            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try

        FileWriter fw = null;
        try {
            fw = new FileWriter(file.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(foodItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null,"Receipt Saved!");


    }
}
