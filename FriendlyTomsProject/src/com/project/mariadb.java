package com.project;

import java.sql.*;

//import com.

public class mariadb {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://127.0.0.1/toms_pub"; //static final String DB_URL = "jdbc:mariadb://192.168.100.174/db";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "Passw0rd";

    public mariadb(){}

    public ResultSet read_query(String query)
    {
        try
        {
            // create our mysql database connection
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            st.close();

            //return sql result
            return rs;
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! EXITING ");
            System.err.println(e.getMessage());
            System.exit(1);
        }

        return null;
    }//end read_query

    public ResultSet prepared_read_query(String sql, Object... parameters)
    {
        try
        {
            // create our mysql database connection
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // create the java statement
            Statement st = conn.createStatement();

            // prepare the query
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }

            // execute the query, and get a java resultset
            ResultSet rs = preparedStatement.executeQuery();

            // close connection
            st.close();

            // return sql result
            return rs;
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! EXITING ");
            System.err.println(e.getMessage());
            //if broken program, I removed a system.exit from here - JB.
        }

        return null;
    }//end prepared_read_query

    public boolean write_query(String query) {
        Connection conn = null;
        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Executing query...");
            stmt = conn.createStatement();

            stmt.executeUpdate(query);
            System.out.println("Query has been performed");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return false;
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            return false;
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try

        return true;
    }//end write_query

    public boolean prepared_write_query(String sql, Object... parameters) {
        Connection conn = null;
        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Executing query...");
            stmt = conn.createStatement();

            //stmt.executeUpdate(query);

            // prepare the query
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }

            // execute the query, and get a java resultset
            preparedStatement.executeQuery();

            System.out.println("Query has been performed");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return false;
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            return false;
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try

        return true;
    }//end write_query

    public void viewAllData(){

        ResultSet read_query = read_query("SELECT * FROM menu");
        try
        {
            while (read_query.next())
            {
                //menu_id name description calories
                int menu_id = read_query.getInt("menu_id");
                String name = read_query.getString("name");
                String description = read_query.getString("description");
                int calories = read_query.getInt("calories");
                // print the results
                System.out.format("%s, %s, %s, %s\n", menu_id, name, description, calories);
            }
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! EXITING ");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}//end mariadb class