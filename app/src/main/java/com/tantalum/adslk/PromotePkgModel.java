package com.tantalum.adslk;

public class PromotePkgModel {

    String username;
    String prmPkgName;
    String amount;
    String date;

    public PromotePkgModel() {
    }


    public PromotePkgModel(String username, String prmPkgName, String amount, String date) {
        this.username = username;
        this.prmPkgName = prmPkgName;
        this.amount = amount;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPrmPkgName() {
        return prmPkgName;
    }

    public void setPrmPkgName(String prmPkgName) {
        this.prmPkgName = prmPkgName;
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
