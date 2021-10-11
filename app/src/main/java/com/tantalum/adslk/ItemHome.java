package com.tantalum.adslk;

public class ItemHome {

    String productCategory;
    String productDescription;
    String productIcon;
    String productId;
    String productPrice;
    String productTitle;

    public ItemHome() {
    }

    public ItemHome(String productCategory, String productDescription, String productIcon, String productId, String productPrice, String productTitle) {
        this.productCategory = productCategory;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.productId = productId;
        this.productPrice = productPrice;
        this.productTitle = productTitle;
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

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
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
}
