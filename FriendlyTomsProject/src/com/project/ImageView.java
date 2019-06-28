package com.project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * This class is used to display the image from the menu.
 */
public class ImageView extends TemplateGui {

    private String webAddress;
    private JPanel imagePanel;
    private JLabel lblImage;

    /**
     * The web address is given to the class.
     * @param imageWebAddress
     * The web address.
     */
    public ImageView(String imageWebAddress){
        super("View Image", "Quit", "FoodMenu");
        webAddress=imageWebAddress;
    }

    /**
     * Generic display GUI
     */
    public void displayImageView(){
        DisplayGenericElements();
        setLabel();
        frame.setSize(800,700);
        frame.add(imagePanel);
    }

    /**
     * The label is set with the image from the address.
     */
    public void setLabel(){
        Image image = null;
        try {
            URL url = new URL(webAddress);
            image = ImageIO.read(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        lblImage.setIcon(new ImageIcon(image));
    }
}
