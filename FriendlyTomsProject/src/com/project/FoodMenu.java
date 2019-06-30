package com.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

/**
 * This class populates the menu with all the food in the database.
 *
 * It categorises them according to if they are Starter, Main, Dessert or Drink.
 */

public class FoodMenu extends TemplateGui {
    private JPanel panel1;
    private JTable tblStarter;
    private JTable tblMain;
    private JTable tblDessert;
    private JTable tblDrink;
    private JTabbedPane tabbedPane1;
    private JButton imageButton;
    private JScrollPane paneStarter;
    private JScrollPane paneMain;
    private JScrollPane paneDessert;
    private JScrollPane paneDrink;
    private String selectedItem;


    public FoodMenu(){super("Menu", "Back", "FoodOrder");

    }

    /**
     * Creates the GUI and populates four separate JTables with the correct foods.
     *
     * These are then all given scroll bars and added to the GUI.
     *
     * Each JTable has a separate scroll bar.
     */
    public void displayMenu(){
        DisplayGenericElements();
        tblStarter.setModel(getMenu("starter"));
        tblMain.setModel(getMenu("main"));
        tblDessert.setModel(getMenu("dessert"));
        tblDrink.setModel(getMenu("drink"));

        Dimension dimension = new Dimension();
        dimension.setSize(500, 250);
        tblStarter.setPreferredScrollableViewportSize(dimension);
        tblDrink.setPreferredScrollableViewportSize(dimension);
        tblDessert.setPreferredScrollableViewportSize(dimension);
        tblMain.setPreferredScrollableViewportSize(dimension);

        paneStarter = new JScrollPane(tblStarter);
        tabbedPane1.add("Starters", paneStarter);

        paneMain = new JScrollPane(tblMain);
        tabbedPane1.add("Mains", paneMain);

        paneDessert = new JScrollPane(tblDessert);
        tabbedPane1.add("Desserts", paneDessert);

        paneDrink = new JScrollPane(tblDrink);
        tabbedPane1.add("Drinks", paneDrink);

        panelMainBorder.add(tabbedPane1, BorderLayout.NORTH);
        panelMainBorder.add(imageButton);

        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedIndex();
                ImageView imageView = new ImageView(getItemWebaddress(selectedItem));
                imageView.displayImageView();
            }
        });

        frame.add(panelMainBorder);
    }

    /**
     * This is used to get the table model including all the food items with the given food category.
     *
     * @param menuOption
     * This is either Starter, Main, Dessert or Drink. It is a column in the database.
     *
     * @return
     * The TableModel is returned which will include all food items.
     */
    public DefaultTableModel getMenu(String menuOption){
        DefaultTableModel model = new DefaultTableModel();
        Object[] columns = {"FoodName","Description", "Cals", "Price"};

        model.setColumnIdentifiers(columns);
        ResultSet read_query = database.prepared_read_query("SELECT * FROM menu WHERE category = ?", menuOption);
        try {
            while(read_query.next()) {
                String foodName = read_query.getString("name");
                String descriptions = read_query.getString("description");
                String cals = read_query.getString("calories");
                String prices = read_query.getString("price");
                String[] foodInfo = {foodName, descriptions, cals, "£"+prices};
                model.addRow(foodInfo);
                            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try
        return model;
    }

    public void setSelectedIndex(){
        int column = 0;
        int row = 0;

        switch (tabbedPane1.getSelectedIndex()){
            case 0:
                row = tblStarter.getSelectedRow();
                selectedItem= tblStarter.getValueAt(row, column).toString();
                break;
            case 1:
                row = tblMain.getSelectedRow();
                selectedItem= tblMain.getValueAt(row, column).toString();
                break;
            case 2:
                row = tblDessert.getSelectedRow();
                selectedItem= tblDessert.getValueAt(row, column).toString();
                break;
            case 3:
                row = tblDrink.getSelectedRow();
                selectedItem= tblDrink.getValueAt(row, column).toString();
                break;

        }
    }

    public String getItemWebaddress(String foodName){
        String address = null;
        ResultSet read_query = database.prepared_read_query("SELECT * FROM menu WHERE name = ?", foodName );
        try {
            while(read_query.next()) {
                address = read_query.getString("webaddress");
            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try
        return address;
    }

}
