package com.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is where the bookings are viewed from. This is for both admins and users.
 */
public class ViewBookings extends TemplateGui {
    private JTable tblBookings;
    private JPanel panel1;
    private JButton btnToday;
    private String previousWin;

    public ViewBookings(String previousAdminWin){
        super("View Bookings", "Back", previousAdminWin);
        this.previousWin = previousAdminWin;
    }

    /**
     * Typical GUI setup with a table.
     */
    public void DisplayViewBooking(){
        DisplayGenericElements();
        tblBookings.setModel(getBooking(getUserType("%")));
        Dimension dimension = new Dimension();
        dimension.setSize(480, 275);
        tblBookings.setPreferredScrollableViewportSize(dimension);
        JScrollPane pane = new JScrollPane(tblBookings);
        panelMain.add(btnToday);
        btnToday.addActionListener(e -> {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
            Date date = new Date();
            String preparedDate1 = dateFormat.format(date);
            tblBookings.setModel(getBooking(getUserType(preparedDate1)));
        });
        panelMain.add(pane);
        frame.add(panelMain, BorderLayout.CENTER);
        frame.setSize(515, 400);

    }

    /**
     * This gets the type of user that is using the class.
     * @return
     */
    public ResultSet getUserType(String date){

        ResultSet previousBookings = null;
        if(date =="%"){
            if(previousWin.equals("AdminMenu")){
                previousBookings = database.prepared_read_query("SELECT * FROM bookings");
            }
            if (previousWin.equals("UserMenu")){
                previousBookings = database.prepared_read_query("SELECT * FROM bookings WHERE name = ?"
                        , getUserName(getUser()));
            }
        }
        else {
            if (previousWin.equals("AdminMenu")) {
                previousBookings = database.prepared_read_query("SELECT * FROM bookings WHERE date = ?", date);
            }
            if (previousWin.equals("UserMenu")) {
                previousBookings = database.prepared_read_query("SELECT * FROM bookings WHERE name = ? AND date = ?"
                        , getUserName(getUser()), date);
            }
        }
        return previousBookings;
    }

    /**
     * The columns depending on which users.
     * @return
     * Then columns in the Jtable.
     */
    public Object[] getAdminUSerObject(){
        if(previousWin.equals("AdminMenu")){
            return new Object[]{"Date","Amount", "Sitting", "User"};
        }
        else{
            return new Object[]{"Date","Amount", "Sitting"};
        }
    }

    /**
     * This gets all the booking from the database and returns a DefaultTableModel that can be added to a JTable.
     * @param previousBookings
     * This gets the SQL statement dependning on the user type (Admin or User).
     * @return
     * This returns the table model that can be added to the JTable.
     */
    public DefaultTableModel getBooking(ResultSet previousBookings) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(getAdminUSerObject());
        try {
            while (previousBookings.next()) {
                String bookingDate = previousBookings.getString("date");
                String userName = previousBookings.getString("name");
                String amount = previousBookings.getString("amount");
                String sitting = previousBookings.getString("sitting");

                if (previousWin.equals("AdminMenu")) {
                    String[] user_info = {bookingDate, amount, sitting, userName};
                    model.addRow(user_info);
                } else {
                    String[] user_info = {bookingDate, amount, sitting};
                    model.addRow(user_info);
                }

            }
        } catch (Exception a) {
            System.out.println("Something failed at 1");
        }//try
        return model;
    }
}
