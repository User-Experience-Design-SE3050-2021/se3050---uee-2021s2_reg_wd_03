package com.ads.lk;

public class User {

    private String name;
    private String email;
    private String password;
    private String type;
    private  String phone;
    private String live;

    public User() {
    }






    public User(String name, String email, String password, String type, String phone, String live) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.phone=phone;
        this.live=live;
    }


    //getters

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public String getPhone() {
        return phone;
    }

    public String getLive() {
        return live;
    }

    //setters

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLive(String live) {
        this.live = live;
    }


}
