package com.example.caffeineoverflow264.model;

import com.google.gson.annotations.SerializedName;

public class UserRating {

    @SerializedName("aggregate_rating")
    private String aggregateRating;

    @SerializedName("rating_text")
    private String ratingText;

    @SerializedName("votes")
    private String votes;

    public UserRating(String aggregateRating,String ratingText,String votes){
        this.aggregateRating = aggregateRating;
        this.ratingText = ratingText;
        this.votes = votes;
    }

    public String getAggregateRating() {
        return aggregateRating;
    }

    public String getRatingText() {
        return ratingText;
    }

    public String getVotes() {
        return votes;
    }





}
