package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private JComboBox comboBox1;
    private JButton checkAvailabilityButton;
    private String loggedUser;
    private Booking booking = new Booking();
    private mariadb db_connector = new mariadb();
    private int sittingOne = 0;
    private int sittingTwo = 0;
    private int sittingThree= 0;

    public TableBooking(){
        super("Table Booking", "Main Menu", "UserMenu");
        }

    public void displayTableBooking() {
        frame.add(panel2, BorderLayout.CENTER);
        txtName.setText(getUserName(getUser()));
        DisplayGenericElements();
        comboBox1.addItem("Sitting 1. 12:00 - 14:00");
        comboBox1.addItem("Sitting 2. 18:00 - 20:00");
        comboBox1.addItem("Sitting 3. 20:05 - 22:00");

        checkAvailabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = textDate.getText();
                checkDate(date);
                showAvailableSeats();
            }
        });
        btnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateBooking(textDate.getText(), "dd/MM/yy").equals(true) &&
                        checkSittingAvailability(getCboStringValue()).equals(true));
                {
                    booking.setDate(textDate.getText());
                    //time/sitting is set in getCboStringValue
                    booking.setName(txtName.getText());
                    booking.setAmount(txtAmount.getText());
                    saveToDatabase();
                }
            }
        });
    }

    public Boolean validateBooking(String dateTime, String dateFormat){
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
        boolean write_query = db_connector.prepared_write_query("INSERT INTO bookings (date, sitting, amount, name) VALUES (?, ?, ?, ?)",
                booking.getDate(), booking.getTime(), booking.getAmount(), booking.getName());
        System.out.println("Was the insert successful: " + write_query);
    }

    public void checkDate(String date){
        ResultSet allBooking=  db_connector.prepared_read_query("SELECT * FROM bookings WHERE date = ?", date);
        ResultSet sumSittingOne = getSitting(1, date);
        ResultSet sumSittingTwo = getSitting(2, date);
        ResultSet sumSittingThree = getSitting(3, date);

        try {
            while(sumSittingOne.next()) {sittingOne = sumSittingOne.getInt("amount");}
            while(sumSittingTwo.next()) {sittingTwo = sumSittingTwo.getInt("amount");}
            while(sumSittingThree.next()) {sittingThree = sumSittingThree.getInt("amount");}
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try

    }

    public ResultSet getSitting(int sitting, String date){
        ResultSet sittingReturn = db_connector.prepared_read_query("SELECT SUM(amount) AS amount FROM bookings WHERE date" +
                " = ? AND sitting = ?", date, sitting);
        return sittingReturn;
    }

    public void showAvailableSeats(){
        String infoMessage = "The amount of tables booked: \n Sitting 1: "+ sittingOne + "/50\n Sitting 2: " + sittingTwo +
                "/50\n Sitting 3: "+ sittingThree +"/50";

        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + "Bookings",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public Boolean checkSittingAvailability(int chosenSitting){
        if(chosenSitting >= 50){return true;}
        else{return false;}
    }

    public int getCboStringValue(){
        int cboSelected = 0;

        int cboIndex = comboBox1.getSelectedIndex();
        switch(cboIndex){
            case 0:
                cboSelected = sittingOne;
                booking.setTime("1");
            case  1:
                cboSelected = sittingTwo;
                booking.setTime("2");
            case 2:
                cboSelected = sittingThree;
                booking.setTime("3");
        }
        return cboSelected;

    }
}
