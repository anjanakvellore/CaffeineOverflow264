package com.example.caffeineoverflow264.repository.service.api;

import com.example.caffeineoverflow264.model.CityDetails;
import com.example.caffeineoverflow264.model.RestaurantDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {
    //not being used for now
    /*@Headers("user-key: d15bf2b4b1fe702fd8188e351a8a2440")
    @GET("api/v2.1/cities")
    Call<CityDetails> getCityDetails(@Query("lat") double latitude, @Query("lon") double longitude);*/

    @Headers("user-key: d15bf2b4b1fe702fd8188e351a8a2440")
    @GET("api/v2.1/search")
    Call<RestaurantDetails> getRestaurantDetails(
            @Query("lat") double latitude, @Query("lon") double longitude,
            @Query("entity_type") String entityType, @Query("cuisines") int cuisine,
            @Query("establishment_type") int establishmentType);


}
