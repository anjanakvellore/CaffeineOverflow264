package com.example.caffeineoverflow264.ui.recipe;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.caffeineoverflow264.model.Result;
import com.example.caffeineoverflow264.model.TopResults;
import com.example.caffeineoverflow264.repository.service.api.RecipeApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class RecipeViewModel extends ViewModel {
    private Retrofit retrofit;
    private static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/";
    private MutableLiveData<List<Result>> results = new MutableLiveData<>();

    public LiveData<List<Result>> getRecipes(String drinkName){
        loadRecipes(drinkName);
        return results;
    }

    private void loadRecipes(String drinkName) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        RecipeApiService recipeApiService = retrofit.create(RecipeApiService.class);
        Call<TopResults> call = recipeApiService.getTopResults(drinkName);
        Log.d(TAG, "MIA      call.request(): " + call.request().toString());
        call.enqueue(new Callback<TopResults>() {
            @Override
            public void onResponse(Call<TopResults> call, Response<TopResults> response) {
                List<Result> recipelist = response.body().getResults();
                results.setValue(recipelist);
            }
            @Override
            public void onFailure(Call<TopResults> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
}