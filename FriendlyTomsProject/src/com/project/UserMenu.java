package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMenu extends TemplateGui {
    private JButton btnOrderFood;
    private JButton btnHistory;
    private JButton btnBook;
    private JPanel panel2;
    private LoginForm LoginForm;
    private FoodOrder FoodOrder;
    private TableBooking TableBooking;
    private OrderHistory orderHistory;

    public UserMenu(){
        super("User Menu", "Logout", "LoginForm");
    }

    public void displayUserMenu(){
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();

        btnOrderFood.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FoodOrder = new FoodOrder();
                FoodOrder.displayFoodOrder();
                frame.dispose();
            }
        });

        btnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableBooking = new TableBooking();
                TableBooking.displayTableBooking();
                frame.dispose();
            }
        });

        btnHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderHistory = new OrderHistory();
                orderHistory.DisplayOrderHistory();
            }
        });
    }
}
