package com.epam.page_objects.entities;

/**
 * Created by Roman_Iovlev on 5/21/2015.
 */
public class User {
    public static User DefaultUser = new User();

    public String login = "gga";
    public String password = "123";

    public User() { }
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }


}

