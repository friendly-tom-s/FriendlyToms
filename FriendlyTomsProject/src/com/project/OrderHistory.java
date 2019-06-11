package com.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class OrderHistory extends TemplateGui {
    private JList lstOrderDetails;
    private JPanel panel2;
    private JTable table;
    private mariadb database = new mariadb();
    private ResultSet previousOrders;
    private ResultSet foodNames;
    private String previousWIn;
    private JPanel frame2 = new JPanel();
    private JScrollPane pane;


    public OrderHistory(String guiName, String buttonVar, String previousWIn ){
        super(guiName, buttonVar, previousWIn);
        this.previousWIn= previousWIn;
    }

    public void DisplayOrderHistory(){
        DisplayGenericElements();
        ResultSet userType = getUserType();
        table.setModel(getOrder(userType));
        pane = new JScrollPane(table);
        frame.add(pane, BorderLayout.CENTER);

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

    public Object[] getObject(){
        if(previousWIn.equals("AdminMenu")){
            Object[] columns = {"FoodName","Date", "User"};
            return columns;
        }
        else{
            Object[] columns = {"FoodName","Date"};
            return columns;
        }
    }

    public DefaultTableModel getOrder(ResultSet previousOrders){
        //DefaultListModel JListItems = new DefaultListModel();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(getObject());
        ResultSet read_query = database.prepared_read_query("SELECT * FROM users");
        try {
            while(previousOrders.next()) {
                String foodID = previousOrders.getString("foodItem");
                String date = previousOrders.getString("order_date");
                String user = previousOrders.getString("userID");
                foodNames = database.prepared_read_query("SELECT name FROM menu where menu_id=?", foodID);//Take this out the while loop and make it an array
                String username = getUserName(user);
                try {
                    while(foodNames.next()) {
                        String columnNameValue = foodNames.getString("name");

                        if(previousWIn.equals("AdminMenu")){
                            String user_info[] = {columnNameValue, date, username};
                            model.addRow(user_info);
                        }
                        else{
                            String user_info[] = {columnNameValue, date};
                            model.addRow(user_info);
                        }
                    }
                }
                catch (Exception a){System.out.println("Something failed at 2");}//try
            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try
        return model;
    }



}
