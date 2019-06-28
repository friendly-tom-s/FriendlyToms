package com.project;

import javax.swing.*;
import java.awt.*;

/**
 * This servers the exact same purpose as the OrderHistory class so it inherits it.
 *
 * The logic in OrderHistory class depends on the value of the previousWin variable, this is why a separate class is used.
 */
public class AdminViewOrder extends OrderHistory {

    private JPanel panelNew = new JPanel(new BorderLayout());

    public AdminViewOrder(){

        super("View Order", "Back", "AdminMenu");
    }

    public void displayAdminElements(){
        DisplayOrderHistory();
        frame.remove(panelMain);
        panelNew.add(panelMain, BorderLayout.NORTH);
        panelNew.add(btnComplete, BorderLayout.CENTER);
        frame.remove(panelMain);
        frame.add(panelNew, BorderLayout.CENTER);
        frame.setSize(500, 450);

    }
}
