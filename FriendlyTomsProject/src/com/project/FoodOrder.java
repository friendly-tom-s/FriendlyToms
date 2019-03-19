package com.project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FoodOrder {
    private JComboBox cboStarter;
    private JPanel panel1;
    private JFrame frame;
    private JComboBox cboMain;
    private JComboBox cboDessert;
    private JComboBox cboDrink;
    private JButton btnOrder;
    private JButton btnMenu;
    private UserMenu UserMenu;

    public FoodOrder(){frame = new JFrame("Food Order");

            }

    public void displayFoodOrder() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(panel1);
        frame.setVisible(true);

        btnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserMenu = new UserMenu();
                UserMenu.displayUserMenu();
                frame.dispose();
            }
        });
    }
}
