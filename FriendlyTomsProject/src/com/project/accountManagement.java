/*

This page details account information with editing links to
account modification pages

$Id $
$Revision$
$Date$
$Author$
$Locker$

 */

package com.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

public class accountManagement {

    public static void main(String[] args){
    //public void renderPage() {
        // create JFrame and JTable
        JFrame frame = new JFrame();
        JTable table = new JTable();

        // create a table model and set a Column Identifiers to this model
        Object[] columns = {"user_id","username","hash","salt"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        // sql code to retrieve result set
        mariadb db_connector = new mariadb();
        ResultSet read_query = db_connector.prepared_read_query("SELECT * FROM users");

        try
        {
            while (read_query.next())
            {
                //menu_id name description calories
                int menu_id = read_query.getInt("user_id");
                String username = read_query.getString("username");
                String hash = read_query.getString("hash");
                String salt = read_query.getString("salt");

                // print the results
                System.out.format("%s, %s, %s, %s\n", menu_id, username, hash, salt);

                String user_info[] = {String.valueOf(menu_id), username, hash, salt};
                model.addRow(user_info);
            }
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! EXITING ");
            System.err.println(e.getMessage());
            System.exit(1);
        }

        // set the model to the table
        table.setModel(model);

        // Change A JTable Background Color, Font Size, Font Color, Row Height
        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.black);
        Font font = new Font("",1,22);
        table.setFont(font);
        table.setRowHeight(30);
        //disable table editing
        table.setDefaultEditor(Object.class, null);

        // create JTextFields
        JTextField textId = new JTextField();
        JTextField textFname = new JTextField();
        JTextField textLname = new JTextField();
        JTextField textAge = new JTextField();

        // create JButtons
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        JButton btnUpdate = new JButton("Update");

        textId.setBounds(20, 220, 100, 25);
        textFname.setBounds(20, 250, 100, 25);
        textLname.setBounds(20, 280, 100, 25);
        textAge.setBounds(20, 310, 100, 25);
        //disable editable fields
        textId.setEditable(false);
        textFname.setEditable(false);
        textLname.setEditable(false);
        textAge.setEditable(false);

        btnAdd.setBounds(150, 220, 100, 25);
        btnUpdate.setBounds(150, 265, 100, 25);
        btnDelete.setBounds(150, 310, 100, 25);

        // create JScrollPane
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 880, 200);

        frame.setLayout(null);

        frame.add(pane);

        // add JTextFields to the jframe
        frame.add(textId);
        frame.add(textFname);
        frame.add(textLname);
        frame.add(textAge);

        // add JButtons to the jframe
        frame.add(btnAdd);
        frame.add(btnDelete);
        frame.add(btnUpdate);

        // create an array of objects to set the row data
        Object[] row = new Object[4];

        // button add row
        btnAdd.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                /*row[0] = textId.getText();
                row[1] = textFname.getText();
                row[2] = textLname.getText();
                row[3] = textAge.getText();

                // add row to the model
                model.addRow(row);*/

                CreateAccount createAccount = new CreateAccount();
                //setPreviousWin("AdminMenu");
                createAccount.displayCreate();

            }
        });

        // button remove row
        btnDelete.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                // i = the index of the selected row
                int i = table.getSelectedRow();
                if(i >= 0){
                    //https://www.mkyong.com/swing/java-swing-how-to-make-a-confirmation-dialog/
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you wish to delete the selected user?", "Delete user conformation", dialogButton);
                    if(dialogResult == 0) {

                        //remove from database
                        mariadb db_connector = new mariadb();
                        ResultSet read_query = db_connector.prepared_read_query("DELETE FROM users WHERE user_id=?", model.getValueAt(i, 0).toString());

                        // remove a row from jtable
                        model.removeRow(i);

                        //clear text fields
                        textId.setText(null);
                        textFname.setText(null);
                        textLname.setText(null);
                        textAge.setText(null);
                    }

                }
                else{
                    System.out.println("Row Delete Error: no row is selected");
                }
            }
        });

        // get selected row data From table to textfields
        table.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e){

                // i = the index of the selected row
                int i = table.getSelectedRow();

                textId.setText(model.getValueAt(i, 0).toString());
                textFname.setText(model.getValueAt(i, 1).toString());
                textLname.setText(model.getValueAt(i, 2).toString());
                textAge.setText(model.getValueAt(i, 3).toString());
            }
        });

        // button update row
        btnUpdate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                // i = the index of the selected row
                int i = table.getSelectedRow();
                // get the SQL value of the first column
                String user_id = model.getValueAt(i, 0).toString();

                //only continue if the selected row index value is valid
                if(i >= 0)
                {
                    //TODO
                    //once the record has been updated, get the updated information via an SQL query and set the values below
                    /*model.setValueAt(textId.getText(), i, 0);
                    model.setValueAt(textFname.getText(), i, 1);
                    model.setValueAt(textLname.getText(), i, 2);
                    model.setValueAt(textAge.getText(), i, 3);*/

                    CreateAccount createAccount = new CreateAccount();
                    //setPreviousWin("AdminMenu");
                    createAccount.displayCreate();
                }
                else{
                    System.out.println("Update Error");
                }
            }
        });

        frame.setSize(900,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}