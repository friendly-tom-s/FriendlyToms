package com.project;

import javax.swing.*;

public class AdminMenu {
    private JButton btnSales;
    private JButton btnStock;
    private JButton btnAccounts;
    private JFrame frame;
    private JPanel panel1;

    public AdminMenu(){frame = new JFrame("GUIForm1");}

    public void displayAdminMenu() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(panel1);
        frame.setVisible(true);
    }
}
