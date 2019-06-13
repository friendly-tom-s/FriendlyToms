package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class LoginForm extends TemplateGui{
    private JPanel panel2;
    private JButton btnCreate;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private User user = new User();
    private int adminPriv= 0 ;
    private String userID;


    public LoginForm(){
        super("Login Form", "Quit", "null");}

    public Boolean verifyInput(){

        System.out.println("Test");

        boolean input_errors = false;

        if(Pattern.matches("[a-zA-Z0-9]{3,20}", user.getUsername())) {
            txtUsername.setText("Username:");
        } else {
            txtUsername.setText("Username: please enter a username");
            input_errors = true;
        }

        if(Pattern.matches("[a-zA-Z0-9]{3,20}", user.getPassword())) {
            txtUsername.setText("Password:");
        } else {
            txtPassword.setText("Password: please enter a password");
            input_errors = true;
        }
        return  input_errors;

    }

    public boolean initCompare(){
        boolean retCheckVar = false;
        boolean input_errors = verifyInput();

        //READ CODE
        com.project.mariadb db_connector = new com.project.mariadb();
        //ResultSet read_query = db_connector.read_query("SELECT user_id,username,salt,hash,is_admin FROM users WHERE username='" + user.getUsername() + "'");

        ResultSet read_query = db_connector.prepared_read_query("SELECT user_id,username,salt,hash,is_admin FROM users WHERE username=?", user.getUsername());

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
                userID = read_query.getString("user_id");
                adminPriv = read_query.getInt("is_admin");
            }
        }
        catch (Exception a)
        {
            System.err.println("Got an exception!");
            System.err.println(a.getMessage());
        }//try


        if(input_errors != true) {
            DatabaseHash PBKDF2_class = new DatabaseHash();

            try {
                boolean matched = PBKDF2_class.validatePassword(user.getPassword(), "1000:" + salt + ":" + hash);

                System.out.println("is the password correct: " + matched);
                System.out.println("The matched var is "+ matched);
                if(matched){retCheckVar = true;}

            } catch (Exception NoSuchAlgorithmException) {
                System.err.println("Got an exception! EXITING ");
                System.err.println(NoSuchAlgorithmException.getMessage());
                System.exit(1);

            }
        }
        System.out.println("The return var is "+ retCheckVar);
        return retCheckVar;
    }

    public void displayLogin(){

        CreateAccount createAccount = new CreateAccount("Create Account", "Back", "LoginForm");
        frame.add(panel2, BorderLayout.CENTER);
        DisplayGenericElements();
        database.prepared_write_query("DELETE FROM basket");

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {setUserAttributes();}
        });

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount.displayCreate();
                System.out.println("test");
                frame.dispose();
            }
        });

    }

    public void saveSessionUser(String userID){
        database.prepared_write_query("DELETE FROM loggedSession");
        database.prepared_write_query("INSERT INTO loggedSession (userID) VALUES (?)", userID);
    }

    public void setUserAttributes(){
        user.setUsername(txtUsername.getText());
        String newVar = new String (txtPassword.getPassword());
        user.setPassword(newVar);
        System.out.println(newVar+ " + " +user.getPassword()+ " I am here");
        boolean credCheck  = initCompare();
        System.out.println("CHECK THIS VAR" + adminPriv);
        if (credCheck){
            saveSessionUser(userID);
            if(adminPriv== 1){
                frame.dispose();
                AdminMenu adminMenu = new AdminMenu();
                adminMenu.displayAdminMenu();
            }
            else {
                frame.dispose();
                UserMenu userMenu = new UserMenu();
                userMenu.setLoggedInUser(user);
                userMenu.displayUserMenu();
            }
        }
        else{
            System.out.println("Not matched - exiting");
            JOptionPane.showMessageDialog(null, "Incorrect Username or Password");

        }
    }
}
