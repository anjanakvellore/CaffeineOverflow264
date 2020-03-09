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

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caffeineoverflow264.R;
import com.example.caffeineoverflow264.ui.SharedViewModel;
import com.example.caffeineoverflow264.ui.detailedrecipe.*;
import com.example.caffeineoverflow264.model.Result;
import com.example.caffeineoverflow264.util.OnItemClickListener;
import com.example.caffeineoverflow264.util.ResultListAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class RecipeFragment extends Fragment {
    private static final String TAG = RecipeFragment.class.getSimpleName();

    private List<Result> results = new ArrayList<>();
    private ResultListAdapter resultListAdapter;
    private SharedViewModel sharedViewModel;
    private RecipeViewModel recipeViewModel;

    private EditText edt_drinkquery;
    private String drinkQueryStr;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Recipe");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#008577")));
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        edt_drinkquery = getView().findViewById(R.id.edt_drinkquery);

        // Retrieve the string of drink name if it is sent back from Log
        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        sharedViewModel.getSelectedEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String drinkName) {
                edt_drinkquery.setText(drinkName);
                drinkQueryStr = drinkName;
                search();
            }
        });

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        recyclerView = getView().findViewById(R.id.rvRecipeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Add event listener for the search button
        Button btn_drinksearch = getView().findViewById(R.id.btn_drinksearch);
        btn_drinksearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drinkQueryStr = edt_drinkquery.getText().toString();
                search();
                try {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
    }

    private void search() {
        Log.d(TAG, "MIA       drinkQueryStr: " + drinkQueryStr);
        recipeViewModel.getRecipes(drinkQueryStr).observe(getViewLifecycleOwner(), recipelist -> {
            if (recipelist != null) {
                results = recipelist;
                resultListAdapter = new ResultListAdapter(results, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Result result) {
                        Log.d(TAG, "MIA      OnItemClick(): " + result.getTitle());
                        sharedViewModel.selectResult(result);
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DetailedRecipeFragment()).commit();
                    }
                });
                recyclerView.setAdapter(resultListAdapter);
                resultListAdapter.notifyDataSetChanged();
            }
        });
    }

}