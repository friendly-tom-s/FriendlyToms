package com.project;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

/**
 * This servers the exact same purpose as the OrderHistory class so it inherits it.
 * <p>
 * The logic in OrderHistory class depends on the value of the previousWin variable, this is why a separate class is used.
 */
public class AdminViewOrder extends OrderHistory {


    public AdminViewOrder() {

        super("View Order", "Back", "AdminMenu");
    }

    public void displayAdminElements() {
        DisplayOrderHistory();
        frame.remove(panelMain);
        panelMainBorder.add(panelMain, BorderLayout.NORTH);
        panelMainBorder.remove(btnUpdate);
        panelMainBorder.add(btnUpdate, BorderLayout.CENTER);
        panelMainBorder.add(btnComplete, BorderLayout.EAST);
        frame.remove(panelMain);
        frame.add(panelMainBorder, BorderLayout.CENTER);
        frame.setSize(500, 420);

        btnComplete.addActionListener(e -> {
            updateSelectedValue();
            String preparedDate = "2019" + "-"+ "%";
            table.setModel(getOrder(getUserType(preparedDate)));
        });
    }

    /**
     * The admin can update the selected item, this is done from here.
     */
    private void updateSelectedValue() {
        int row;
        try{
            row = table.getSelectedRow();
            String foodName = table.getValueAt(row, 0).toString();
            table.setValueAt("YES", row, 3);
            String userValue = table.getValueAt(row, 3).toString();
            String date = table.getValueAt(row, 1).toString();
            String username = table.getValueAt(row, 2).toString();
            ResultSet foodQuery = database.prepared_read_query("SELECT menu_id FROM menu WHERE name = ?", foodName);
            ResultSet nameQuery = database.prepared_read_query("SELECT user_id FROM users WHERE username = ?", username);

            boolean write_query = database.prepared_write_query("UPDATE orders SET completed =? WHERE order_date = ? " +
                    "AND userID =? AND foodItem = ?", userValue,date, getDBValues(nameQuery, "user_id"), getDBValues(foodQuery, "menu_id"));}
        catch (Exception a){JOptionPane.showMessageDialog(null, "Please fill out the correct data field");
        }

    }

    /**
     * Used to get a single item from a DB.
     * @param query
     * SQL command
     * @param expectedValue
     * Column name
     * @return
     * The single value
     */
    private String getDBValues(ResultSet query, String expectedValue){
        String returnName = null;
        try {
            while (query.next()) {
                returnName = query.getString(expectedValue);
            }
        }
        catch(Exception ignored){}

        return returnName;

    }


}
