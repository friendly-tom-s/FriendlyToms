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

/**
 * This class is used to book tables. It can only be accessed by the users.
 */
public class TableBooking extends TemplateGui{
    private JTextField txtName;
    private JButton btnBook;
    private JPanel panel2;
    private JTextField textDate;
    private JTextField txtAmount;
    private JComboBox comboBox1;
    private JButton checkAvailabilityButton;
    private Booking booking = new Booking();
    private int sittingOne = 0;
    private int sittingTwo = 0;
    private int sittingThree= 0;

    public TableBooking(){
        super("Table Booking", "Main Menu", "UserMenu");
    }

    /**
     * This is a typical display Gui method although the sittings are hardcoded into the dropdown because they never change.
     *
     * The action listeners are set here as usual.
     */
    public void displayTableBooking() {
        frame.add(panel2, BorderLayout.CENTER);
        txtName.setText(getUserName(getUser()));
        DisplayGenericElements();
        frame.setSize(350, 450);
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
                    booking.setName(txtName.getText());
                    booking.setAmount(txtAmount.getText());
                    saveToDatabase();
                }
            }
        });
    }

    /**
     * This is used to make sure that the date is entered into the correct format according to the dateFormat that is passed in.
     *
     * @param dateTime
     * This is the string that is being checked to make sure that it is in the correct format.
     *
     * @param dateFormat
     * This is the format that the user entry should comply to.
     *
     * @return
     *A boolen is returned that states if the date is correct or not.
     */
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

    /**
     * This adds to booking to the database once all checks have been completed.
     */
    public void saveToDatabase(){
        boolean write_query = database.prepared_write_query("INSERT INTO bookings " +
                        "(date, sitting, amount, name) VALUES (?, ?, ?, ?)",
                booking.getDate(), booking.getTime(), booking.getAmount(), booking.getName());
    }

    /**
     * This method checks that a selected date has available sittings. It does this by checking each sitting in the database
     * on the date that the user has selected.
     *
     * @param date
     * The date that the user has selected.
     *
     */
    public void checkDate(String date){
        ResultSet sumSittingOne = getSitting(1, date);
        ResultSet sumSittingTwo = getSitting(2, date);
        ResultSet sumSittingThree = getSitting(3, date);

        try {
            while(sumSittingOne.next()) {sittingOne = sumSittingOne.getInt("amount");}
            while(sumSittingTwo.next()) {sittingTwo = sumSittingTwo.getInt("amount");}
            while(sumSittingThree.next()) {sittingThree = sumSittingThree.getInt("amount");}
        }
        catch (Exception a){System.out.println("Something failed at 1");}
    }

    /**
     * This will get the amount of all of the bookings in the database for a given date on a given sitting. It does this
     * by using a SUM SQL statement to add up all of the table booking amounts.
     *
     * @param sitting
     * The sitting that is being checked.
     *
     * @param date
     * The date that is being checked.
     *
     * @return
     * This will return a ResultSet only because this is how the database class has been configured, it will only contain one
     * value and that is an integer.
     */
    public ResultSet getSitting(int sitting, String date){
        ResultSet sittingReturn = database.prepared_read_query("SELECT SUM(amount) AS amount FROM bookings WHERE date" +
                " = ? AND sitting = ?", date, sitting);
        return sittingReturn;
    }

    /**
     * This will display a messageBox with the amount of available seats in each sitting on a selected date.
     */
    public void showAvailableSeats(){
        String infoMessage = "The amount of tables booked: \n Sitting 1: "+ sittingOne + "/50\n Sitting 2: " + sittingTwo +
                "/50\n Sitting 3: "+ sittingThree +"/50";

        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + "Bookings",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This will make sure that a chosen sitting has not exceeded the amount booked.
     *
     * @param chosenSitting
     * Either sitting 1, 2 or 3.
     *
     * @return
     * Either True or False.
     */
    public Boolean checkSittingAvailability(int chosenSitting){
        if(chosenSitting >= 50){return true;}
        else{return false;}
    }

    /**
     * This will set he booking value depending on what cbo value is selected.
     *
     * Index 0 on the cbo is Sitting 1 etc.
     *
     * @return
     * It will return the integer value of the sitting: 1, 2 or 3.
     */
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
