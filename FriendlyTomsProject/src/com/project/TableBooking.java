package com.project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableBooking{
    private JTextField txtName;
    private JComboBox cboDate;
    private JComboBox cboTime;
    private JComboBox cboPeople;
    private JButton btnBook;
    private JButton btnMainMenu;
    private UserMenu UserMenu;
    private JFrame frame;
    private JPanel panel1;



    public TableBooking(){frame = new JFrame("Food Order");


    }

    public void displayTableBooking() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(panel1);
        frame.setVisible(true);

    btnMainMenu.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            UserMenu = new UserMenu();
            UserMenu.displayUserMenu();
            frame.dispose();
        }
    });
    }
}
