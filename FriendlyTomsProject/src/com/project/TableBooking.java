package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableBooking extends TemplateGui{
    private JTextField txtName;
    private JComboBox cboDate;
    private JComboBox cboTime;
    private JComboBox cboPeople;
    private JButton btnBook;
    private JButton btnMainMenu;
    private UserMenu UserMenu;
    private JPanel panel2;



    public TableBooking(){super("Table Booking", "Main Menu", "UserMenu");}

    public void displayTableBooking() {
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();
    }
}
