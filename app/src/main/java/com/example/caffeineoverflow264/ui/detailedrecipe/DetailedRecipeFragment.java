package com.example.caffeineoverflow264.ui.detailedrecipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caffeineoverflow264.R;
import com.example.caffeineoverflow264.model.DetailedRecipe;
import com.example.caffeineoverflow264.model.Ingredient;
import com.example.caffeineoverflow264.model.Result;
import com.example.caffeineoverflow264.ui.SharedViewModel;
import com.example.caffeineoverflow264.util.IngridentListAdapter;
import com.example.caffeineoverflow264.util.OnIngredientClickListener;
import com.example.caffeineoverflow264.repository.service.DownloadService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailedRecipeFragment extends Fragment {
    private static final String TAG = DetailedRecipeFragment.class.getSimpleName();

    private TextView recipeNameTv;
    private TextView recipeInstructionTv;
    private RecyclerView recyclerView;

    private String recipeId;
    private DetailedRecipe detailedRecipe;
    private List<Ingredient> ingredients = new ArrayList<>();
    private IngridentListAdapter ingridentListAdapter;

    private SharedViewModel sharedViewModel;
    private DetailedRecipeViewModel detailedRecipeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("MIA       DetailedRecipe Fragment -> onCreateView()");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Recipe Detail");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#008577")));

        return inflater.inflate(R.layout.fragment_detailed_recipe, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        sharedViewModel.getSelectedResult().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                recipeId = Objects.requireNonNull(sharedViewModel.getSelectedResult().getValue()).getId();
                displayRecipe(recipeId);
            }
        });

        recipeNameTv = getView().findViewById(R.id.recipeTitle);
        recipeInstructionTv = getView().findViewById(R.id.recipeInstruction);
        detailedRecipeViewModel = new ViewModelProvider(this).get(DetailedRecipeViewModel.class);
        recyclerView = getView().findViewById(R.id.ingridentList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void displayRecipe(String id) {
        detailedRecipeViewModel.getDetailedRecipe(id).observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                detailedRecipe = data;
                recipeNameTv.setText(detailedRecipe.getTitle());
                recipeInstructionTv.setText(detailedRecipe.getInstructions());
                ingredients = data.getExtendedIngredients();
                ingridentListAdapter = new IngridentListAdapter(ingredients, new OnIngredientClickListener() {
                    @Override
                    public void onIngredientClick(Ingredient ingredient) { // Direct to Amazon Web Browser
                        Log.d(TAG, "ingredient clicked: " + ingredient.getName());
                        String data = "https://www.amazon.com/s?k=" + ingredient.getName() + "&ref=nb_sb_noss";
                        Intent defaultBrowser = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER);
                        defaultBrowser.setData(Uri.parse(data));
                        startActivity(defaultBrowser);
                    }
                });
                recyclerView.setAdapter(ingridentListAdapter);
                ingridentListAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflate menu
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.detailed_recipe_options_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle menu item clicks
        int id = item.getItemId();
        if (id == R.id.downloadOption) {
            download();
        }
        if (id == R.id.shareOption) {
            share();
        }
        return super.onOptionsItemSelected(item);
    }


    private void download() {
        StringBuilder ingredientString = new StringBuilder();
        String instructions = detailedRecipe.getInstructions();
        String title = detailedRecipe.getTitle();
        ingredientString.append("Ingredients");
        ingredientString.append("\n");
        for (Ingredient ingredient : detailedRecipe.getExtendedIngredients()) {
            ingredientString.append(ingredient.getName() + "(" + ingredient.getAmount() + " " + ingredient.getUnit() + ")");
            ingredientString.append("\n");
        }
        Intent intent = new Intent(getActivity(), DownloadService.class);
        intent.putExtra("instructions", instructions);
        intent.putExtra("title", title);
        intent.putExtra("ingredients", ingredientString.toString());
        getActivity().startService(intent);

    }

    private void share() {
        StringBuilder recipeString = new StringBuilder();
        recipeString.append(detailedRecipe.getTitle());
        recipeString.append("\n");
        recipeString.append("\n");
        recipeString.append("Ingredients");
        recipeString.append("\n");
        recipeString.append("\n");
        for (Ingredient ingredient : detailedRecipe.getExtendedIngredients()) {
            recipeString.append(ingredient.getName() + "(" + ingredient.getAmount() + " " + ingredient.getUnit() + ")");
            recipeString.append("\n");
        }
        recipeString.append("\n");
        recipeString.append(detailedRecipe.getInstructions());

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, recipeString.toString());
        String intentTitle = "Share recipe via....";
        Intent chosenIntent = Intent.createChooser(intent, intentTitle);
        startActivity(chosenIntent);
    }
}
