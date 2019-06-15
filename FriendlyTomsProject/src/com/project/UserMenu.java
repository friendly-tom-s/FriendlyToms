package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is simply a Gui that has buttons taking the user to the next Guis.
 */
public class UserMenu extends TemplateGui {
    private JButton btnOrderFood;
    private JButton btnHistory;
    private JButton btnBook;
    private JPanel panel2;
    private LoginForm LoginForm;
    private FoodOrder FoodOrder;
    private TableBooking TableBooking;
    private OrderHistory orderHistory;
    private User loggedUser;

    public UserMenu(){

        super("User Menu", "Logout", "LoginForm");
    }

    public void setLoggedInUser(User loggedUser){
        this.loggedUser = loggedUser;
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
                orderHistory = new OrderHistory("Order History", "Back", "UserMenu");
                orderHistory.DisplayOrderHistory();
                frame.dispose();
            }
        });
    }
}
