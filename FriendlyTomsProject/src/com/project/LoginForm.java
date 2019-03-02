package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm {
    private JPanel panel1;
    private JButton btnCreate;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JFrame frame;


    public LoginForm(){
        frame = new JFrame("GUIForm1");

    }

    public void displayLogin(){
        UserMenu userMenu = new UserMenu();
        CreateAccount createAccount = new CreateAccount();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(panel1);
        frame.setVisible(true);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userMenu.displayMenu();
                frame.dispose();
            }
        });

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount.displayCreate();
                frame.dispose();
            }
        });

    }
}
