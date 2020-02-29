package com.example.caffeineoverflow264.model;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImage() {
        return image;
    }

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("amount")
    private String amount;
    @SerializedName("unit")
    private String unit;
    @SerializedName("image")
    private String image;


}