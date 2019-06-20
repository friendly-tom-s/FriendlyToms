package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class is used for the food ordering, it moves all selected items to the basket.
 */

public class FoodOrder extends TemplateGui {
    private JComboBox cboStarter;
    private JPanel panel2;
    private JComboBox cboMain;
    private JComboBox cboDessert;
    private JComboBox cboDrink;
    private JButton btnOrder;
    private JButton btnBasket;
    private JButton btnMenu;
    private JLabel basketAmount;
    private UserMenu UserMenu;
    private String name;
    private String calories;
    private String description;
    private String category;
    private ArrayList<String> main_items=new ArrayList<String>();
    private int basketValue;

    public FoodOrder(){
        super("Food Order", "Main Menu", "UserMenu");

    }

    /**
     * Adds the generic Gui elements and adds the action listeners for the buttons.
     */
    public void displayFoodOrder() {

        setFoodTypes();
        frame.add(panel2, BorderLayout.CENTER);

        btnOrder.addActionListener(new ActionListener() {
            @Override
            //This gets the selected items from the combo boxes and adds them to an array.
            public void actionPerformed(ActionEvent e) {
                String[] chosenItems = {(cboStarter.getSelectedItem()).toString(),(cboMain.getSelectedItem()).toString(),
                        (cboDessert.getSelectedItem()).toString(),(cboDrink.getSelectedItem()).toString()};
                addItemsToDatabase(chosenItems);
                JOptionPane.showMessageDialog(null,"Added to your basket!");

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
        btnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FoodMenu foodMenu = new FoodMenu();
                foodMenu.displayMenu();
                frame.dispose();
            }
        });

        DisplayGenericElements();
    }

    /**
     * This moves every item in the array into the basket table in the database.
     *
     * @param chosenItems
     * This is the array of chosen items.
     */
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

    /**
     * A switch statement is used to sort all foods into the correct combo box depending on their category.
     *
     * I.e Starter, Main, Dessert or Drink.
     */
    public void setFoodTypes(){
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
        }//try
    }

    /**
     * This was created for the test. This is a bad and inefficient way of writing unit tests but the code was not written in the correct
     * way to start with. This is called using a reflection.
     *
     * @return
     * Returns the amount of items in the combo box.
     */
    public int returnValuesOfDrinkCBO(){
        return cboDrink.getItemCount();
    }
}
