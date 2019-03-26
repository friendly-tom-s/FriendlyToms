package com.project;

import javax.swing.*;
import java.awt.*;

public class ManageAccounts extends TemplateGui {
    private JButton btnView;
    private JButton btnDelete;
    private JButton btnCreate;
    private JPanel panel2;

    public ManageAccounts(){super("Manage Accounts", "Main Menu", "AdminMenu");}

    public void DisplayManageAccounts(){
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();
    }
}
