package com.project;

public class AdminCreation extends CreateAccount{

    public AdminCreation(){super("Admin Creation", "Back", "AdminMenu");}

    public void DisplayGui(){displayCreate(); rdoAdmin.setVisible(true);}


}
