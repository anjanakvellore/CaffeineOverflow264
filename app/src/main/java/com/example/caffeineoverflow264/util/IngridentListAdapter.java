package com.example.caffeineoverflow264.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caffeineoverflow264.R;
import com.example.caffeineoverflow264.model.Ingredient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IngridentListAdapter extends RecyclerView.Adapter<IngridentListAdapter.ViewHolder> {

    private List<Ingredient> ingredients;
    private OnIngredientClickListener listener;


    public IngridentListAdapter(List<Ingredient> ingredients, OnIngredientClickListener listener) {
        this.listener = listener;
        this.ingredients = ingredients;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIngredient;
        //        TextView ingredientId;
        TextView ingredientName;
        TextView ingredientAmount;
        TextView ingredientUnit;

        ViewHolder(View itemView) {
            super(itemView);
            ivIngredient = itemView.findViewById(R.id.ivIngredient);
            ingredientName = itemView.findViewById(R.id.ingredientName);
//            ingredientId = itemView.findViewById(R.id.ingredientId);
            ingredientAmount = itemView.findViewById(R.id.ingredientAmount);
            ingredientUnit = itemView.findViewById(R.id.ingredientUnit);
        }

        public void bind(final Ingredient item, final OnIngredientClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    System.out.println("Ingredient is clicked " + item.getName() );
                    listener.onIngredientClick(item);
                }
            });
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+ingredient.getImage()).into(holder.ivIngredient);
//        holder.ingredientId.setText(ingredient.getId());
        holder.ingredientName.setText(ingredient.getName());
        holder.ingredientAmount.setText(String.valueOf(ingredient.getAmount()));
        holder.ingredientUnit.setText(String.valueOf(ingredient.getUnit()));
        holder.bind(ingredient, listener);
    }



    @Override
    public int getItemCount() {
        if (ingredients != null) {
            return ingredients.size();
        } else {
            return 0;
        }
    }
}
