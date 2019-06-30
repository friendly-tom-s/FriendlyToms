package com.project;

import javax.swing.*;
import java.awt.*;

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

    public void DisplayAddMenuItem() {
        frame.add(panel1, BorderLayout.CENTER);
        DisplayGenericElements();

        String[] categories = new String[]{"Starter", "Main", "Dessert", "Drink"};
        cboCategory.setModel(new DefaultComboBoxModel(categories));

        btnSubmit.addActionListener(e -> {
            setProductData();

            String[] productParts = {(product.getName()), (product.getDescription()),
                    (product.getCalories()), (product.getCategory()),
                    (product.getPrice()), (product.getNoinstock()), (product.getUrl())};

            JOptionPane.showMessageDialog(null, "Added new product to database");

            writeToDB(productParts);
        });
    }

    public void setProductData() {

        product.setName(txtName.getText());
        product.setDescription(txtDescription.getText());
        product.setCalories(spnCalories.getValue().toString());
        product.setCategory(cboCategory.getSelectedItem().toString());
        product.setPrice(txtPrice.getText());
        product.setNoinstock(spnStock.getValue().toString());
        product.setUrl(txtURL.getText());
    }

    public void writeToDB(String[] productParts) {
        try {
            database.prepared_write_query("INSERT INTO menu (name, description, calories," +
                    " category, stock, price, webaddress) VALUES (?,?,?,?,?,?,?)", productParts[0],
                    productParts[1], productParts[2], productParts[3], productParts[4], productParts[5], productParts[6]);

        } catch (Exception ignored) {
        }
    }
}


