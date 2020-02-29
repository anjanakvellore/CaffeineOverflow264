package com.example.caffeineoverflow264.model;

import com.google.gson.annotations.SerializedName;

public class RestaurantData {

    @SerializedName("name")
    private String name;

    @SerializedName("average_cost_for_two")
    private String averageCostForTwo;

    @SerializedName("currency")
    private String currency;

    @SerializedName("user_rating")
    private UserRating userRating;

    @SerializedName("location")
    private Location location;

    @SerializedName("phone_numbers")
    private String phoneNumbers;

    @SerializedName("timings")
    private String timings;

    public RestaurantData(String name, String averageCostForTwo, String currency, UserRating userRating, Location location, String phoneNumbers){
        this.name = name;
        this.averageCostForTwo = averageCostForTwo;
        this.currency = currency;
        this.userRating = userRating;
        this.location = location;
        this.phoneNumbers = phoneNumbers;
    }


    public String getName() {
        return name;
    }

    public String getAverageCostForTwo() {
        return averageCostForTwo;
    }

    public String getCurrency() {
        return currency;
    }

    public UserRating getUserRating() {
        return userRating;
    }

    public Location getLocation() {
        return location;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public String getTimings(){return timings;};

}
