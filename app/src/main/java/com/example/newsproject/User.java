package com.example.newsproject;

public class User {
    public String name;
    public String mobileNo;
    public String email;
    public String password;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {}

    public User(String name, String mobileNo, String email, String password) {
        this.name = name;
        this.mobileNo = mobileNo;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters (optional)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
