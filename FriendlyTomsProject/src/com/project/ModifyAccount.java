package com.project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class ModifyAccount extends CreateAccount{
    private User user;

    public ModifyAccount(User user){
        super("Modify Account", "Back", "ManageAccounts");
        this.user = user;
    }

    public void DisplayModify(){
        displayCreate();
        btnCreate.setText("Update");
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkEntriesArePresent()) {
                    txtUserName.setText(user.getUsername());
                    setUserData();
                        confirmUserData("UPDATE users SET username = ?, salt = ?, hash=?, is_admin = ?, first_name = ?, last_name=?, email=? WHERE user_id = "+getUserID(),true);

                } else {
                    JOptionPane.showMessageDialog(null, "Please fill out all data fields");
                }
            }
        });

        txtUserName.setText("Username cannot be changed");
        txtFirstName.setText(user.getFirst_name());
        txtSurname.setText(user.getLast_name());
        txtEmail.setText(user.getEmail());

        if(user.getAdminStatus() == 1){
            rdoAdmin.setSelected(true);
        }
        else{rdoAdmin.setSelected(false);}
    }

    public String getUserID(){
            String userID = null;
            ResultSet session = database.prepared_read_query("Select * from users where username = ?", user.getUsername());
            try {
                while(session.next()) userID = session.getString("user_id");
            }
            catch (Exception a){System.out.println("Something failed at 1");}//try

            return userID;

    }


}