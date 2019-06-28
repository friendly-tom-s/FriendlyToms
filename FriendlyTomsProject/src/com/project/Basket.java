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
    private JPanel penlTest = new JPanel(new BorderLayout());
    private JButton btnOrder;
    private JTable table;
    private JLabel costLabel;
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
        penlTest.add(pane, BorderLayout.NORTH);
        penlTest.add(costLabel, BorderLayout.CENTER);
        penlTest.add(btnOrder, BorderLayout.SOUTH);
        frame.add(penlTest, BorderLayout.CENTER);

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
                UserMenu userMenu = new UserMenu();
                userMenu.displayUserMenu();
                frame.dispose();

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
        ResultSet listItems = database.prepared_read_query("SELECT itemID FROM basket");

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
                        String user_info[] = {columnNameValue, prices};
                        model.addRow(user_info);
                    }
                }
                catch (Exception a){System.out.println("Something failed at 2");}//try
            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try

        return model;
    }


    /**
     * This get and set for total cost because this var is used elsewhere in the program.
     * @param cost
     */
    public void setTotalCost(int cost){
        totalCost = cost;
    }

    public int getTotalCost(){
        return totalCost;
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

    /**
     * This is where the user receipt is saved if they wish to do so.
     *
     * It adds all the items that the user ordered to the receipt as well as the total cost.
     *
     * It adds it to the user's desktop.
     */
    private void printUserReceipt(){
        File file = new File(System.getProperty("user.home") + "/Desktop/FT_Receipt.txt");

        // if file doesnt exists, then create it
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Write the receipt.
        FileWriter fw = null;
        try {
            fw = new FileWriter(file.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(getFoodItems());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null,"Receipt Saved to you desktop!");

    }

    /**
     * This gets all the database items that the user ordered. As it the database is normalised this means that more
     * statements are needed because it requires IDs across tables to work
     *
     * @return
     * The string that will be added the receipt.
     */
    private String getFoodItems(){
        ResultSet listItems = database.prepared_read_query("SELECT itemID FROM basket");
        String foodItems= "";

        try {
            while(listItems.next()) {
                String columnValue = listItems.getString("itemID");
                nameOfItems = database.prepared_read_query("SELECT name FROM menu where menu_id=?", columnValue);
                try {
                    while(nameOfItems.next()) {
                        String columnNameValue = nameOfItems.getString("name");
                        foodItems = foodItems + System.lineSeparator() + columnNameValue;
                    }
                    foodItems = foodItems + System.lineSeparator() + "This came to a total of £"+getTotalCost();
                }
                catch (Exception a){System.out.println("Something failed at 2");}//try
            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try
        return foodItems;
    }
}
