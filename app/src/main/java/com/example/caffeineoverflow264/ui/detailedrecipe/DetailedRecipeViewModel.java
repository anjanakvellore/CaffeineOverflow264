package com.example.caffeineoverflow264.ui.detailedrecipe;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.caffeineoverflow264.model.DetailedRecipe;
import com.example.caffeineoverflow264.model.Ingredient;
import com.example.caffeineoverflow264.repository.service.api.DetailedRecipeApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import androidx.lifecycle.ViewModel;

import static android.content.ContentValues.TAG;

public class DetailedRecipeViewModel extends ViewModel {

    private Retrofit retrofit;
    private static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/";
    private MutableLiveData<DetailedRecipe> detailedRecipe = new MutableLiveData<>();
    private MutableLiveData<List<Ingredient>> ingredients = new MutableLiveData<>();

    public LiveData<DetailedRecipe> getDetailedRecipe(String id) {
        loadDetailRecipe(id);
        return detailedRecipe;
    }

    private void loadDetailRecipe(String id) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        DetailedRecipeApiService recipeDetailApiService = retrofit.create(DetailedRecipeApiService.class);
        Call<DetailedRecipe> call = recipeDetailApiService.getDetailedRecipe(id);
        Log.d(TAG, "MIA      call.request(): " + call.request().toString());
        call.enqueue(new Callback<DetailedRecipe>() {
            @Override
            public void onResponse(Call<DetailedRecipe> call, Response<DetailedRecipe> response) {
                DetailedRecipe data = response.body();
                detailedRecipe.setValue(data);
                List<Ingredient> ingredientList = response.body().getExtendedIngredients();
                ingredients.setValue(ingredientList);
            }

            @Override
            public void onFailure(Call<DetailedRecipe> call, Throwable t) {
                Log.e(TAG, "DetailedRecipe API call fails");
            }
        });
    }

}
