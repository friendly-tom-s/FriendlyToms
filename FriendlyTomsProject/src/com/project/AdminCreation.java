package com.project;
import javax.swing.*;
import java.awt.*;

public class AdminCreation extends CreateAccount{

    public AdminCreation(){super("Admin Creation", "Back", "AdminMenu");}

    public void DisplayGui(){displayCreate(); rdoAdmin.setVisible(true);}
}
