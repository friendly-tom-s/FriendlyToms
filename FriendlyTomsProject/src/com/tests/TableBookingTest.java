package com.tests;

import com.project.TableBooking;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class TableBookingTest {

    TableBooking tableBooking = new TableBooking();

    @Test
    public void test_that_future_date_will_return_zero_bookings(){
        ResultSet testData = tableBooking.getSitting(1, "12/12/99");
        int sumReturnedString= 10;
        try{
            while(testData.next()) {sumReturnedString = testData.getInt("amount");}
            }

        catch (SQLException e) {
            e.printStackTrace();
    }
        assertEquals(0, sumReturnedString);

    }

    @Test
    public void test_that_a_valid_date_returns_true(){
        boolean testBool = tableBooking.validateBooking("12/12/12", "dd/MM/yy");
        boolean expectedBool= true;
        assertEquals(expectedBool, testBool);
    }

    @Test
    public void test_that_an_invalid_date_returns_false(){
        boolean testBool = tableBooking.validateBooking("12/40/12", "dd/MM/yy");
        boolean expectedBool= false;
        assertEquals(expectedBool, testBool);

    }
}
