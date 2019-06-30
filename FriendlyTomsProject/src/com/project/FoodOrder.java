package com.project;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
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
    private JLabel basketIcon;
    private UserMenu UserMenu;
    private String name;
    private String calories;
    private String description;
    private String category;
    private ArrayList<String> main_items = new ArrayList<String>();
    private int basketValue;
    private Basket basket = new Basket();

    public FoodOrder() {
        super("Food Order", "Main Menu", "UserMenu");
    }

    /**
     * Adds the generic Gui elements and adds the action listeners for the buttons.
     */
    public void displayFoodOrder() {

        setIconImage();
        setTotalcost();
        setFoodTypes();
        frame.add(panel2, BorderLayout.CENTER);

        //This gets the selected items from the combo boxes and adds them to an array.
        btnOrder.addActionListener(e -> {
            String[] chosenItems = {(cboStarter.getSelectedItem()).toString(), (cboMain.getSelectedItem()).toString(),
                    (cboDessert.getSelectedItem()).toString(), (cboDrink.getSelectedItem()).toString()};

            addItemsToDatabase(chosenItems);
            JOptionPane.showMessageDialog(null, "Added to your basket!");
            setTotalcost();

        });
        btnBasket.addActionListener(e -> {
            basket.displayElements();
            frame.dispose();
        });
        btnMenu.addActionListener(e -> {
            FoodMenu foodMenu = new FoodMenu();
            foodMenu.displayMenu();
            frame.dispose();
        });

        DisplayGenericElements();
        frame.setSize(300, 500);

    }

    /**
     * This moves every item in the array into the basket table in the database.
     *
     * @param chosenItems This is the array of chosen items.
     */
    public void addItemsToDatabase(String[] chosenItems) {
        for (String var : chosenItems
        ) {
            boolean catchFail = true;
            switch (var) {
                case "<BREAK>":
                    catchFail = false;
                    break;
                case "Main":
                    catchFail = false;
                    break;
                case "Starter":
                    catchFail = false;
                    break;
                case "Dessert":
                    catchFail = false;
                    break;
                case "Drink":
                    catchFail = false;
                    break;
            }

            if (catchFail == true) {
                ResultSet read_query = database.prepared_read_query("SELECT menu_id FROM menu WHERE name =?", var);
                try {
                    while (read_query.next()) {
                        String foodItem = read_query.getString("menu_id");
                        database.prepared_write_query("INSERT INTO basket (itemID, userID) VALUES (?,?)", foodItem, getUser());
                    }
                } catch (Exception a) {
                    break;
                }//try}

            }
        }
    }

    public void setTotalcost() {
        DefaultTableModel throwawayRS = basket.getListItems();
        String totalcost = String.valueOf(basket.getTotalCost());
        basketAmount.setText("£" + totalcost);
    }

    public void setIconImage() {
        Image image = null;
        try {
            URL url = new URL("http://chittagongit.com/download/107277");
            image = ImageIO.read(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        basketIcon.setIcon(new ImageIcon(image.getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
    }

    /**
     * A switch statement is used to sort all foods into the correct combo box depending on their category.
     * <p>
     * I.e Starter, Main, Dessert or Drink.
     */
    public void setFoodTypes() {
        ResultSet foodQuery = database.prepared_read_query("SELECT * FROM menu");
        cboMain.addItem("Main");
        cboStarter.addItem("Starter");
        cboDessert.addItem("Dessert");
        cboDrink.addItem("Drink");

        try {
            while (foodQuery.next()) {
                name = foodQuery.getString("name");
                main_items.add(name);
                System.out.println(name);
                description = foodQuery.getString("description");
                calories = foodQuery.getString("calories");
                category = foodQuery.getString("category");

                switch (category) {
                    case "Main":
                        cboMain.addItem(name);
                        break;
                    case "Starter":
                        cboStarter.addItem(name);
                        break;
                    case "Dessert":
                        cboDessert.addItem(name);
                        break;
                    case "Drink":
                        cboDrink.addItem(name);
                        break;

                }
            }
        } catch (Exception a) {
            System.err.println("Got an exception!");
            System.err.println(a.getMessage());
        }//try
    }

    /**
     * This was created for the test. This is a bad and inefficient way of writing unit tests but the code was not written in the correct
     * way to start with. This is called using a reflection.
     *
     * @return Returns the amount of items in the combo box.
     */
    public int returnValuesOfDrinkCBO() {
        return cboDrink.getItemCount();
    }
}
