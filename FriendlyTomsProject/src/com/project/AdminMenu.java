package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A very simple class that displays the Admin Menu, this allows for the buttons to take the user to the
 * correct place.
 */
public class AdminMenu extends TemplateGui {
    private JButton btnSales;
    private JButton btnStock;
    private JButton btnAccounts;
    private JPanel panel2;

    public AdminMenu(){super("Admin Menu", "Logout", "LoginForm");}

    /**
     * The GUI is created and the button listeners set, this is where the user gets taken to the correct place.
     */
    public void displayAdminMenu() {
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();

        btnSales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminViewOrder adminViewOrder = new AdminViewOrder();
                adminViewOrder.DisplayOrderHistory();
                frame.dispose();
            }
        });

        btnAccounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageAccounts manageAccounts = new ManageAccounts();
                manageAccounts.DisplayManageAccounts();
                frame.dispose();
            }
        });

        btnStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageStock manageStock = new ManageStock();
                manageStock.DisplayStock();
                frame.dispose();
            }
        });
    }
}
