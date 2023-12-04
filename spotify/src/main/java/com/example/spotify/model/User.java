package com.example.spotify.model;
import java.sql.Date;

public class User {

    private String userID;
    private String userName;
    private String realName;
    private String email;
    private String password;
    private String gender;
    private Date dateOfBirth;
    private String location;
    private String nationality;
    private String subscriptionID;

    // Constructor
    public User() {
        this.userID = userID;
        this.userName = userName;
        this.realName = realName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.location = location;
        this.nationality = nationality;
        this.subscriptionID = subscriptionID;
    }

    // Getters and Setters
    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getSubscriptionID() {
        return this.subscriptionID;
    }

    public void setAdmin(String subscriptionID) {
        this.subscriptionID = subscriptionID;
    }

}