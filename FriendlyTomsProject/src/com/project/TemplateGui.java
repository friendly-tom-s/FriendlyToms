package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

/**
 * TemplateGui is used as the parent class for every GUI within the program.
 *
 * Every class will inherit from this class to use common variables such as "frame", "database" and "userName".
 */
public class TemplateGui {
    private JPanel panel1;
    private JButton backButtonButton;
    protected JFrame frame;
    private String previousWin;
    protected mariadb database = new mariadb();
    private String userid;
    private String userName;

    /**
     * When the class is inherited and the constructor is created each GUI needs to provide the relevant details
     * so that the Gui can change correctly.
     *
     * @param guiName
     * This is the name of the new Gui, this will appear at the top of the window.
     *
     * @param buttonVar
     * This is the text that the button will display, this is typically "Main Menu" or "Back".
     *
     * @param previousWin
     * This is thw window that the back button will go to when pressed. This is in the constructor because is changes
     * with each Gui.
     */
    public TemplateGui(String guiName, String buttonVar, String previousWin){
        frame = new JFrame(guiName);
        backButtonButton.setText(buttonVar);
        this.previousWin = previousWin;
    }

    /**
     * This method is used by every to display and create the Gui. This creates the size and adds to panel with the button.
     *
     * Before this was implemented, all the code was identical between the Guis.
     */
    public void DisplayGenericElements(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.add(panel1,BorderLayout.PAGE_END);
        frame.setVisible(true);
        backButtonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                previousWinLogic();
            }
        });
    }

    /**
     * This is called by the action listener, it will determine which window/Gui needs to be opened depending on what the
     * previous window variable is.
     */
    public void previousWinLogic(){
        if (previousWin.equals("AdminMenu")){
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.displayAdminMenu();
        }
        if (previousWin.equals("LoginForm")){
            LoginForm loginForm = new LoginForm();
            loginForm.displayLogin();
        }
        if (previousWin.equals("UserMenu")){
            UserMenu userMenu = new UserMenu();
            userMenu.displayUserMenu();
        }
        if (previousWin.equals("FoodOrder")){
            FoodOrder foodOrder = new FoodOrder();
            foodOrder.displayFoodOrder();
        }
    }

    /**
     * This method is used to get the UserID of the logged in user, this is a very useful method throughout the program.
     *
     * @return
     * The UserID is returned.
     */
    public String getUser(){
        ResultSet session = database.prepared_read_query("Select * from loggedsession");
        try {
            while(session.next()) {
                userid = session.getString("userid");
            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try

        return userid;
    }

    /**
     * This method gets the username (not the ID) of a user depending on the UserID. Again, this is very useful.
     *
     * @param queryUserId
     * This is UserID of the user that needs the username.
     *
     * @return
     * The username is returned.
     */
    public String getUserName(String queryUserId){
        ResultSet session = database.prepared_read_query("Select * from users where user_id = ?", queryUserId);
        try {
            while(session.next()) {
                userName = session.getString("username");
            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try

        return userName;
    }
}
