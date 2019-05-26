package com.project;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemplateGui {
    private JPanel panel1;
    private JButton backButtonButton;
    JFrame frame;
    private String previousWin;

    public TemplateGui(String guiName, String buttonVar, String previousWin){
        frame = new JFrame(guiName);
        backButtonButton.setText(buttonVar);
        this.previousWin = previousWin;
    }

    public void DisplayGenericElements(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.add(panel1,BorderLayout.PAGE_END);
        frame.setVisible(true);
        backButtonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Pressed");
                frame.dispose();
                previousWinLogic();
            }
        });
    }

    public void previousWinLogic(){
        if (previousWin.equals("AdminMenu")){
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.displayAdminMenu();
        }
        if (previousWin.equals("LoginForm")){
            LoginForm loginForm = new LoginForm();
            loginForm.displayLogin();
        }
        if (previousWin.equals("UserMenu")){
            UserMenu userMenu = new UserMenu();
            userMenu.displayUserMenu();
        }
        if (previousWin.equals("FoodOrder")){
            FoodOrder foodOrder = new FoodOrder();
            foodOrder.displayFoodOrder();
        }
    }



}
