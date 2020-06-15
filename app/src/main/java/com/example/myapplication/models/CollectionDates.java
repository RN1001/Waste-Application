package com.example.myapplication.models;

public class CollectionDates {

    private String addressid;
    private String type;
    private String date;

    public CollectionDates() {
        // empty
    }

    public CollectionDates(String addressid, String type, String date) {
        this.addressid = addressid;
        this.type = type;
        this.date = date;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return addressid + ", " + type + ", " + date;
    }
}
