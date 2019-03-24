package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.regex.Pattern;
import javax.swing.border.Border;

public class LoginForm {
    private JPanel panel1;
    private JButton btnCreate;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    JFrame frame;
    private User user = new User();
    private int adminPriv= 0 ;


    public LoginForm(){
        frame = new JFrame("GUIForm1");

    }
    public void DisplayGenericElements(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.add(panel1, BorderLayout.PAGE_END);
        frame.setVisible(true);

    }

    public Boolean verifyInput(){

        System.out.println("Test");
        //Jake has added a comment and he should be working in a branch.


        //This is added to the master

        boolean input_errors = false;
        //this is a test
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
        ResultSet read_query = db_connector.read_query("SELECT user_id,username,salt,hash,is_admin FROM users WHERE username='" + user.getUsername() + "'");

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
                adminPriv = read_query.getInt("is_admin");
            }
        }
        catch (Exception a)
        {
            System.err.println("Got an exception!");
            System.err.println(a.getMessage());
            //System.exit(1);
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

        CreateAccount createAccount = new CreateAccount();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(panel1);
        frame.setVisible(true);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                user.setUsername(txtUsername.getText());
                String newVar = new String (txtPassword.getPassword());
                user.setPassword(newVar);
                System.out.println(newVar+ " + " +user.getPassword()+ " I am here");
                boolean credCheck  = initCompare();
                System.out.println("CHECK THIS VAR" + adminPriv);
                if (credCheck){
                    if(adminPriv== 1){
                        AdminMenu adminMenu = new AdminMenu();
                        adminMenu.displayAdminMenu();
                    }
                    else {
                        UserMenu userMenu = new UserMenu();
                        userMenu.displayUserMenu();
                    }
                    frame.dispose();
                }

                else{
                    System.out.println("Not matched - exiting");
                    JOptionPane.showMessageDialog(null, "Incorrect Username or Password");

                }


            }
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
}
