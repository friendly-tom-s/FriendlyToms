package com.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class FoodMenu extends TemplateGui {
    private JPanel panel1;
    private JTable tblStarter;
    private JTable tblMain;
    private JTable tblDessert;
    private JTable tblDrink;
    private JTabbedPane tabbedPane1;
    private JScrollPane paneStarter;
    private JScrollPane paneMain;
    private JScrollPane paneDessert;
    private JScrollPane paneDrink;


    public FoodMenu(){super("Menu", "Back", "FoodOrder");}

    public void displayMenu(){
        DisplayGenericElements();
        tblStarter.setModel(getMenu("starter"));
        tblMain.setModel(getMenu("main"));
        tblDessert.setModel(getMenu("dessert"));
        tblDrink.setModel(getMenu("drink"));

        paneStarter = new JScrollPane(tblStarter);
        tabbedPane1.add("Starters", paneStarter);

        paneMain = new JScrollPane(tblMain);
        tabbedPane1.add("Mains", paneMain);

        paneDessert = new JScrollPane(tblDessert);
        tabbedPane1.add("Desserts", paneDessert);

        paneDrink = new JScrollPane(tblDrink);
        tabbedPane1.add("Drinks", paneDrink);
        frame.add(tabbedPane1);
    }

    public DefaultTableModel getMenu(String menuOption){
        DefaultTableModel model = new DefaultTableModel();
        Object[] columns = {"FoodName","Description", "Cals"};

        model.setColumnIdentifiers(columns);
        ResultSet read_query = database.prepared_read_query("SELECT * FROM menu WHERE category = ?", menuOption);
        try {
            while(read_query.next()) {
                String foodName = read_query.getString("name");
                String descriptions = read_query.getString("description");
                String cals = read_query.getString("calories");
                String[] foodInfo = {foodName, descriptions, cals};
                model.addRow(foodInfo);
                            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try
        return model;
    }




}
