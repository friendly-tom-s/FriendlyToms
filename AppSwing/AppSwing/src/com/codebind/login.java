package com.codebind;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.regex.*;

public class login {
    private JFormattedTextField username_textfield;
    private JTextField password_textfield;
    private JButton login_button;
    private JButton exit_button;
    private JLabel username_label;
    private JLabel password_label;
    private JPanel login_panel;
    private userParent userParent = new userParent();

    public login() {
        login_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //https://www.javatpoint.com/java-rege
                userParent.setUsername(username_textfield.getText());
                userParent.setUsername(password_textfield.getText());
                initCompare();
            }
        });

        exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public Boolean verifyInput(){

        System.out.println("Test");

        boolean input_errors = false;

        if(Pattern.matches("[a-zA-Z0-9]{3,20}", userParent.getUsername())) {
            username_label.setText("Username:");
        } else {
            username_label.setText("Username: please enter a username");
            input_errors = true;
        }

        if(Pattern.matches("[a-zA-Z0-9]{3,20}", userParent.getPassword())) {
            password_label.setText("Password:");
        } else {
            password_label.setText("Password: please enter a password");
            input_errors = true;
        }
    return  input_errors;

    }

    public void initCompare(){
        Boolean input_errors = verifyInput();

        //READ CODE
        mariadb db_connector = new mariadb();
        ResultSet read_query = db_connector.read_query("SELECT user_id,username,salt,hash FROM users WHERE username='" + userParent.getUsername() + "'");

        int user_id = 0;
        String hash = "";
        String salt = "";


        try {
            if(!read_query.next()) {
                System.out.println("No matching username found");
                input_errors = true;
            } else {
                System.out.println("matching username found: success");
                String username = read_query.getString("username");
                hash = read_query.getString("hash");
                salt = read_query.getString("salt");
            }
        }
        catch (Exception a)
        {
            System.err.println("Got an exception!");
            System.err.println(a.getMessage());
            //System.exit(1);
        }//try


        if(input_errors != true) {
            PBKDF2 PBKDF2_class = new PBKDF2();

            try {
                boolean matched = PBKDF2_class.validatePassword(userParent.getPassword(), "1000:" + salt + ":" + hash);

                System.out.println("is the password correct: " + matched);

            } catch (Exception NoSuchAlgorithmException) {
                System.err.println("Got an exception! EXITING ");
                System.err.println(NoSuchAlgorithmException.getMessage());
                System.exit(1);
            }
        }
    }

    public void initialiseframe() {
        JFrame frame = new JFrame("Tom's Pub login");
        frame.setSize(800,600);
        frame.setContentPane(new login().login_panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //resizeable
        //https://coderanch.com/t/628321/java/disable-resizing-jframe
        frame.setResizable(false);

        //frame.pack();
        frame.setVisible(true);

        //font size
        //https://stackoverflow.com/questions/2715118/how-to-change-the-size-of-the-font-of-a-jlabel-to-take-the-maximum-size

        //frame.   .setFont(new Font("Serif", Font.PLAIN, 14));
    }
}
