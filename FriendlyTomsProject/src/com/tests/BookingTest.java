package com.tests;


import com.project.Booking;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookingTest {
    Booking booking = new Booking();

    @Test
    public void test_that_a_string_is_returned_when_a_string_is_set(){

        booking.setName("This is a test");
        assertEquals("This is a test", booking.getName());

    }
}