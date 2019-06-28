package com.project;

import java.sql.*;


/**
 * This is the class that is used throughout the program. It is used to connect to the MySQL Database.
 * There are 2 options that used the most:
 * 1. Prepared_read_query - This is used to read from the database with a query that has parameters as opposed to
 *                          a simple read query.
 * 2. Prepared_write_query - This is used to write to the database with a query that has parameters as opposed to a
 *                           simple write query.
 *
 * Througout the code it can be seen that we have chosen to use "?" and parameters instead of putting the variables
 * into the SQL string. We took this approach to prevent SQL injection because the DB will be looking for "?" and
 * not plain text variables.
 *
 */
public class mariadb {
    // JDBC driver name and database URL
    private String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private String DB_URL = "jdbc:mariadb://127.0.0.1/toms_pub"; //static final String DB_URL = "jdbc:mariadb://192.168.100.174/db";

    // Database credentials
    private String USER = "root";
    private String PASS = "Passw0rd";

    public mariadb() {
    }

    /**
     * This is used to read from the database with given parameters.
     *
     * @param sql        This is the SQL statement that is to be sent to the DB. I.e "SELECT * FROM users"
     * @param parameters When we give a SQL statement that is prepared with variables the vars and the params.
     *                   I.e "SELECT * FROM users where user_id = ?", 5
     * @return A ResultSet is returned to get the retrieved data from the DB.
     */
    public ResultSet prepared_read_query(String sql, Object... parameters) {
        try {
            // create our mysql database connection
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // create the java statement
            Statement st = conn.createStatement();

            // prepare the query
            PreparedStatement preparedStatement = getPS(conn, sql, parameters);

            // execute the query, and get a java resultset
            assert preparedStatement != null;
            ResultSet rs = preparedStatement.executeQuery();

            // close connection
            st.close();
            conn.close();

            // return sql result
            return rs;
        } catch (Exception e) {
            System.err.println("Got an exception! EXITING ");
            System.err.println(e.getMessage());
        }

        return null;
    }//end prepared_read_query

    /**
     * As this is logic is used by both the Read and the Write statements it was out into a method to remove duplicate code.
     * It is used to get the prepared statment.
     *
     * @param conn       This is the connection to the database.
     * @param sql        This is the SQL statement given by the class.
     * @param parameters These are the params that relate to the given SQL statement.
     * @return The prepared statement is returned.
     */
    private PreparedStatement getPS(Connection conn, String sql, Object... parameters) {

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }
            return preparedStatement;
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return null;
        }

    }

    /**
     * This acts the same as the read method but it writes to the DB instead.
     *
     * @param sql        The given SQL statement.
     * @param parameters The params relating to the DB
     * @return A true (success) or false (fail) is returned. This is very useful for debugging the statements when they are first written
     * We took the decision to keep this in here in case further modules are added .
     */
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
            assert preparedStatement != null;
            preparedStatement.executeQuery();

        } catch (Exception se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return false;
        }//Handle errors for Class.forName
        finally {
            //finally block used to close resources
            closeConnection(stmt, conn);
        }//end try

        return true;
    }//end write_query

    /**
     * This closes the connection to the DB.
     *
     * @param stmt It will close the connection if there are no outstanding statements .
     * @param conn This is the DB connection.
     */
    private void closeConnection(Statement stmt, Connection conn) {
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
}