package com.tests;
import com.project.LoginForm;
import com.project.TemplateGui;
import com.project.mariadb;
import org.junit.Test;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class TemplateTest {

    private TemplateGui templateGui = new TemplateGui("testClass", "testClass", "testClass");
    private mariadb database = new mariadb();
    LoginForm loginForm = new LoginForm();

    @Test
    public void test_that_the_correct_user_is_stored_and_retrieved(){

        String createdTestIndex = setDatabaseForTest();
        loginForm.saveSessionUser(createdTestIndex);

        String sessionUserIndex = templateGui.getUser();
        String sessionUserString = templateGui.getUserName(sessionUserIndex);

        assertEquals("TestUserTester", sessionUserString);

        removeTestsFromDB(createdTestIndex);
    }

    @Test
    public void test_that_the_correct_index_is_returned(){
        String newTestIndex = setDatabaseForTest();
        loginForm.saveSessionUser(newTestIndex);
        String newComparisonIndex = templateGui.getUser();

        assertEquals(newTestIndex,newComparisonIndex);
        removeTestsFromDB(newTestIndex);
    }

    public String setDatabaseForTest(){
        String testIndex = null;
        String testUsername = "TestUserTester";
        String testSalt = "TestSalt";
        String testHash = "TestHash";
        String testAdmin = "0";

        boolean write_query = database.prepared_write_query("INSERT INTO users (username,salt,hash,is_admin) VALUES (?, ?, ?, ?)"
                , testUsername, testSalt, testHash, testAdmin);
        ResultSet getIndexFromDatabase = database.prepared_read_query("SELECT * FROM users WHERE username = ?", testUsername);

        try {
            while(getIndexFromDatabase.next()) {
                testIndex = getIndexFromDatabase.getString("user_id");
            }
        }
        catch (Exception a){System.out.println("Exception Caught");}//try

        return testIndex;
    }

    public void removeTestsFromDB(String testIndex){
        boolean write_query = database.prepared_write_query("DELETE FROM users WHERE user_id = ?", testIndex );
    }
}
