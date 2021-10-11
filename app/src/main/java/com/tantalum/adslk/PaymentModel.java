package com.tantalum.adslk;

public class PaymentModel {

    String username;
    String name;
    String bankname;
    String accountnumber;

    public PaymentModel() {
    }

    public PaymentModel(String username, String name, String bankname, String accountnumber) {
        this.username = username;
        this.name = name;
        this.bankname = bankname;
        this.accountnumber = accountnumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }
}
