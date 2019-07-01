package com.tests;

import com.project.FoodOrder;
import com.project.mariadb;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;

public class FoodOrderTest {

    /**
     * This makes sure that the cbos are handling as expected. It checks that there is the expected amount in the DB.
     */
    @Test
    public void test_that_all_drink_dropdown_has_right_amount(){
        FoodOrder foodOrder = new FoodOrder();
        foodOrder.setFoodTypes();

        int cboCompare = foodOrder.returnValuesOfDrinkCBO();

        assertEquals(getAmountInDatabase("Drink"), cboCompare);

    }

    public int getAmountInDatabase(String itemCompare){
        mariadb mariadb = new mariadb();
        String amountOfItemsString = null;
        ResultSet amountOfItems = mariadb.prepared_read_query("SELECT COUNT(category) AS amount FROM menu WHERE category =?", itemCompare);

        try {
            while(amountOfItems.next()) {
                amountOfItemsString = amountOfItems.getString("amount");
            }
        }
        catch (Exception a){System.out.println("Something failed at 2");}//try

        return Integer.parseInt(amountOfItemsString);
    }
}
