package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;


public class Checkout extends TemplateGui {
    private JLabel costLabel;
    private JComboBox cboTable;
    private JTextField txtCVV;
    private JTextField txtCardNumber;
    private JButton btnPay;
    private JPanel panel1;
    private JTextField txtExpiry;
    private JComboBox cboCard;
    private JButton btnSave;
    private static final int TIME_VISIBLE = 2000;
    private ResultSet nameOfItems;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    public Checkout() {
        super("Checkout", "Back", "Basket");


    }
    /**
     * A typical class that inherits the generic elements from the template gui.
     * It sets the form to be ready with the saved card details, table numbers and the total cost of the order.
     *
     */
    public void displayCheckout() {
        Basket basket = new Basket();
        basket.getListItems();
        costLabel.setText("£"+basket.getTotalCost());
        setCardDetails();

        int numbers_to_add_max = 99;
        for (int i = 1; i <= numbers_to_add_max; i++) {
            cboTable.addItem(i);

        }

        frame.add(panel1, BorderLayout.CENTER);
        DisplayGenericElements();

        btnPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (cboCard.getSelectedIndex() == 0)
                {
                    JOptionPane pane = new JOptionPane("Please select a card from the dropdown", JOptionPane.WARNING_MESSAGE);

                    JDialog dialog = pane.createDialog(null, "Error");
                    dialog.setModal(false);
                    dialog.setVisible(true);
                }
                else {

                    JOptionPane pane = new JOptionPane("Payment processing, please wait.........", JOptionPane.INFORMATION_MESSAGE);

                    JDialog dialog = pane.createDialog(null, "Payment Processing");
                    dialog.setModal(false);
                    dialog.setVisible(true);
                    dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

                    new Timer(TIME_VISIBLE, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dialog.setVisible(false);
                        }
                    }).start();

                    makeOrder();

