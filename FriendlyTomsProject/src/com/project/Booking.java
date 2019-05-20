package com.project;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Booking {

    private String date;
    private String time;
    private String amount;
    private String name;

    public Booking(){
    }

    public void setDate(String date){this.date = date; }

    public String getDate(){return date;}

    public void setTime(String time){
        this.time = time;
    }

    public String getTime(){return time;}

    public void setAmount(String amount){this.amount = amount;}

    public String getAmount(){return amount;}

    public void setName(String name){this.name = name;}

    public String getName(){return name;}
}
