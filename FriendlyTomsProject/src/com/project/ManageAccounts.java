package com.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class ManageAccounts extends TemplateGui {
    private JButton btnView;
    private JButton btnDelete;
    private JButton btnCreate;
    private JPanel panel2;
    private JTable table;
    private AdminCreation adminCreation;
    private JScrollPane pane;
    private JPanel frame2 = new JPanel();
    private mariadb db_connector = new mariadb();
    DefaultTableModel model = new DefaultTableModel();

    public ManageAccounts(){super("Manage Accounts", "Main Menu", "AdminMenu");}

    public void DisplayManageAccounts(){

        DisplayGenericElements();
        setJtable();
        //table.setSize(200, 200);
        pane = new JScrollPane(table);
        frame2.add(pane);
        frame2.add(btnCreate);
        frame2.add(btnDelete);
        frame.add(frame2, BorderLayout.CENTER);
        frame.setSize(500, 550);

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminCreation = new AdminCreation();
                adminCreation.DisplayGui();
                frame.dispose();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedItem();
            }
        });
    }

    public void setJtable(){
        Object[] columns = {"UID","username", "First Name", "Last Name"};
        model.setColumnIdentifiers(columns);
        ResultSet read_query = db_connector.prepared_read_query("SELECT * FROM users");

        try
        {
            while (read_query.next())
            {
                int menu_id = read_query.getInt("user_id");
                String username = read_query.getString("username");
                String first_name = read_query.getString("first_name");
                String last_name = read_query.getString("last_name");
                String user_info[] = {String.valueOf(menu_id), username, first_name, last_name};
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

    }

    public void deleteSelectedItem(){
        table.removeAll();
        int i = table.getSelectedRow();
        if(i >= 0){
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "Are you sure you wish to delete the selected user?", "Delete user conformation", dialogButton);
            if(dialogResult == 0) {
                //remove from database
                ResultSet read_query = db_connector.prepared_read_query("DELETE FROM users WHERE user_id=?", model.getValueAt(i, 0).toString());
                // remove a row from jtable
                model.removeRow(i);
                setJtable();
            }
        }
        else{
            System.out.println("Row Delete Error: no row is selected");
        }
    }
}
