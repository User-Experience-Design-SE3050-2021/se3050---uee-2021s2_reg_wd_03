package com.example.adsmobileapp;

public class ModeAdDetails {

    private String currentDate;
    private String time;
    private String productCategory;
    private String productDescription;
    private String productId;
    private String productPrice;
    private String productTitle;
    private String productIcon;


    public ModeAdDetails() {

    }

    public ModeAdDetails(String currentDate, String time, String productCategory, String productDescription, String productId, String productPrice, String productTitle, String productIcon) {
        this.currentDate = currentDate;
        this.time = time;
        this.productCategory = productCategory;
        this.productDescription = productDescription;
        this.productId = productId;
        this.productPrice = productPrice;
        this.productTitle = productTitle;
        this.productIcon = productIcon;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }
}