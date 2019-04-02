package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FoodOrder extends TemplateGui {
    private JComboBox cboStarter;
    private JPanel panel2;
    private JComboBox cboMain;
    private JComboBox cboDessert;
    private JComboBox cboDrink;
    private JButton btnOrder;
    private UserMenu UserMenu;
    private mariadb database = new mariadb();
    private String name;
    private String calories;
    private String description;
    private ArrayList<String> main_items=new ArrayList<String>();

    public FoodOrder(){super("Food Order", "Main Menu", "UserMenu");}

    public void displayFoodOrder() {

        ResultSet testVar = database.prepared_read_query("SELECT * FROM menu");

        try {
            while(testVar.next()) {
                name = testVar.getString("name");
                main_items.add(name);
                cboMain.addItem(name);
                description = testVar.getString("description");
                calories = testVar.getString("calories");
            }
        }
        catch (Exception a)
        {
            System.err.println("Got an exception!");
            System.err.println(a.getMessage());
            //System.exit(1);
        }//try


        //cboMain.addItem(main_items);
        System.out.println(testVar);




        //cboStarter.addItem(testVar);
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();



    }
}
