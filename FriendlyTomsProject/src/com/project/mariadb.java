package com.project;

import java.sql.*;

public class mariadb {
    // JDBC driver name and database URL
    private String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private String DB_URL = "jdbc:mariadb://127.0.0.1/toms_pub"; //static final String DB_URL = "jdbc:mariadb://192.168.100.174/db";

    // Database credentials
    private String USER = "root";
    private String PASS = "Passw0rd";

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
            PreparedStatement preparedStatement = getPS(conn, sql, parameters);

            // execute the query, and get a java resultset
            ResultSet rs = preparedStatement.executeQuery();

            // close connection
            st.close();
            conn.close();

            // return sql result
            return rs;
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! EXITING ");
            System.err.println(e.getMessage());
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
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            stmt = conn.createStatement();

            stmt.executeUpdate(query);
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
            closeConnection(stmt, conn);
        }//end try

        return true;
    }//end write_query

    public PreparedStatement getPS(Connection conn, String sql, Object... parameters){

        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }
        return preparedStatement;
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return null;}

    }

    public boolean prepared_write_query(String sql, Object... parameters) {
        Connection conn = null;
        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            stmt = conn.createStatement();

            //stmt.executeUpdate(query);

            // prepare the query
            PreparedStatement preparedStatement = getPS(conn, sql, parameters);

            // execute the query, and get a java resultset
            preparedStatement.executeQuery();

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
            closeConnection(stmt, conn);
        }//end try

        return true;
    }//end write_query


    public void closeConnection(Statement stmt, Connection conn){
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
        }

    }
}//end mariadb class