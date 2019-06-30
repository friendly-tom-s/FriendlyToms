package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.regex.Pattern;

/**
 * This class is used for users to login and to make sure that the username is saved into the "loggedUsers" database table
 * so that the program can access the database in the future.
 */
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

    /**
     * This functions makes sure that the text has been entered into the text boxes
     *
     * @return
     * A boolean is returned containing either True or False.
     *
     */
    public Boolean verifyInput(){

        boolean input_errors = false;

        if(Pattern.matches("[a-zA-Z0-9]{3,20}", user.getUsername())) {

        } else {
            input_errors = true;
        }

        if(Pattern.matches("[a-zA-Z0-9]{3,20}", user.getPassword())) {

        } else {
            input_errors = true;
        }
        return  input_errors;
    }

    /**
     * This method checks that the username is in the database by running a SELECT statement in the "user" table in the database.
     *
     * If this returns True the password is checked against the program encryption.
     *
     * @return
     * A boolean is return that confirms if the entered data is present in the database or not.
     */
    public boolean initCompare(){
        boolean retCheckVar = false;
        boolean input_errors = verifyInput();

        ResultSet read_query = database.prepared_read_query("SELECT user_id,username,salt,hash,is_admin FROM users WHERE username=?", user.getUsername());
        String hash = "";
        String salt = "";

        try {
            if(!read_query.next()) {
                input_errors = true;
            } else {
                String username = read_query.getString("username");
                hash = read_query.getString("hash");
                salt = read_query.getString("salt");
                userID = read_query.getString("user_id");
                adminPriv = read_query.getInt("is_admin");
            }
        }
        catch (Exception a)
        {
            System.err.println(a.getMessage());
        }

        if(input_errors != true) {
            DatabaseHash PBKDF2_class = new DatabaseHash();

            try {
                boolean matched = PBKDF2_class.validatePassword(user.getPassword(), "1000:" + salt + ":" + hash);

                if(matched){retCheckVar = true;}

            } catch (Exception NoSuchAlgorithmException) {
                System.err.println(NoSuchAlgorithmException.getMessage());
                System.exit(1);
            }
        }
        return retCheckVar;
    }

    /**
     * This is the method used to setup the GUI and it is what loads the frotnt end, this is called by the previous class,
     * in this case "Main".
     *
     * The DisplayGenericElements is inherited from the templateGui and it is called here, this sets up the frame size.
     *
     * Action listeners to the buttons are added here.
     *
     * The basket is also cleared with the "DELETE" statement, this is because basket sessions are only valid for when the user
     * is logged in.
     *
     */
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
                frame.dispose();
            }
        });
    }

    /**
     * This method is used to save the current user to the database in the "loggedSession" table. This is
     * useful throughout the program as it makes sure that variables are not carried to every class.
     *
     * @param userID
     * This is the ID of the user that has just logged in, the database index from the "users" table.
     */
    public void saveSessionUser(String userID){
        database.prepared_write_query("DELETE FROM loggedSession");
        database.prepared_write_query("INSERT INTO loggedSession (userID) VALUES (?)", userID);
    }

    /**
     * This is where the session user is called.
     *
     * If the credential check returns true then an if statment checks what type of user is requesting to log in.
     *
     * If the user is an admin they will be taken to the Admin Menu whereas if the user is a typical
     * user they will go to the User Menu.
     *
     * If the credentials are incorrect a message box is shown.
     */
    public void setUserAttributes(){
        user.setUsername(txtUsername.getText());
        String newVar = new String (txtPassword.getPassword());
        user.setPassword(newVar);
        boolean credCheck  = initCompare();
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
            JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
        }
    }
}
