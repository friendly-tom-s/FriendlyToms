package com.project;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UserMenu extends LoginForm{
    private JButton btnOrderFood;
    private JButton btnBack;
    private JButton btnHistory;
    private JButton btnBook;
    private JPanel panel2;
    //JFrame frame;
    private LoginForm LoginForm;
    private FoodOrder FoodOrder;
    private TableBooking TableBooking;

    public UserMenu(){super();

    }

    public void displayUserMenu(){
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginForm = new LoginForm();
                LoginForm.displayLogin();
                frame.dispose();
            }
        });

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
//
            }
        });







    }
}
