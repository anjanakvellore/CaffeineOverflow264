package com.example.caffeineoverflow264.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caffeineoverflow264.R;
import com.example.caffeineoverflow264.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ViewHolder> {

    private List<Result> results;
    private OnItemClickListener listener;

    public ResultListAdapter(List<Result> results, OnItemClickListener listener) {
        this.listener = listener;
        this.results = results;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivRecipe;
        TextView tvMinutes;
        TextView tvServings;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivRecipe = itemView.findViewById(R.id.ivRecipe);
            tvMinutes = itemView.findViewById(R.id.tvMinutes);
            tvServings = itemView.findViewById(R.id.tvServings);
        }

        void bind(final Result item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    System.out.println("I am clicked");
                    listener.onItemClick(item);
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.tvTitle.setText(result.getTitle());
        Picasso.get().load("https://spoonacular.com/recipeImages/" + result.getId() +"-480x360.jpg").into(holder.ivRecipe);
        holder.tvMinutes.setText("Minutes to prepare: "+String.valueOf(result.getReadyInMinutes()));
        holder.tvServings.setText("Serving size: "+String.valueOf(result.getServings()));
        holder.bind(results.get(position), listener);
    }

    @Override
    public int getItemCount() {
        if (results != null) {
            return results.size();
        } else {
            return 0;
        }
    }
}
