package com.project;

import javax.swing.*;
import java.awt.*;

public class AdminCreation extends TemplateGui {
    private JTextField btnName;
    private JTextField btnUsername;
    private JTextField btnPassword;
    private JRadioButton rdoAdmin;
    private JPanel panel2;

    public AdminCreation(){super("AdminCreation", "Back", "AdminMenu");}

    public void displayGui(){
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();
    }
}
