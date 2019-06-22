package com.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class AddMenuItem extends TemplateGui {
    private JPanel panel1;
    private JButton btnSubmit;
    private JTextField txtname;
    private JTextField txtCalories;
    private JTextField txtURL;
    private JTextField txtDescription;
    private JTextField txtPrice;
    private JComboBox cmbCategory;
    private JSpinner spnStock;

    private JPanel panelMain = new JPanel(new BorderLayout());


    public AddMenuItem() {
        super("Menu", "Back", "ManageStock");
    }

    public void DisplayAddMenuItem()
    {
        frame.add(panel1, BorderLayout.CENTER);
        DisplayGenericElements();
    }
}