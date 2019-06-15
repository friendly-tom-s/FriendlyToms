package com.project;

/**
 * This class is how Admins are created. It inherits from the typical user creation class but adds to ability to make them
 * an admin by making an invisible rdo button visible that determines if the account is an admin.
 */
public class AdminCreation extends CreateAccount{

    public AdminCreation(){super("Admin Creation", "Back", "AdminMenu");}

    public void DisplayGui(){displayCreate(); rdoAdmin.setVisible(true);}


}
