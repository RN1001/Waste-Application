package com.example.myapplication.models;

public class Address {

    private String addressid;
    private String addressOne;
    private String addressTwo;
    private String street;
    private String locality;
    private String town;


    public Address(String addressid, String addressOne, String addressTwo, String street, String locality,
                   String town) {
        this.addressid = addressid;
        this.addressOne = addressOne;
        this.addressTwo = addressTwo;
        this.street = street;
        this.locality = locality;
        this.town = town;
    }


    public Address() {
        // TODO Auto-generated constructor stub
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public String toString() {

        return addressid + ", " + addressOne + ", " + addressTwo + ", " + street + ", " + town + ", " + locality;
    }
}
