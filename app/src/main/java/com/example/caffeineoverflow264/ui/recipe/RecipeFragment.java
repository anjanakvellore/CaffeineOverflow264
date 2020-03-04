package com.example.caffeineoverflow264.ui.recipe;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caffeineoverflow264.R;
import com.example.caffeineoverflow264.ui.SharedViewModel;
import com.example.caffeineoverflow264.ui.detailedrecipe.*;
import com.example.caffeineoverflow264.model.Result;
import com.example.caffeineoverflow264.model.TopResults;
import com.example.caffeineoverflow264.repository.service.api.RecipeApiService;
import com.example.caffeineoverflow264.util.OnItemClickListener;
import com.example.caffeineoverflow264.util.ResultListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class RecipeFragment extends Fragment {

    private static final String TAG = RecipeFragment.class.getSimpleName();
    private static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/";

    private EditText edt_drinkquery;

    private List<Result> results = new ArrayList<>();
    private ResultListAdapter resultListAdapter;

    private Retrofit retrofit;

    private SharedViewModel sharedViewModel;

    private String eventName = null; // String of drink name sent back from Log

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        System.out.println("MIA       Recipe Fragment -> onCreateView()");


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Recipe");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#008577")));

        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("MIA       Recipe Fragment -> onViewCreated()");
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // TODO: change icon with the open fragment
//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
//        View view = bottomNavigationView.findViewById(R.id.navigation_recipe);
//        view.performClick();


        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        // Retrieve the string of drink name sent back from Log
        sharedViewModel.getSelectedEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String eventName) {
                edt_drinkquery.setText(eventName);
                getRecipes(sharedViewModel.getSelectedEvent().getValue());
            }
        });

        // Dump data into recycler view
        RecyclerView recyclerView = getView().findViewById(R.id.rvRecipeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        resultListAdapter = new ResultListAdapter(results, new OnItemClickListener() {
            @Override
            public void onItemClick(Result result) {
                Log.d(TAG, "MIA      OnItemClick(): " + result.getTitle());
                sharedViewModel.selectResult(result);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        new DetailedRecipeFragment()).commit();
            }
        });
        recyclerView.setAdapter(resultListAdapter);



        edt_drinkquery = getView().findViewById(R.id.edt_drinkquery);
        // Add event listener for the search button
        Button btn_drinksearch = (Button) getView().findViewById(R.id.btn_drinksearch);
        btn_drinksearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String drinkQueryStr = edt_drinkquery.getText().toString();
                Log.d(TAG,"MIA       drinkQueryStr: " + drinkQueryStr );
                getRecipes(drinkQueryStr);
                try {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });


    }

    private void getRecipes(String drinkName) {
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
                results.clear();
                results.addAll(response.body().getResults());
                resultListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<TopResults> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
}