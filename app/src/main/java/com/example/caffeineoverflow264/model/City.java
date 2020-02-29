package com.example.caffeineoverflow264.model;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("id")
    private int cityId;

    @SerializedName("name")
    private String cityName;


    public City(int id, String name){
        cityId = id;
        cityName = name;
    }

    public int getCityId(){
        return cityId;
    }

    public String getCityName(){
        return cityName;
    }


}