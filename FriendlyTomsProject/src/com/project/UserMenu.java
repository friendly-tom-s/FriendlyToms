package com.project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * This is simply a Gui that has buttons taking the user to the next Guis.
 */
public class UserMenu extends TemplateGui {
    private JButton btnOrderFood;
    private JButton btnHistory;
    private JButton btnBook;
    private JPanel panel2;
    private JLabel lblText;
    private JLabel lblMenu;
    private JButton btnBooking;
    private FoodOrder FoodOrder;
    private TableBooking TableBooking;
    private OrderHistory orderHistory;
    private User loggedUser;
    private JPanel mainUserPanel = new JPanel();

    public UserMenu(){

        super("User Menu", "Logout", "LoginForm");
    }

    /**
     * This sets the user that is currently logged in.
     * @param loggedUser
     * The user is taken from the login screen.
     */
    public void setLoggedInUser(User loggedUser){
        this.loggedUser = loggedUser;
    }

    /**
     * The basket icon is taken from the internet.
     */
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

    /**
     * Typical GUI set up method but the buttons used are styled buttons.
     */
    public void displayUserMenu(){
        mainUserPanel.add(btnOrderFood);
        btnOrderFood.setUI(new StyledButtonUI());
        mainUserPanel.add(btnBook);
        btnBook.setUI(new StyledButtonUI());
        mainUserPanel.add(btnHistory);
        btnHistory.setUI(new StyledButtonUI());
        btnBooking.setUI(new StyledButtonUI());
        mainUserPanel.add(btnBooking);
        mainUserPanel.add(lblText);
        setIconImage();
        mainUserPanel.add(lblMenu);
        lblText.setText("Welcome to Friendly Tom's pub, the friendliest pub in the village.");
        frame.add(mainUserPanel, BorderLayout.CENTER);
        DisplayGenericElements();
        frame.setSize(520, 400);

        btnOrderFood.addActionListener(e -> {
            FoodOrder = new FoodOrder();
            FoodOrder.displayFoodOrder();
            frame.dispose();
        });

        btnBook.addActionListener(e -> {
            TableBooking = new TableBooking();
            TableBooking.displayTableBooking();
            frame.dispose();
        });

        btnHistory.addActionListener(e -> {
            orderHistory = new OrderHistory("Order History", "Back", "UserMenu");
            orderHistory.DisplayOrderHistory();
            frame.dispose();
        });
        btnBooking.addActionListener(e -> {
            ViewBookings viewBookings = new ViewBookings("UserMenu");
            viewBookings.DisplayViewBooking();
            frame.dispose();
        });
    }
}
