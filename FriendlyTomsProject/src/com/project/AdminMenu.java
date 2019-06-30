package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A  class that displays the Admin Menu, this allows for the buttons to take the user to the
 * correct place.
 *
 */
public class AdminMenu extends TemplateGui {
    private JButton btnSales;
    private JButton btnStock;
    private JButton btnAccounts;
    private JPanel panel2;
    private JButton btnBooking;

    public AdminMenu(){super("Admin Menu", "Logout", "LoginForm");}

    /**
     * The GUI is created and the button listeners set, this is where the user gets taken to the correct place.
     */
    public void displayAdminMenu() {
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();

        btnSales.addActionListener(e -> {
            AdminViewOrder adminViewOrder = new AdminViewOrder();
            adminViewOrder.displayAdminElements();
            frame.dispose();
        });

        btnAccounts.addActionListener(e -> {
            ManageAccounts manageAccounts = new ManageAccounts();
            manageAccounts.DisplayManageAccounts();
            frame.dispose();
        });

        btnStock.addActionListener(e -> {
            ManageStock manageStock = new ManageStock();
            manageStock.DisplayStock();
            frame.dispose();
        });

        btnBooking.addActionListener(e -> {
            ViewBookings viewBookings = new ViewBookings("AdminMenu");
            viewBookings.DisplayViewBooking();
            frame.dispose();
        });
    }
}
