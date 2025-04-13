package com.example.demo.model;

// Class Login created for request-login, in the Borrowing Controller.
public class Login {
    private String username ;
    private String password;

    // jsonbody schema
    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
