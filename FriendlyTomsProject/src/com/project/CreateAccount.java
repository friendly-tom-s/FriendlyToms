package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.regex.Pattern;

public class CreateAccount extends TemplateGui {
    private JButton btnCreate;
    private JTextField txtUserName;
    private JTextField txtEmail;
    private JPasswordField pswPassword;
    private JPasswordField pswConfirm;
    protected JPanel panel3;
    protected JRadioButton rdoAdmin;
    private User user = new User();
    mariadb db_connector = new mariadb();


    public CreateAccount(String guiName, String buttonVar, String previousWin) {

        super(guiName, buttonVar, previousWin);
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUserData();
                confirmUserData();
            }//action performed
        });//create_account_button

//        viewAllDataButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                db_connector.viewAllData();
//            }
//        });
    }//create_account

    public void setUserData(){
        //user.setFirst_name(first_name_textfield.getText());
        //user.setLast_name(last_name_textfield.getText());
        user.setUsername(txtUserName.getText());
        user.setConfirmPassword(new String (pswPassword.getPassword()));
        user.setPassword(new String (pswPassword.getPassword()));
        user.setEmail(txtEmail.getText());

        if (rdoAdmin.isSelected())
        {
            user.setAdminStatus(1);
        }
        else
        {
            user.setAdminStatus(0);
        }

        //user.setPhone_number(phone_number_textfield.getText());
    }

    public void confirmUserData(){

        boolean input_errors = false;
        if(Pattern.matches("[a-zA-Z0-9]{3,20}", user.getPassword())) {
            txtUserName.setText("Username:");
        } else {
            txtUserName.setText("Username: must contain 3-20 lowercase characters");
            input_errors = true;
        }
        //validate password
        if(Pattern.matches("[a-zA-Z0-9]{3,20}", user.getPassword())) {
            pswPassword.setText("Password:");
        } else {
            pswPassword.setText("Password: must contain 3-20 characters made up of alpha-numeric characters and include at least one special character");
            input_errors = true;
        }
        //validate passwords match
        if(Pattern.matches(user.getPassword(), user.getConfirm_password())) {
            pswConfirm.setText("Confirm Password:");
        } else {
            pswConfirm.setText("Confirm Password: Passwords must match");
            input_errors = true;
        }

        //to check if user being created already exists
        if(input_errors != true) {
            //READ CODE
            //ResultSet read_query = db_connector.read_query("SELECT user_id FROM users WHERE username='" + user.getUsername() + "'");
            ResultSet read_query = db_connector.prepared_read_query("SELECT user_id FROM users WHERE username=?", user.getUsername());

            int user_id = 0;

            try {
                if (!read_query.next()) {
                    System.out.println("success: no duplicate");

                } else {
                    System.out.println(user.getUsername() + "here 22");
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
            DatabaseHash PBKDF2_class = new DatabaseHash();

            try {
                String generatedSecuredPasswordHash = PBKDF2_class.generateStorngPasswordHash(user.getPassword());
                System.out.println(generatedSecuredPasswordHash);

                String[] parts = generatedSecuredPasswordHash.split(":");
                int iterations = Integer.parseInt(parts[0]);
                String salt = parts[1];
                String hash = parts[2];//System.out.println("iterations: " + iterations + " hash: " + hash + " salt: " + salt);

                //WRITE CODE
                mariadb db_connector = new mariadb();
                //boolean write_query = db_connector.write_query("INSERT INTO users (username,salt,hash,is_admin) VALUES ('" + user.getUsername() + "','" + salt + "','" + hash + "','0')");
                boolean write_query = db_connector.prepared_write_query("INSERT INTO users (username,salt,hash,is_admin) VALUES (?, ?, ?, ?)", user.getUsername(), salt, hash, user.getAdminStatus());
                System.out.println("Was the insert successful: " + write_query);

            } catch (Exception NoSuchAlgorithmException){
                System.err.println("Got an exception! EXITING ");
                System.err.println(NoSuchAlgorithmException.getMessage());
                System.exit(1);
            }//try
        }//input_errors

    }

    public void displayCreate(){
        LoginForm loginForm = new LoginForm();
        DisplayGenericElements();
        rdoAdmin.setVisible(false);
        frame.add(panel3, BorderLayout.CENTER);
    }
}
