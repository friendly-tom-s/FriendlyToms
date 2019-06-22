package com.project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class ImageView extends TemplateGui {

    private String webAddress;
    private JPanel imagePanel;
    private JLabel lblImage;

    public ImageView(String imageWebAddress){
        super("View Image", "Quit", "FoodMenu");
        webAddress=imageWebAddress;
    }

    public void displayImageView(){
        DisplayGenericElements();
        setLabel();
        frame.setSize(800,700);
        frame.add(imagePanel);
    }

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
