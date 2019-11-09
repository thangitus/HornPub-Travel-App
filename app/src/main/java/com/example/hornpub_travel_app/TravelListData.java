package com.example.hornpub_travel_app;

public class TravelListData {
    private String picture, direction, time, customer, price;

    public TravelListData(String picture, String direction, String time, String customer, String price) {
        this.picture = picture;
        this.direction = direction;
        this.time = time;
        this.customer = customer;
        this.price = price;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomer() {
        return customer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
