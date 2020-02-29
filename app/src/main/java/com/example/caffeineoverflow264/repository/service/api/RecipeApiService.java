package com.example.caffeineoverflow264.repository.service.api;

import com.example.caffeineoverflow264.model.TopResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RecipeApiService {
    @GET("recipes/search")
    @Headers({
            "x-rapidapi-host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com",
            "x-rapidapi-key: f5f17108cfmsh651e47d752cbfd2p13124cjsn9ee6f39b357a"
    })
    Call<TopResults> getTopResults(@Query("query") String foodName);
}
