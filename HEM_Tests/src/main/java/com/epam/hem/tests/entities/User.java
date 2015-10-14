package com.epam.hem.tests.entities;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-08
 */
public class User {

    public static User DEFAULT_USER = new User();

    public String userName = "user";
    public String password = "password";

    public User() {
    }

    public User(String login, String password) {
        this.userName = login;
        this.password = password;
    }
}