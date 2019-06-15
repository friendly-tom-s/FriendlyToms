package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.regex.Pattern;

/**
 * This class is used to create all user accounts. This is used for the admins and the users.
 *
 * It is either accessed from the login screen or the admin menu.
 */

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

    /**
     * The constructor changes due to the fact that this clas is inherited for the admin creation meaning that the previous window
     * is not always the same. It needs to be assigned when the class/object is created.
     *
     * @param guiName
     * The name of the Gui, either Admin Creation or User Creation.
     *
     * @param buttonVar
     * This will either be Main Menu or Back.
     *
     * @param previousWin
     * This is either AdminMenu or LoginForm.
     */
    public CreateAccount(String guiName, String buttonVar, String previousWin) {

        /**
         * The TemplateGui constructor is given the variables from this constructor.
         */
        super(guiName, buttonVar, previousWin);

    }//create_account

    /**
     * If the admin radio button is selected then the user gets an admin status.
     *
     * Only if this class is accessed from the AdminMenu can the Admin Radio button be seen.
     */
    public void setUserData(){
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

    /**
     * This makes sure that the entered details follow the correct regex scheme meaning that the username and password are validated.
     *
     * It then makes sure that the user does not already exist.
     *
     * If the user does not exist it adds the new user to the database.
     */
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
            ResultSet read_query = db_connector.prepared_read_query("SELECT user_id FROM users WHERE username=?", user.getUsername());

            int user_id = 0;

            try {
                if (!read_query.next()) {

                } else {
                    user_id = read_query.getInt("user_id");
                    input_errors = true;

                }
            } catch (Exception a) {
                System.exit(1);
            }
        }

        if(input_errors != true) {
            DatabaseHash PBKDF2_class = new DatabaseHash();

            try {
                String generatedSecuredPasswordHash = PBKDF2_class.generateStorngPasswordHash(user.getPassword());
                String[] parts = generatedSecuredPasswordHash.split(":");
                int iterations = Integer.parseInt(parts[0]);
                String salt = parts[1];
                String hash = parts[2];
                //WRITE CODE
                mariadb db_connector = new mariadb();
                boolean write_query = db_connector.prepared_write_query("INSERT INTO users (username,salt,hash,is_admin) VALUES (?, ?, ?, ?)", user.getUsername(), salt, hash, user.getAdminStatus());

            } catch (Exception NoSuchAlgorithmException){
                System.exit(1);
            }//try
        }//input_errors

    }

    /**
     * The Gui is created
     */
    public void displayCreate(){
        DisplayGenericElements();
        //The rdoAdmin is only set True from the Admin Creation class.
        rdoAdmin.setVisible(false);
        frame.add(panel3, BorderLayout.CENTER);

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUserData();
                confirmUserData();
            }
        });
    }
}
