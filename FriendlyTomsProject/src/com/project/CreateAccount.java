package com.project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccount {
    private JButton btnCreate;
    private JButton btnMenu;
    private JTextField txtFirstName;
    private JTextField txtSurname;
    private JPasswordField pswPassword;
    private JPasswordField pswConfirm;
    private JPanel panel1;
    JFrame frame;


    public CreateAccount(){frame = new JFrame("GUIForm1");}

    public void displayCreate(){
        LoginForm loginForm = new LoginForm();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(panel1);
        frame.setVisible(true);

        btnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                loginForm.displayLogin();
                frame.dispose();
            }

        });
    }
}
