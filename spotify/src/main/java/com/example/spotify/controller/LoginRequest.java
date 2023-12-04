package com.example.spotify.controller;

public class LoginRequest {
    private String userName;
    private String password;

    // userName에 대한 getter
    public String getUserName() {
        return userName;
    }

    // userName에 대한 setter
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // password에 대한 getter
    public String getPassword() {
        return password;
    }

    // password에 대한 setter
    public void setPassword(String password) {
        this.password = password;
    }
}
