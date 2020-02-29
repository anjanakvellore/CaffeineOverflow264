package com.example.caffeineoverflow264.model;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("readyInMinutes")
    private int readyInMinutes;
    @SerializedName("servings")
    private int servings;

    public Result(String id, String title, int readyInMinutes, int servings) {
        this.id = id;
        this.title = title;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public int getServings() {
        return servings;
    }
}
