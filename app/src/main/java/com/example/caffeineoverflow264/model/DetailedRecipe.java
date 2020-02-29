package com.example.caffeineoverflow264.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DetailedRecipe {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("extendedIngredients")
    private ArrayList<Ingredient> extendedIngredients;
    @SerializedName("instructions")
    private String instructions;

    public DetailedRecipe(String id, String title, ArrayList<Ingredient> extendedIngredients, String instructions) {
        this.id = id;
        this.title = title;
        this.extendedIngredients = extendedIngredients;
        this.instructions = instructions;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Ingredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public String getInstructions() {
        return instructions;
    }
}
