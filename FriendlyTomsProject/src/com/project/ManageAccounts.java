package com.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.ResultSet;


/**
 * This class is used to manage all accounts, this is both admin and user accounts. It can only be accessed from the Admin Menu.
 */
public class ManageAccounts extends TemplateGui {
    private JButton btnDelete;
    private JButton btnCreate;
    private JPanel panel2;
    private JTable table;
    private JTextField txtUsername;
    private JButton btnSearch;
    private AdminCreation adminCreation;
    private JScrollPane pane;
    private JPanel frame2 = new JPanel();
    DefaultTableModel model = new DefaultTableModel();

    public ManageAccounts(){super("Manage Accounts", "Main Menu", "AdminMenu");

    }

    /**
     * This is a slightly different way of creating the Gui than with other classes due to the use of a JPanel and a JScrollPane.
     * Because they handle differently they could not easily be added to a pre-existing panel and thus a new one had to be created,
     * this new pane is then added to the th existing frame from TemplateGui.
     *
     * The reason for this extra work as opposed to using a Jlist is because a scrollbar can be implemented well.
     */
    public void DisplayManageAccounts(){
        DisplayGenericElements();
        frame.requestFocusInWindow();
        setJtable("SELECT * FROM users");
        pane = new JScrollPane(table);
        frame2.add(txtUsername);
        frame2.add(btnSearch);
        frame2.add(pane);
        frame2.add(btnCreate);
        frame2.add(btnDelete);
        frame.add(frame2, BorderLayout.CENTER);
        frame.setSize(500, 570);

        btnCreate.addActionListener(e -> {
            adminCreation = new AdminCreation();
            adminCreation.DisplayGui();
            frame.dispose();
        });

        btnDelete.addActionListener(e -> deleteSelectedItem());
        txtUsername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtUsername.setText("");
            }
        });

        btnSearch.addActionListener(e -> {
//                DefaultTableModel modelWipe = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            setJtable("SELECT * FROM users WHERE last_name = ?", txtUsername.getText());
        });
    }

    /**
     * This is used to set the columns and add the data to the JTable, the data is taken from the users table in
     * the database.
     *
     * The data is set to the JTable using a model.
     */
    public void setJtable(String SQL, Object... parameters){

        Object[] columns = {"UID","username", "First Name", "Last Name"};
        model.setColumnIdentifiers(columns);
        ResultSet read_query;
        try{
            read_query = database.prepared_read_query(SQL, parameters[0]);
        }
        catch (ArrayIndexOutOfBoundsException a){
            read_query = database.prepared_read_query(SQL);
        }

        try
        {
            while (read_query.next())
            {
                int menu_id = read_query.getInt("user_id");
                String username = read_query.getString("username");
                String first_name = read_query.getString("first_name");
                String last_name = read_query.getString("last_name");
                String[] user_info = {String.valueOf(menu_id), username, first_name, last_name};
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

    /**
     * This function will remove the item from the database from the selected index if the button is clicked in the database.
     */
    public void deleteSelectedItem(){
        table.removeAll();
        int i = table.getSelectedRow();
        if(i >= 0){
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "Are you sure you wish to delete the selected user?", "Delete user conformation", dialogButton);
            if(dialogResult == 0) {
                //remove from database
                ResultSet read_query = database.prepared_read_query("DELETE FROM users WHERE user_id=?", model.getValueAt(i, 0).toString());
                // remove a row from jtable
                model.removeRow(i);
                setJtable("SELECT * FROM users");
            }
        }
        else{
            System.out.println("Row Delete Error: no row is selected");
        }
    }
}
