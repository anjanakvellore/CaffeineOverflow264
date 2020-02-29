package com.example.caffeineoverflow264.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CityDetails {
    @SerializedName("location_suggestions")
    private List<City> cityList;

    public CityDetails(){
        cityList = new ArrayList<City>();
    }

    public List<City> getCityList(){
        return cityList;
    }
}
