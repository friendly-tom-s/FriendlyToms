package com.codebind;

public class testClass {

    private int _startCadence;

    //constructor
    //https://docs.oracle.com/javase/tutorial/java/javaOO/constructors.html
    public testClass(int startCadence, int startSpeed, int startGear) {

        _startCadence = startCadence;

        /*gear = startGear;
        cadence = startCadence;
        speed = startSpeed;*/
    }

    //call a class
    //https://stackoverflow.com/questions/19809739/call-a-class-from-another-class

    public String testfunc(String username, String password)
    {
        /*Class2 class2 = new Class2();
        class2.invokeSomeMethod();
        //your actual code
        */

        String concat = username + password + _startCadence;

        return concat;
    }

}
