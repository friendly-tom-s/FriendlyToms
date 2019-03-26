package com.project;

import javax.swing.*;
import java.awt.*;

public class OrderHistory extends TemplateGui {
    private JComboBox cboOrder;
    private JList lstOrderDetails;
    private JPanel panel2;

    public OrderHistory(){super("Order History", "Main Menu", "UserMenu");}

    public void DisplayOrderHistory(){
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();
    }
}
