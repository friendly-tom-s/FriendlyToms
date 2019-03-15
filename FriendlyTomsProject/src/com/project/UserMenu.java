package com.project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMenu {
    private JButton btnOrderFood;
    private JButton btnBack;
    private JButton btnHistory;
    private JButton btnBook;
    private JPanel panel1;
    private JFrame frame;
    private LoginForm LoginForm;

    public UserMenu(){frame = new JFrame("GUIForm1");

    }

    public void displayMenu(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(panel1);
        frame.setVisible(true);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginForm = new LoginForm();
                LoginForm.displayLogin();
                frame.dispose();
            }
        });


    }
}
