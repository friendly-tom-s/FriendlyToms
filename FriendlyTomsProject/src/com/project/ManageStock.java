package com.project;
import javax.swing.*;
import java.awt.*;

public class ManageStock extends TemplateGui{
    private JPanel panel2;
    private JComboBox cboItem;
    private JTextField txtAddStock;
    private JButton btnAddStock;
    private JButton btnMenu;

    public ManageStock(){ super("Manage Stock", "Main Menu", "AdminMenu");}

    public void DisplayStock(){
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();
    }
}
