package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.regex.Pattern;

/**
 * This class is used to create all user accounts. This is used for the admins and the users.
 * <p>
 * It is either accessed from the login screen or the admin menu.
 */

public class CreateAccount extends TemplateGui {
    protected JButton btnCreate;
    protected JTextField txtUserName;
    protected JTextField txtEmail;
    protected JPasswordField pswPassword;
    protected JPasswordField pswConfirm;
    protected JPanel panel3;
    protected JRadioButton rdoAdmin;
    protected JTextField txtFirstName;
    protected JTextField txtSurname;
    private User user = new User();
    private String previousWin;

    /**
     * The constructor changes due to the fact that this clas is inherited for the admin creation meaning that the previous window
     * is not always the same. It needs to be assigned when the class/object is created.
     *
     * @param guiName     The name of the Gui, either Admin Creation or User Creation.
     * @param buttonVar   This will either be Main Menu or Back.
     * @param previousWin This is either AdminMenu or LoginForm.
     */
    public CreateAccount(String guiName, String buttonVar, String previousWin) {

        /*
          The TemplateGui constructor is given the variables from this constructor.
         */
        super(guiName, buttonVar, previousWin);
        this.previousWin =previousWin;

    }//create_account

    /**
     * If the admin radio button is selected then the user gets an admin status.
     * <p>
     * Only if this class is accessed from the AdminMenu can the Admin Radio button be seen.
     */
    public void setUserData() {
        user.setUsername(txtUserName.getText());
        user.setConfirmPassword(new String(pswPassword.getPassword()));
        user.setPassword(new String(pswPassword.getPassword()));
        user.setEmail(txtEmail.getText());
        user.setFirst_name(txtFirstName.getText());
        user.setLast_name(txtSurname.getText());

        if (rdoAdmin.isSelected()) {
            user.setAdminStatus(1);
        } else {
            user.setAdminStatus(0);
        }

        //user.setPhone_number(phone_number_textfield.getText());
    }

    /**
     * This makes sure that the entered details follow the correct regex scheme meaning that the username and password are validated.
     * <p>
     * It then makes sure that the user does not already exist.
     * <p>
     * If the user does not exist it adds the new user to the database.
     */
    public void confirmUserData(String SQL, boolean update) {
        Boolean checkUser = false;
        Boolean checkPw = false;

        boolean input_errors = false;
        if (Pattern.matches("[a-zA-Z0-9]{3,20}", user.getPassword())) {

        } else {

            input_errors = true;
        }
        //validate password
        if (Pattern.matches("[a-zA-Z0-9]{3,20}", user.getPassword())) {

        } else {
            input_errors = true;
        }
        //validate passwords match
        if (Pattern.matches(user.getPassword(), user.getConfirm_password())) {
        } else {
            input_errors = true;
        }

        //to check if user being created already exists
        if(!update){
        if (!input_errors) {
            //READ CODE
            ResultSet read_query = database.prepared_read_query("SELECT " +
                    "user_id FROM users WHERE username=?", user.getUsername());

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
        }}
        /*
        Creates the account if there are no errors.
         */
        if (!input_errors) {

            JOptionPane.showMessageDialog(null, "Account Created.");
            String[] parts = getEncrypt();
            String salt = parts[1];
            String hash = parts[2];
            //WRITE CODE
            boolean write_query = database.prepared_write_query(SQL, user.getUsername(), salt, hash, user.getAdminStatus(),
                    user.getFirst_name(), user.getLast_name(), user.getEmail());
            if(previousWin == "LoginForm"){
            LoginForm loginForm = new LoginForm();
            loginForm.displayLogin();}
            else{
                ManageAccounts manageAccounts = new ManageAccounts();
                manageAccounts.DisplayManageAccounts();
            }
            frame.dispose();

        }//input_errors
    }

    public String[] getEncrypt() {
        DatabaseHash PBKDF2_class = new DatabaseHash();
        String[] parts = new String[0];
        try {
            String generatedSecuredPasswordHash = PBKDF2_class.generateStrongPasswordHash(user.getPassword());
            parts = generatedSecuredPasswordHash.split(":");
        } catch (Exception NoSuchAlgorithmException) {
        }

        return parts;
    }

    /**
     * The Gui is created
     */
    public void displayCreate() {
        DisplayGenericElements();
        //The rdoAdmin is only set True from the Admin Creation class.
        rdoAdmin.setVisible(false);
        frame.add(panel3, BorderLayout.CENTER);

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkEntriesArePresent()) {
                    setUserData();
                    confirmUserData("INSERT INTO users (username,salt,hash,is_admin, first_name, last_name, email) VALUES" +
                                    " (?, ?, ?, ?, ?, ?, ?)", false);

                } else {
                    JOptionPane.showMessageDialog(null, "Please fill out all data fields");
                }
            }
        });
    }

    /**
     * This makes sure that all fields are completed.
     *
     * @return Pass or Fail.
     */

    public boolean checkEntriesArePresent() {
        Boolean check = true;
        if (txtUserName.getText().equals("")) {
            check = false;
        }
        if (txtEmail.getText().equals("")) {
            check = false;
        }
        if (txtFirstName.getText().equals("")) {
            check = false;
        }
        if (txtSurname.getText().equals("")) {
            check = false;
        }
        return check;
    }
}
