package com.project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

/**
 * This is simply a Gui that has buttons taking the user to the next Guis.
 */
public class UserMenu extends TemplateGui {
    private JButton btnOrderFood;
    private StyledButtonUI testButton = new StyledButtonUI();
    private JButton btnHistory;
    private JButton btnBook;
    private JPanel panel2;
    private JLabel lblText;
    private JLabel lblMenu;
    private LoginForm LoginForm;
    private FoodOrder FoodOrder;
    private TableBooking TableBooking;
    private OrderHistory orderHistory;
    private User loggedUser;
    private JPanel mainUserPanel = new JPanel();

    public UserMenu(){

        super("User Menu", "Logout", "LoginForm");
    }

    public void setLoggedInUser(User loggedUser){
        this.loggedUser = loggedUser;
    }

    public void setIconImage(){
        Image image = null;
        try {
            URL url = new URL("https://i.pinimg.com/originals/f8/3b/b5/f83bb57b8b2ca37123e77ab5fffb7d76.jpg");
            image = ImageIO.read(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        lblMenu.setIcon(new ImageIcon(image));
    }

    public void displayUserMenu(){
        mainUserPanel.add(btnOrderFood);
        btnOrderFood.setUI(new StyledButtonUI());
        mainUserPanel.add(btnBook);
        btnBook.setUI(new StyledButtonUI());
        mainUserPanel.add(btnHistory);
        btnHistory.setUI(new StyledButtonUI());
        mainUserPanel.add(lblText);
        setIconImage();
        mainUserPanel.add(lblMenu);
        lblText.setText("Welcome to Friendly Tom's Pub. Here we are devoted to blah blah blah");

        frame.add(mainUserPanel, BorderLayout.CENTER);
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
