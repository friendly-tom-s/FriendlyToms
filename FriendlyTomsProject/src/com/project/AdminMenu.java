package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu extends TemplateGui {
    private JButton btnSales;
    private JButton btnStock;
    private JButton btnAccounts;
    private JPanel panel2;

    public AdminMenu(){super("Admin Menu", "Logout", "LoginForm");


    }

    public void displayAdminMenu() {
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();

        btnAccounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageAccounts manageAccounts = new ManageAccounts();
                manageAccounts.DisplayManageAccounts();

            }
        });

        btnStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageStock manageStock = new ManageStock();
                manageStock.DisplayStock();
            }
        });
    }
}
