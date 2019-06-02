package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodOrder extends TemplateGui {
    private JComboBox cboStarter;
    private JPanel panel2;
    private JComboBox cboMain;
    private JComboBox cboDessert;
    private JComboBox cboDrink;
    private JButton btnOrder;
    private JButton btnBasket;
    private UserMenu UserMenu;
    private mariadb database = new mariadb();
    private String name;
    private String calories;
    private String description;
    private String category;
    private ArrayList<String> main_items=new ArrayList<String>();

    public FoodOrder(){
        super("Food Order", "Main Menu", "UserMenu");


            }

    public void displayFoodOrder() {
        //To implement the food ordering I suggest:
        //Create a food order class, with the getters and setters.
        //Add the object to the database under a table called basketOrder
        //Allow the users to add as many items as they like.
        //When the basket is conformed/Ordered the database items will be copied/moved to the full orders table
        //The basket orders will be deleted.

        //There will be three tables; orders, orderdetails and temporder/basket.
        //Ordersdetails will include the date and customer ID

        ResultSet foodQuery = database.prepared_read_query("SELECT * FROM menu");

        try {
            while(foodQuery.next()) {
                name = foodQuery.getString("name");
                main_items.add(name);
                System.out.println(name);
                description = foodQuery.getString("description");
                calories = foodQuery.getString("calories");
                category = foodQuery.getString("category");

                switch (category){
                    case "main":
                        cboMain.addItem(name);
                        break;
                    case "starter":
                        cboStarter.addItem(name);
                        break;
                    case "dessert":
                        cboDessert.addItem(name);
                        break;
                    case "drink":
                        cboDrink.addItem(name);
                        break;
                }
            }
        }
        catch (Exception a)
        {
            System.err.println("Got an exception!");
            System.err.println(a.getMessage());
            //System.exit(1);
        }//try
       System.out.println(foodQuery);
        //cboStarter.addItem(testVar);
        frame.add(panel2, BorderLayout.CENTER);
        btnOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] chosenItems = {(cboStarter.getSelectedItem()).toString(),(cboMain.getSelectedItem()).toString(),
                        (cboDessert.getSelectedItem()).toString(),(cboDrink.getSelectedItem()).toString()};
                addItemsToDatabase(chosenItems);
            }
        });
        btnBasket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Basket basket = new Basket();
                basket.displayElements();
                frame.dispose();
            }
        });
        DisplayGenericElements();
    }

    public void addItemsToDatabase(String[] chosenItems){
        for (String var:chosenItems
             ) {
            ResultSet read_query =database.prepared_read_query("SELECT menu_id FROM menu WHERE name =?", var);
            try {
                while(read_query.next()) {
                    String foodItem = read_query.getString("menu_id");
                    database.prepared_write_query("INSERT INTO basket (itemID) VALUES (?)", foodItem);
                }
            }
            catch (Exception a)
            {break;}//try

        }
    }
}
