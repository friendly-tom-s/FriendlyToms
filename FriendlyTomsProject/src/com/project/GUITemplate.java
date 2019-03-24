package com.project;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GUITemplate {
    private JPanel panel1;
    private JButton backButtonButton;
    JFrame frame;

   //public GUITemplate(){);}

    public void DisplayGenericElements(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.add(panel1, BorderLayout.PAGE_END);
        frame.setVisible(true);

    }
}


//package com.project;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class GUITemplate {
//    //public JButton btnMainMenu;
//    //public JFrame frame;
//    //public JPanel panel1;
//    //public UserMenu UserMenu;
//
//
//    public GUITemplate() {
//
//    }
//public void GUIDefaults(){
//
//    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    //frame.setSize(400, 400);
//    //frame.add(panel1);
//    //frame.setVisible(true);
//
////    btnMainMenu.addActionListener(new ActionListener() {
////        @Override
////        public void actionPerformed(ActionEvent e) {
////            UserMenu = new UserMenu();
////            UserMenu.displayUserMenu();
////            frame.dispose();
////        }
////    });
//        }
//
//
//
//
//
//}
