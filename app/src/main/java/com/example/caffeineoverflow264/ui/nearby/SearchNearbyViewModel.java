package com.example.caffeineoverflow264.ui.nearby;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.caffeineoverflow264.model.City;
import com.example.caffeineoverflow264.model.Restaurant;
import com.example.caffeineoverflow264.model.RestaurantDetails;
import com.example.caffeineoverflow264.repository.service.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchNearbyViewModel extends ViewModel {

    static final String TAG = SearchNearbyViewModel.class.getSimpleName();
    private MutableLiveData<City> city;
    private MutableLiveData<List<Restaurant>> restaurants;
    private static final String BASE_URL = "https://developers.zomato.com/";
    private static Retrofit retrofit = null;
    private final static String API_KEY = "d15bf2b4b1fe702fd8188e351a8a2440";


    public LiveData<List<Restaurant>> getRestaurantDetails(double latitude, double longitude){
        if(restaurants== null){
            restaurants = new MutableLiveData<List<Restaurant>>();
            loadRestaurantDetails(latitude,longitude);
        }
        return restaurants;
    }

   /* private void loadDetails(double latitude,double longitude){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ApiService apiService = retrofit.create(ApiService.class);
        Call<CityDetails> call = apiService.getCityDetails(latitude,longitude);
        call.enqueue(new Callback<CityDetails>() {
            @Override
            public void onResponse(Call<CityDetails> call, Response<CityDetails> response) {
               CityDetails cityDetails = response.body();
               City cityObj = new City(cityDetails.getCityList().get(0).getCityId(),cityDetails.getCityList().get(0).getCityName());
               //city.setValue(cityObj);
            }
            @Override
            public void onFailure(Call<CityDetails> call, Throwable throwable) {
                //Log.e(TAG, throwable.toString());
            }
        });
    }*/

    private void loadRestaurantDetails(double latitude, double longitude){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ApiService apiService = retrofit.create(ApiService.class);
        Call<RestaurantDetails> call = apiService.getRestaurantDetails(latitude,longitude,"subzone",161,286);
        call.enqueue(new Callback<RestaurantDetails>() {
            @Override
            public void onResponse(Call<RestaurantDetails> call, Response<RestaurantDetails> response) {
                System.out.println( "MIA      : " + "location service on response");
                RestaurantDetails restaurantDetails = response.body();
                List<Restaurant> restaurantList = restaurantDetails.getRestaurantList();
                restaurants.setValue(restaurantList);
            }
            @Override
            public void onFailure(Call<RestaurantDetails> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }

        });
    }
}
