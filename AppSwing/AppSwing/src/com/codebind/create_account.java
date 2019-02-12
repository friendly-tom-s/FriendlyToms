package com.codebind;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.regex.Pattern;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class create_account {
    private JFormattedTextField username_textfield;
    private JButton create_account_button;
    private JFormattedTextField first_name_textfield;
    private JFormattedTextField last_name_textfield;
    private JFormattedTextField password_textfield;
    private JFormattedTextField confirm_password_textfield;
    private JFormattedTextField email_textfield;
    private JFormattedTextField phone_number_textfield;
    private JPanel create_account_panel;
    private JLabel username_label;
    private JLabel password_label;
    private JLabel confirm_password_label;
    private JLabel email_label;
    private JLabel phone_number_label;
    private JLabel first_name_label;
    private JLabel last_name_label;
    private JButton viewAllDataButton;
    mariadb db_connector = new mariadb();
    userParent userParent = new userParent();

    public create_account() {
        create_account_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUserData();
                confirmUserData();
            }//action performed
        });//create_account_button

        viewAllDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db_connector.viewAllData();
            }
        });
    }//create_account

    public void setUserData(){
        userParent.setFirst_name(first_name_textfield.getText());
        userParent.setLast_name(last_name_textfield.getText());
        userParent.setLast_name(username_textfield.getText());
        userParent.setConfirmPassword(confirm_password_textfield.getText());
        userParent.setPassword(password_textfield.getText());
        userParent.setEmail(email_textfield.getText());
        userParent.setPhone_number(phone_number_textfield.getText());
    }

    public void confirmUserData(){

        boolean input_errors = false;
        if(Pattern.matches("[a-zA-Z0-9]{3,20}", userParent.getPassword())) {
            username_label.setText("Username:");
        } else {
            username_label.setText("Username: must contain 3-20 lowercase characters");
            input_errors = true;
        }
        //validate password
        if(Pattern.matches("[a-zA-Z0-9]{3,20}", userParent.getPassword())) {
            password_label.setText("Password:");
        } else {
            password_label.setText("Password: must contain 3-20 characters made up of alpha-numeric characters and include at least one special character");
            input_errors = true;
        }
        //validate passwords match
        if(Pattern.matches(userParent.getPassword(), userParent.getConfirm_password())) {
            confirm_password_label.setText("Confirm Password:");
        } else {
            confirm_password_label.setText("Confirm Password: Passwords must match");
            input_errors = true;
        }

        //to check if user being created already exists
        if(input_errors != true) {
            //READ CODE
            ResultSet read_query = db_connector.read_query("SELECT user_id FROM users WHERE username='" + userParent.getUsername() + "'");

            int user_id = 0;

            try {
                if (!read_query.next()) {
                    System.out.println("success: no duplicate");

                } else {
                    System.out.println("error: username already exists");
                    user_id = read_query.getInt("user_id");
                    input_errors = true;

                }
            } catch (Exception a) {
                System.err.println("Got an exception!");
                System.err.println(a.getMessage());
                System.exit(1);
            }//try
        }//input_errors

        //hash and salt password then add it to the database
        if(input_errors != true) {
            PBKDF2 PBKDF2_class = new PBKDF2();

            try {
                String generatedSecuredPasswordHash = PBKDF2_class.generateStorngPasswordHash(userParent.getPassword());
                System.out.println(generatedSecuredPasswordHash);

                String[] parts = generatedSecuredPasswordHash.split(":");
                int iterations = Integer.parseInt(parts[0]);
                String salt = parts[1];
                String hash = parts[2];//System.out.println("iterations: " + iterations + " hash: " + hash + " salt: " + salt);

                //WRITE CODE
                mariadb db_connector = new mariadb();
                boolean write_query = db_connector.write_query("INSERT INTO users (username,salt,hash) VALUES ('" + userParent.getUsername() + "','" + salt + "','" + hash + "')");
                System.out.println("Was the insert successful: " + write_query);


            } catch (Exception NoSuchAlgorithmException){
                System.err.println("Got an exception! EXITING ");
                System.err.println(NoSuchAlgorithmException.getMessage());
                System.exit(1);
            }//try
        }//input_errors

    }

    public void initialiseframe() {
        JFrame frame = new JFrame("Tom's Pub login");
        frame.setSize(800,600);
        frame.setContentPane(new create_account().create_account_panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);


        //frame.setBorder

        //frame.pack();
        frame.setVisible(true);

        /*username_textfield.setText("haydn");
        password_textfield.setText("test");
        confirm_password_textfield.setText("test");*/


        //font size
        //https://stackoverflow.com/questions/2715118/how-to-change-the-size-of-the-font-of-a-jlabel-to-take-the-maximum-size


        //frame.   .setFont(new Font("Serif", Font.PLAIN, 14));
    }//initialiseframe


}
