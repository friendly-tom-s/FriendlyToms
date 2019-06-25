package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class AddMenuItem extends TemplateGui {
    private JPanel panel1;
    private JButton btnSubmit;
    private JTextField txtName;
    private JTextField txtURL;
    private JTextField txtDescription;
    private JTextField txtPrice;
    private JComboBox cboCategory;
    private JSpinner spnStock;
    private JSpinner spnCalories;
    private Product product = new Product();
    mariadb db_connector = new mariadb();

    private JPanel panelMain = new JPanel(new BorderLayout());


    public AddMenuItem() {
        super("Menu", "Back", "ManageStock");

    }

    public void DisplayAddMenuItem() {
        frame.add(panel1, BorderLayout.CENTER);
        DisplayGenericElements();

        String[] categories = new String[] {"Starter", "Main", "Dessert", "Drink"};
        cboCategory.setModel(new DefaultComboBoxModel(categories));

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setProductData();

                String[] productParts = {(product.getName()),(product.getDescription()),
                        (product.getCalories()),(product.getCategory()),
                        (product.getPrice()),(product.getNoinstock()),(product.getUrl())};

                /*String[] productParts = {(txtName.getText()),(txtDescription.getText()),
                        (spnCalories.getValue()).toString(),(cboCategory.getSelectedItem()).toString(),
                        (txtPrice.getText()),(spnStock.getValue().toString()), (txtURL.getText())};*/

                JOptionPane.showMessageDialog(null,"Added new product to database");

            writeToDB(productParts);
            }
        });
    }

    public void setProductData(){

        product.setName(txtName.getText());
        product.setDescription(txtDescription.getText());
        product.setCalories(spnCalories.getValue().toString());
        product.setCategory(cboCategory.getSelectedItem().toString());
        product.setPrice(txtPrice.getText());
        product.setNoinstock(spnStock.getValue().toString());
        product.setUrl(txtURL.getText());

        //user.setUsername(txtUserName.getText());
        //user.setConfirmPassword(new String (pswPassword.getPassword()));
        //user.setPassword(new String (pswPassword.getPassword()));
        //user.setEmail(txtEmail.getText());


    }

    public void writeToDB(String[] productParts){
      // for (String var:chosenItems) {
          // ResultSet read_query = database.prepared_read_query("SELECT menu_id FROM menu WHERE name =?", var);
           try {
               //while (read_query.next()) {
                   //String foodItem = read_query.getString("menu_id");
                   mariadb db_connector = new mariadb();

                   db_connector.prepared_write_query("INSERT INTO menu (name, description, calories, category, stock, price, webaddress) VALUES (?,?,?,?,?,?,?)", productParts[0], productParts[1], productParts[2], productParts[3], productParts[4], productParts[5], productParts[6]);
              // }
               }
            catch(Exception a)
               {


                  // break;
               }//try




    }



}


