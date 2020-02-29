package com.example.caffeineoverflow264.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetails {
    @SerializedName("restaurants")
    private List<Restaurant> restaurantList;

    public RestaurantDetails(){
        restaurantList = new ArrayList<>();
    }

    public List<Restaurant> getRestaurantList(){
        return this.restaurantList;
    }

}
