package com.example.caffeineoverflow264.model;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("address")
    private String address;

    @SerializedName("locality")
    private String locality;

    @SerializedName("city")
    private String city;

    @SerializedName("zipcode")
    private String zipCode;

    public Location(String address,String locality,String city, String zipCode){
        this.address = address;
        this.locality = locality;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public String getLocality() {
        return locality;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

}
