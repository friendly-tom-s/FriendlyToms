package com.project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Basket extends TemplateGui {
    private JList list1;
    private JPanel panel2;
    private JButton btnOrder;
    private mariadb database = new mariadb();
    ResultSet nameOfItems;
    private String userid;


    public Basket(){super("Basket", "Back", "FoodOrder");

    }

    public void displayElements(){
        frame.add(panel2, BorderLayout.CENTER);
        System.out.println(getListItems());
        list1.setModel(getListItems());
        DisplayGenericElements();
        btnOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { makeOrder();}
        });
    }

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

    private void makeOrder(){
        ResultSet listItems = database.prepared_read_query("SELECT * FROM basket");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        try {
            while(listItems.next()) {
                String foodItem = listItems.getString("itemID");
                database.prepared_write_query("INSERT INTO orders (order_date, userID, foodItem) VALUES (?,?,?)", date, getUser(), foodItem);
            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try

    }

}
