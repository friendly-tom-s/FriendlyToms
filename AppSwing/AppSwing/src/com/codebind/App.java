package com.codebind;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    private JButton button_msg;
    private JPanel panelMain;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    //youtube tutorial for this App
    //https://www.youtube.com/watch?v=5vSyylPPEko



    public App() {
        button_msg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("dank: ");

                testClass testClass2 = new testClass(1,2,3);

                String returned_concat = testClass2.testfunc("jeff","ben");

                System.out.println(returned_concat);

                //textField1.setText("test");
                set_frame("howdey");

            }
        });
    }

    public void initialiseframe() {
        JFrame frame = new JFrame("App test");
        //frame.setSize(500,400);
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //resizeable
        //https://coderanch.com/t/628321/java/disable-resizing-jframe
        frame.setResizable(false);

        frame.pack();
        frame.setVisible(true);

        //font size
        //https://stackoverflow.com/questions/2715118/how-to-change-the-size-of-the-font-of-a-jlabel-to-take-the-maximum-size


        //frame.   .setFont(new Font("Serif", Font.PLAIN, 14));
    }

    public void set_frame(String pass) {


        textField1.setText("passthrough:" + pass);
    }

    /*public static void main(String[] args) {
        JFrame frame = new JFrame("App test");
        //frame.setSize(500,400);
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //resizeable
        //https://coderanch.com/t/628321/java/disable-resizing-jframe
        frame.setResizable(false);

        frame.pack();
        frame.setVisible(true);



        //frame.   .setFont(new Font("Serif", Font.PLAIN, 14));

    }*/
}
