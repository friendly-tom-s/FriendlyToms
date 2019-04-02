package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageAccounts extends TemplateGui {
    private JButton btnView;
    private JButton btnDelete;
    private JButton btnCreate;
    private JPanel panel2;
    private AdminCreation adminCreation;

    public ManageAccounts(){super("Manage Accounts", "Main Menu", "AdminMenu");
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*frame.dispose();
                accountManagement accountManagement = new accountManagement();
                accountManagement.renderPage();*/
            }
        });
    }

    public void DisplayManageAccounts(){
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminCreation = new AdminCreation();
                adminCreation.DisplayGui();
                frame.dispose();
            }
        });

    }
}
