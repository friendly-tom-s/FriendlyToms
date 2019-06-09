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
    private String previousWIn;


    public OrderHistory(String guiName, String buttonVar, String previousWIn ){
        super(guiName, buttonVar, previousWIn);
        this.previousWIn= previousWIn;
    }

    public void DisplayOrderHistory(){
        frame.add(panel2, BorderLayout.CENTER);
        ResultSet userType = getUserType();
        lstOrderDetails.setModel(getOrder(userType));
        DisplayGenericElements();
    }

    public ResultSet getUserType(){
        if(previousWIn.equals("AdminMenu")){
            previousOrders = database.prepared_read_query("SELECT * FROM orders");
        }
        if (previousWIn.equals("UserMenu")){
            previousOrders = database.prepared_read_query("SELECT * FROM orders WHERE userid = ?", getUser());
        }
        return previousOrders;
    }

    public DefaultListModel getOrder(ResultSet previousOrders){
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
