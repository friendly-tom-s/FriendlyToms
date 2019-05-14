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
    private User loggedUser;



    public TableBooking(User loggedUser){
        super("Table Booking", "Main Menu", "UserMenu");
        this.loggedUser = loggedUser;
    }

    public void displayTableBooking() {
        frame.add(panel2, BorderLayout.CENTER);
        txtName.setText(loggedUser.getUsername());
        DisplayGenericElements();
    }

    public String getNameInput(){return txtName.getText();}


}
