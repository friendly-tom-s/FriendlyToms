package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FoodOrder extends TemplateGui {
    private JComboBox cboStarter;
    private JPanel panel2;
    private JComboBox cboMain;
    private JComboBox cboDessert;
    private JComboBox cboDrink;
    private JButton btnOrder;
    private UserMenu UserMenu;

    public FoodOrder(){super("Food Order", "Main Menu", "UserMenu");}

    public void displayFoodOrder() {
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();


    }
}
