package com.project;

/**
 * This is where all the user data is set.
 */

public class User {

    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private String confirm_password;
    private String email;
    private String phone_number;
    private int adminStatus;

    public User(){}

    public void setFirst_name(String first_name){
        this.first_name = first_name;
    }

    public void setLast_name(String last_name){this.last_name = last_name;}

    public void setUsername(String Username){this.username = Username;}

    public void setPassword(String password){this.password = password;}

    public void setConfirmPassword(String password){this.confirm_password = password;}

    public void setEmail(String email){this.email = email;}

    public void setAdminStatus(int adminStatus){this.adminStatus = adminStatus;}

    public String getUsername(){return username;}

    public String getPassword(){return password;}

    public String getEmail(){return email;}

    public String getConfirm_password(){return confirm_password;}

    public int getAdminStatus(){return adminStatus;}

    public String getFirst_name(){return first_name;}

    public String getLast_name(){return last_name;}

}
