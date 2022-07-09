package com.dagla.android.parser;

import java.util.ArrayList;

public class HomeCateProductsDetails {

    private String banner_id;
    private String product_id;
    private String name;
    private String brand_name;
    private String price;
    private String old_price;

    private String image;
    
    public HomeCateProductsDetails(String banner_id, String product_id, String name, String brand_name,
                           String price,String old_price,String image) {

        this.banner_id = banner_id;
        this.product_id = product_id;
        this.name = name;
        this.brand_name = brand_name;
        this.price = price;
        this.old_price = old_price;
        this.image = image;


    }



    public String getBannerId() {
        return banner_id;
    }

    public void setBannerId(String banner_id) {
        this.banner_id = banner_id;
    }

    public String getProductId() {
        return product_id;
    }

    public void setProductId(String product_id) {
        this.product_id = product_id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBannerName() {
        return brand_name;
    }

    public void setBannerName(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOldPrice() {
        return old_price;
    }

    public void setOldPrice(String old_price) {
        this.old_price = old_price;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
