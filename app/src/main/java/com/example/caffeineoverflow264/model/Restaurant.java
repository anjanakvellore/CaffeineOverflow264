package com.example.caffeineoverflow264.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    @SerializedName("restaurant")
    private RestaurantData restaurantData;

    public Restaurant (RestaurantData restaurantData){
        restaurantData = restaurantData;
    }

    public RestaurantData getRestaurantData(){
        return this.restaurantData;
    }
}