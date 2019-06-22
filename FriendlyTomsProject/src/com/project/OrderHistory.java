package com.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

/**
 * This class is used to get all the orders made by a user or all the orders made in total.
 *
 * This class is accessed by users and admins but the admins get to see a different screen.
 */
public class OrderHistory extends TemplateGui {
    private JList lstOrderDetails;
    private JPanel panel2;
    private JPanel panelMain = new JPanel(new BorderLayout());
    private JTable table;
    private JComboBox cboMonth;
    private JComboBox cboYear;
    private JButton btnUpdate;
    private ResultSet previousOrders;
    private ResultSet foodNames;
    private String previousWIn;
    private JPanel frame2 = new JPanel();
    private JScrollPane pane;
    private String searchItem = "%";
    private String searchYear = "2019";


    /**
     * Because this class is inherited the constructor is slightly different; when the object is created it can be either
     * by a user and from a different location so the details of the Gui need to be different.
     *
     * @param guiName
     * This will either be "Admin View Orders" or "User View Orders".
     *
     * @param buttonVar
     * This will be "Main Menu" in all cases.
     *
     * @param previousWIn
     * This will either be UserMenu or AdminMenu.
     */
    public OrderHistory(String guiName, String buttonVar, String previousWIn ){
        super(guiName, buttonVar, previousWIn);
        this.previousWIn= previousWIn;

    }

    /**
     * This is the typical Gui creation method.
     */
    public void DisplayOrderHistory(){
        DisplayGenericElements();
        //ResultSet userType = getUserType();
        table.setModel(getOrder(getUserType()));
        Dimension dimension = new Dimension();
        dimension.setSize(500, 275);
        table.setPreferredScrollableViewportSize(dimension);
        pane = new JScrollPane(table);
        String[] itemsMonth = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};

        int yearNum = 2020;
        for (int i = 0; i < 10; i++) {
            yearNum = yearNum -1;
            cboYear.addItem(yearNum);
        }
        cboMonth.setModel(new DefaultComboBoxModel(itemsMonth));
        panelMain.add(pane, BorderLayout.SOUTH);
        panelMain.add(cboMonth, BorderLayout.WEST);
        panelMain.add(cboYear, BorderLayout.CENTER);
        panelMain.add(btnUpdate, BorderLayout.EAST);
        frame.add(panelMain, BorderLayout.CENTER);
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monthLogic((cboMonth.getSelectedItem()).toString());
                searchYear = cboYear.getSelectedItem().toString();
                table.setModel(getOrder(getUserType()));

            }
        });

    }

    /**
     * Depending on where the user has come from, either admin or user screens, they will receive different information,
     * this is where that is decided.
     *
     * @return
     * If the user is an Admin they will get all orders.
     *
     * If the user is a standard user they will only get orders with their userID. This variable is assigned from the
     * TemplateGui and the getUser method.
     */
    public ResultSet getUserType(){
        String preparedDate = searchYear + "-"+ searchItem + "%";
        if(previousWIn.equals("AdminMenu")){
            previousOrders = database.prepared_read_query("SELECT * FROM orders WHERE order_date LIKE ?", preparedDate);
        }
        if (previousWIn.equals("UserMenu")){
            previousOrders = database.prepared_read_query("SELECT * FROM orders WHERE userid = ? AND order_date LIKE ?"
                    , getUser(), preparedDate);
        }
        return previousOrders;
    }

    /**
     * This sets the titles of the columns that are displayed in the JTable. It is determined depending on where the
     * user has come from.
     *
     * @return
     * The Admin will get 3 columns whereas the user will only get 2.
     *
     */
    public Object[] getObject(){
        if(previousWIn.equals("AdminMenu")){
            Object[] columns = {"FoodName","Date", "User"};
            return columns;
        }
        else{
            Object[] columns = {"FoodName","Date"};
            return columns;
        }
    }

    /**
     * This method is used to get the TableModel that will be set to the JTable, this is where all of the data is shown to
     * the user. It collects all the data from the users table and gets the corresponding food that they ordered. The admin
     * is then shown the food, date and username whereas the user is only shown the food and the date.
     *
     * @param previousOrders
     * This determines how many columns are in the JTable object. Admin is 3 whereas a user is 2.
     *
     * @return
     * This will be a TableModel that can be set to the JTable to be displayed.
     */
    public DefaultTableModel getOrder(ResultSet previousOrders){
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(getObject());
        try {
            while(previousOrders.next()) {
                String foodID = previousOrders.getString("foodItem");
                String date = previousOrders.getString("order_date");
                String user = previousOrders.getString("userID");
                foodNames = database.prepared_read_query("SELECT name FROM menu where menu_id=?", foodID);//Take this out the while loop and make it an array
                String username = getUserName(user);
                try {
                    while(foodNames.next()) {
                        String columnNameValue = foodNames.getString("name");

                        if(previousWIn.equals("AdminMenu")){
                            String user_info[] = {columnNameValue, date, username};
                            model.addRow(user_info);
                        }
                        else{
                            String user_info[] = {columnNameValue, date};
                            model.addRow(user_info);
                        }
                    }
                }
                catch (Exception a){System.out.println("Something failed at 2");}//try
            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try
        return model;
    }

    public void monthLogic(String chosenMonth){
        switch (chosenMonth){
            case "All":
                searchItem = "%";
                break;
            case "January":
                searchItem = "01";
                break;
            case "February":
                searchItem = "02";
                break;
            case "March":
                searchItem = "03";
                break;
            case "April":
                searchItem = "04";
                break;
            case "May":
                searchItem = "05";
                break;
            case "June":
                searchItem = "06";
                break;
            case "July":
                searchItem = "07";
                break;
            case "August":
                searchItem = "08";
                break;
            case "September":
                searchItem = "09";
                break;
            case "October":
                searchItem = "10";
                break;
            case "November":
                searchItem = "11";
                break;
            case "December":
                searchItem = "12";
                break;
        }
    }
}