                    int delay = 2500; //milliseconds
                    ActionListener taskPerformer = new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {

                            Object[] options = {"Yes, print receipt",
                                    "No, thanks"};
                            int n = JOptionPane.showOptionDialog(frame,
                                    "Order has been made, would you like to print the receipt? ",
                                    "Order Completed",
                                    JOptionPane.YES_NO_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    options,
                                    options[1]);
                            if (n == 0) {
                                printUserReceipt();
                            } else {
                                UserMenu userMenu = new UserMenu();
                                userMenu.displayUserMenu();
                                frame.dispose();
                            }
                            database.prepared_write_query("DELETE FROM basket WHERE userID=?", getUser());

                        }
                    };
                    Timer myTimer = new Timer(delay, taskPerformer);
                    myTimer.setRepeats(false);
                    myTimer.start();
                }
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            writeCardDetails();

            }
        });
    }
    /**
     * This function gets all the cards saved for the user from the database and places into the card ComboBox.
     * It decrypts the number and gets the last 5 digits to display for security reasons.
     */

    public void setCardDetails() {

            AES aes = new AES();
            String key = "FriendlyTomsDeserve98%";

            ResultSet cardQuery = database.prepared_read_query("SELECT * FROM carddetails WHERE userID=?", getUser());


            try {
                while (cardQuery.next()) {


                    String encryptedcardNo = cardQuery.getString("cardNo");
                    String decryptedCardNo = aes.decrypt(encryptedcardNo, key);

                    //BigInteger cardNo = new BigInteger(decryptedCardNo);
                    long cardNo = Long.parseLong(decryptedCardNo);

                    long last5Digits = cardNo % 100000;
                    //main_items.add(name);

                    cboCard.addItem(last5Digits);
                }
            } catch (Exception a) {
                System.err.println("Got an exception!");
                System.err.println(a.getMessage());
            }//try

    }

    /**
     * This function writes a new card to the database which the user has entered.
     * The details entered are each checked to ensure that they are formatted correctly as card details.
     * Before it is entered into the DB, AES encryption is used for security.
     */

    private void writeCardDetails(){

        boolean input_errors = verifyCardInput();
        if(input_errors != false){
            JOptionPane pane1 = new JOptionPane("Please enter valid card details", JOptionPane.WARNING_MESSAGE);
            JDialog dialog = pane1.createDialog(null, "Error");
            dialog.setVisible(true);
        }
        else {
            String newCardNo = txtCardNumber.getText();
            String newExpiry = txtExpiry.getText();

            AES aes = new AES();
            String key = "FriendlyTomsDeserve98%";
            String encryptedCardNo = aes.encrypt(newCardNo, key);
            String encryptedExpiry = aes.encrypt(newExpiry, key);


            try {


                database.prepared_write_query("INSERT INTO carddetails (userID, cardNo, expiry) VALUES (?,?,?)", getUser(), encryptedCardNo, encryptedExpiry);

            } catch (Exception a) {
                System.out.println("Something failed at 1");
            }//try
            JOptionPane pane2 = new JOptionPane("New card successfully added! Please select from the card drop down.", JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = pane2.createDialog(null, "Success");
            dialog.setModal(false);
            dialog.setVisible(true);
            cboCard.removeAllItems();
            txtCardNumber.setText("");
            txtExpiry.setText("");
            txtCVV.setText("");
            setCardDetails();
        }
    }

    /**
     * This function uses regex to make sure that the details have been entered correctly into the text boxes
     * Credit card number must be 16 digits
     * Expiry must be in the format MM/YY
     * CVV must be three digits long
     *
     * @return
     * A boolean is returned containing either True or False.
     *
     */
    public Boolean verifyCardInput(){

        //int cardNo = Integer.parseInt(txtCardNumber.getText());
        boolean input_errors = false;

        if(Pattern.matches("^[0-9]{16}$", txtCardNumber.getText())) {

        } else {
            input_errors = true;
        }

        if(Pattern.matches("^(0[1-9]|1[0-2])\\/[0-9]{2}$", txtExpiry.getText())) {

        } else {
            input_errors = true;
        }

        if(Pattern.matches("^[0-9]{3}$", txtCVV.getText())) {

        } else {
            input_errors = true;
        }

        return  input_errors;
    }
    /**
     * When the user confirms that everything in the basket is what they want to order and have made payment, the confirm order button gets everything from
     * the basket table and adds them to the order table.
     *
     * The stock level is also reduced by however many items have been ordered.
     *
     * The basket is not cleared because this is already done when the user logs in.
     */
    private void makeOrder(){
        ResultSet listItems = database.prepared_read_query("SELECT * FROM basket WHERE userID=?", getUser());
        Date date = new Date();

        Integer tableNo = (Integer) cboTable.getSelectedItem();

        try {
            while(listItems.next()) {
                String foodItem = listItems.getString("itemID");
                database.prepared_write_query("INSERT INTO orders (order_date, userID, foodItem, tableNo, completed) VALUES (?,?,?,?, ?)", date, getUser(), foodItem, tableNo, "No");
                database.prepared_write_query("UPDATE menu SET stock = stock - 1 WHERE menu_id = ?", foodItem);
            }
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try
    }

    /**
     * This is where the user receipt is saved if they wish to do so.
     *
     * It adds the date, all items that the user ordered to the receipt, the total cost and the last 5 digits of the card used.
     *
     * It adds it to the user's desktop.
     */
    private void printUserReceipt(){
        File file = new File(System.getProperty("user.home") + "/Desktop/FT_Receipt.txt");

        // if file doesnt exist, then create it
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Write the receipt.
        FileWriter fw = null;
        try {
            fw = new FileWriter(file.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(getFoodItems());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null,"Receipt Saved to your desktop!");
        UserMenu userMenu = new UserMenu();
        userMenu.displayUserMenu();
        frame.dispose();

    }

    /**
     * This gets all the database items that the user ordered. As it the database is normalised this means that more
     * statements are needed because it requires IDs across tables to work
     *
     * @return
     * The string that will be added the receipt.
     */

    private String getFoodItems(){
        Basket basket = new Basket();
        basket.getListItems();

        Date date = new Date();

        ResultSet listItems = database.prepared_read_query("SELECT itemID FROM basket WHERE userID=?", getUser());
        String foodItems= "";

        String cardNo = cboCard.getSelectedItem().toString();

        try {
            while(listItems.next()) {
                String columnValue = listItems.getString("itemID");
                nameOfItems = database.prepared_read_query("SELECT name FROM menu where menu_id=?", columnValue);
                try {
                    while(nameOfItems.next()) {
                        String columnNameValue = nameOfItems.getString("name");
                        foodItems = foodItems + System.lineSeparator() + columnNameValue;
                    }

                }
                catch (Exception a){System.out.println("Something failed at 2");}//try
            }
            foodItems = date + System.lineSeparator() + "-----------------------------" + foodItems + System.lineSeparator() + "This came to a total of £"+basket.getTotalCost() + System.lineSeparator() + "Payment taken from card ending in: "+cardNo;
        }
        catch (Exception a){System.out.println("Something failed at 1");}//try
        return foodItems;
    }
}


