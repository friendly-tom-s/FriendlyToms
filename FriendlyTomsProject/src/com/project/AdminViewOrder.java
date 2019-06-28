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

    private JPanel panelNew = new JPanel(new BorderLayout());

    public AdminViewOrder() {

        super("View Order", "Back", "AdminMenu");
    }

    public void displayAdminElements() {
        DisplayOrderHistory();
        frame.remove(panelMain);
        panelNew.add(panelMain, BorderLayout.NORTH);
        panelNew.add(btnComplete, BorderLayout.CENTER);
        frame.remove(panelMain);
        frame.add(panelNew, BorderLayout.CENTER);
        frame.setSize(500, 450);

        btnComplete.addActionListener(e -> {
            updateSelectedValue();
            String preparedDate = "2019" + "-"+ "%";
            table.setModel(getOrder(getUserType(preparedDate)));
        });

    }

    public void updateSelectedValue() {
        int row= 0;
        try{
            row = table.getSelectedRow();
            String foodName = table.getValueAt(row, 0).toString();
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

    public String getDBValues(ResultSet query, String expectedValue){
        String returnName = null;
        try {
            while (query.next()) {
                returnName = query.getString(expectedValue);
            }
        }
        catch(Exception a){}

        return returnName;

    }


}
