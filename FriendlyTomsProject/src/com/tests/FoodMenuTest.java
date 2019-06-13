package com.tests;


import com.project.FoodMenu;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FoodMenuTest {

    @Test
    public void testThatTheTestWorks(){
        FoodMenu foodMenu = new FoodMenu();
        int newAdd = foodMenu.addTen(0);
        assertEquals(10,newAdd);

    }

}
