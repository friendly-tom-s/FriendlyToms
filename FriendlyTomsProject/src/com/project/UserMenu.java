package com.project;

import javax.swing.*;

public class UserMenu {
    private JButton btnOrderFood;
    private JButton btnBack;
    private JButton btnHistory;
    private JButton btnBook;
    private JPanel panel1;
    JFrame frame;

    public UserMenu(){frame = new JFrame("GUIForm1");}

    public void displayMenu(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(panel1);
        frame.setVisible(true);
    }
}
