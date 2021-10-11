package com.tantalum.adslk;

public class PublishPkgModel {

    String username;
    String publishPkgName;
    String amount;
    String date;

    public PublishPkgModel() {
    }

    public PublishPkgModel(String username, String publishPkgName, String amount, String date) {
        this.username = username;
        this.publishPkgName = publishPkgName;
        this.amount = amount;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPublishPkgName() {
        return publishPkgName;
    }

    public void setPublishPkgName(String publishPkgName) {
        this.publishPkgName = publishPkgName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
