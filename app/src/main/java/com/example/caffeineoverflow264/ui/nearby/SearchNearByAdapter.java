package com.example.caffeineoverflow264.ui.nearby;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.caffeineoverflow264.R;
import com.example.caffeineoverflow264.model.Restaurant;

import java.util.List;

public class SearchNearByAdapter extends RecyclerView.Adapter<SearchNearByAdapter.ViewHolder> {
    private List<Restaurant> restaurants;

    SearchNearByAdapter(List<Restaurant> data) {
        this.restaurants= data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtresName;
        TextView txtresLocation;
        TextView txtresPhone;
        TextView txtresAvgCost;
        TextView txtresRating;
        TextView txtresVotes;
        TextView txtresTimings;
        ImageView imageresImg;

        ViewHolder(View itemView) {
            super(itemView);
            //imageresImg = itemView.findViewById(R.id.resImg);
            txtresName = itemView.findViewById(R.id.resName);
            txtresLocation = itemView.findViewById(R.id.resLocation);
            txtresPhone = itemView.findViewById(R.id.resPhone);
            txtresAvgCost = itemView.findViewById(R.id.resAvgCost);
            txtresRating = itemView.findViewById(R.id.resRating);
            txtresVotes = itemView.findViewById(R.id.resVotes);
            txtresTimings = itemView.findViewById(R.id.resTimings);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_row,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Restaurant res = restaurants.get(position);
        //holder.tv.setText(program);
        //Picasso.get().load(BASE_URL+program.getPosterPath()).resize(300, 300).into(holder.imageView);
        holder.txtresName.setText(res.getRestaurantData().getName());
        String location = res.getRestaurantData().getLocation().getAddress();
        holder.txtresLocation.setText(location);
        holder.txtresPhone.setText(res.getRestaurantData().getPhoneNumbers());
        holder.txtresTimings.setText(res.getRestaurantData().getTimings());
        String avgCost = res.getRestaurantData().getCurrency() + res.getRestaurantData().getAverageCostForTwo();
        holder.txtresAvgCost.setText(avgCost);
        holder.txtresRating.setText(res.getRestaurantData().getUserRating().getAggregateRating());
        holder.txtresVotes.setText(res.getRestaurantData().getUserRating().getVotes());
    }

    @Override
    public int getItemCount() {
        if ( restaurants == null ) {
            return 0;
        } else {
            return restaurants.size();
        }
    }
}
