package com.project;

import javax.swing.*;
import java.awt.*;

/**
 * This class is used to create new items to be sold in the pub.
 */
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

    public AddMenuItem() {
        super("Menu", "Back", "ManageStock");
    }

    /**
     * Typical display GUI.
     */
    public void DisplayAddMenuItem() {
        frame.add(panel1, BorderLayout.CENTER);
        DisplayGenericElements();

        String[] categories = new String[]{"Starter", "Main", "Dessert", "Drink"};
        cboCategory.setModel(new DefaultComboBoxModel(categories));

        btnSubmit.addActionListener(e -> {
            if(checkItemsArePresent()) {
                setProductData();

                String[] productParts = {(product.getName()), (product.getDescription()),
                        (product.getCalories()), (product.getCategory()),
                        (product.getPrice()), (product.getNoinstock()), (product.getUrl())};

                JOptionPane.showMessageDialog(null, "Added new product to database");

                writeToDB(productParts);
            }
            else{JOptionPane.showMessageDialog(null, "Please enter all details");}
        });
    }

    /**
     * The new product object is set
     */
    public void setProductData() {

        product.setName(txtName.getText());
        product.setDescription(txtDescription.getText());
        product.setCalories(spnCalories.getValue().toString());
        product.setCategory(cboCategory.getSelectedItem().toString());
        product.setPrice(txtPrice.getText());
        product.setNoinstock(spnStock.getValue().toString());
        product.setUrl(txtURL.getText());
    }

    /**
     * Database is written after validation has occurred.
     * @param productParts
     */
    public void writeToDB(String[] productParts) {
        try {
            database.prepared_write_query("INSERT INTO menu (name, description, calories," +
                    " category, stock, price, webaddress) VALUES (?,?,?,?,?,?,?)", productParts[0],
                    productParts[1], productParts[2], productParts[3], productParts[4], productParts[5], productParts[6]);

        } catch (Exception ignored) {
        }
    }

    /**
     * Checks all field have been entered.
     * @return
     * Pass or Fail
     */
    private boolean checkItemsArePresent(){
        boolean check = true;
        if(txtName.getText().equals("")){
            check = false;
        }
        if(txtDescription.getText().equals("")){
            check = false;
        }
        if(spnStock.getValue().equals("0")){
            check = false;
        }
        if(txtPrice.getText().equals("")){
            check = false;
        }
        if(spnCalories.getValue().equals("0")){
            check = false;
        }
        if(txtURL.getText().equals("")){
            check = false;
        }
        return check;
    }
}


