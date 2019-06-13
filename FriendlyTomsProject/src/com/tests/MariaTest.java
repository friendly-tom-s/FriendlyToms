package com.tests;

import org.junit.Test;
import com.project.mariadb;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class MariaTest {

    mariadb database = new mariadb();

    @Test
    public void test_that_DB_returns_correct_first_col_with_select_statement(){
        String name = null;
        ResultSet testRS =  database.prepared_read_query("SELECT * FROM users");

        ResultSetMetaData rsmd = null;
        try {
            rsmd = testRS.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            name = rsmd.getColumnName(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals("user_id", name);

    }

    @Test
    public void test_that_incorrect_SQL_statement_throws_error(){
        ResultSet testRS =  database.prepared_read_query("SELECT * FROM nothing");
        assertEquals(null, testRS);
    }
}
