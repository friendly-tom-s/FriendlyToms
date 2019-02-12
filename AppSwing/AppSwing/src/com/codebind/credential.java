package com.codebind;

public class credential {

    /*
    functions required

    create user (create_user)
    hash and salt password (hash_salt_password)
    verify credentials (authenticate_user)
    update_credentials / change username / change password?



     */

    public static void main(String[] args) {
        credential credential_management = new credential();

        credential_management.create_user("haydn","Passw0rd");


    }

    public boolean create_user(String username, String password) {
        System.out.println("Supplied username: " + username + ", password: " + password);

        //hash and salt the users password
        //https://stackoverflow.com/questions/2860943/how-can-i-hash-a-password-in-java




        //then add a db entry to store username, hash and salt

        //INSERT INTO <> (username,hash,salt) VALUES ('username','YIIBAWDBUI','BWD')

        return false;
    }

    public boolean hash_salt_password(String password) {



        return false;
    }

    public boolean authenticate_user(String username, String password) {



        return false;
    }

}
