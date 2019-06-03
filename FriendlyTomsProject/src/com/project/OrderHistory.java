package com.project;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class OrderHistory extends TemplateGui {
    private JList lstOrderDetails;
    private JPanel panel2;
    private mariadb database = new mariadb();
    private ResultSet previousOrders;
    private ResultSet foodNames;

    public OrderHistory(){super("Order History", "Main Menu", "UserMenu");}

    public void DisplayOrderHistory(){
        frame.add(panel2, BorderLayout.CENTER);
        lstOrderDetails.setModel(getOrder());
        DisplayGenericElements();
    }

    public DefaultListModel getOrder(){
        previousOrders = database.prepared_read_query("SELECT * FROM orders WHERE userid = ?", getUser());
        DefaultListModel JListItems = new DefaultListModel();
        try {
            while(previousOrders.next()) {
                String foodID = previousOrders.getString("foodItem");
                String date = previousOrders.getString("order_date");
                foodNames = database.prepared_read_query("SELECT name FROM menu where menu_id=?", foodID);//Take this out the while loop and make it an array
                try {
                    while(foodNames.next()) {
                        String columnNameValue = foodNames.getString("name");
                        String joinedVar = (columnNameValue + " " + " " + date);
                        JListItems.addElement(joinedVar);
                    }
                }
                catch (Exception a){System.out.println("Something failed at 2");}//try
            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try
        return JListItems;
    }


}
