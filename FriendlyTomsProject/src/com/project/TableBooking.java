package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TableBooking extends TemplateGui{
    private JTextField txtName;
    private JButton btnBook;
    private JButton btnMainMenu;
    private UserMenu UserMenu;
    private JPanel panel2;
    private JTextField textDate;
    private JTextField textTime;
    private JTextField txtAmount;
    private User loggedUser;
    private Booking booking = new Booking();

    public TableBooking(User loggedUser){
        super("Table Booking", "Main Menu", "UserMenu");
        this.loggedUser = loggedUser;
    }

    public void displayTableBooking() {
        frame.add(panel2, BorderLayout.CENTER);
        txtName.setText(loggedUser.getUsername());
        DisplayGenericElements();
        btnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateDate(textDate.getText(), "dd/MM/yy").equals(true) &&
                        validateDate(textTime.getText(), "hh:mm").equals(true)){

                    booking.setDate(textDate.getText());
                    booking.setTime(textTime.getText());
                    booking.setName(txtName.getText());
                    booking.setAmount(txtAmount.getText());
                    saveToDatabase();
                }
            }
        });
    }

    public Boolean validateDate(String dateTime, String dateFormat){
        DateFormat format = new SimpleDateFormat(dateFormat);
        format.setLenient(false);
        String date = dateTime;
        try {
            format.parse(date);
            return true;
        } catch (ParseException e) {
            System.out.println(date + " is not valid according to " + ((SimpleDateFormat) format).toPattern() + " pattern.");
            return false;
        }
    }

    public void saveToDatabase(){
        mariadb db_connector = new mariadb();
        //boolean write_query = db_connector.write_query("INSERT INTO users (username,salt,hash,is_admin) VALUES ('" + user.getUsername() + "','" + salt + "','" + hash + "','0')");
        boolean write_query = db_connector.prepared_write_query("INSERT INTO bookings (date, time, amount, name) VALUES (?, ?, ?, ?)",
                booking.getDate(), booking.getTime(), booking.getAmount(), booking.getName());
        System.out.println("Was the insert successful: " + write_query);
    }

}
