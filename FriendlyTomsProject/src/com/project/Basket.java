package com.project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.ResultSet;
import java.util.ArrayList;


public class Basket extends TemplateGui {
    private JList list1;
    private JPanel panel2;
    private mariadb database = new mariadb();
    ResultSet nameOfItems;


    public Basket(){super("Basket", "Back", "FoodOrder");}

    public void displayElements(){
        frame.add(panel2, BorderLayout.CENTER);
        System.out.println(getListItems());
        list1.setModel(getListItems());
        DisplayGenericElements();
    }

    public DefaultListModel getListItems(){
        DefaultListModel JListItems = new DefaultListModel();
        ResultSet listItems = database.prepared_read_query("SELECT itemID FROM basket");
        try {
            while(listItems.next()) {
                String columnValue = listItems.getString("itemID");
                nameOfItems = database.prepared_read_query("SELECT name FROM menu where menu_id=?", columnValue);
            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try

        try {
            while(nameOfItems.next()) {
                String columnValue = nameOfItems.getString("name");
                System.out.println(columnValue);
                JListItems.addElement(columnValue);
            }
        }
        catch (Exception a){System.out.println("Something failed at 2");}//try

        return JListItems;

    }
}
