package com.example.caffeineoverflow264.ui.nearby;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caffeineoverflow264.R;
import com.example.caffeineoverflow264.model.City;
import com.example.caffeineoverflow264.model.Restaurant;
import com.example.caffeineoverflow264.util.LocationService;

import java.util.List;

public class NearbyFragment extends Fragment {

    private double latitude = 0.0;
    private double longitude = 0.0;
    private City city;

    List<Restaurant> restaurants;
    private RecyclerView recyclerView;
    SearchNearByAdapter adapter;

    private SearchNearbyViewModel searchNearbyViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffe95451")));
        View rootView = inflater.inflate(R.layout.fragment_nearby, container, false);
        return rootView;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rvRestaurants);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        LocationService locationService = LocationService.getLocationManager(getContext());

        if(locationService.location!=null) {
            this.latitude = locationService.location.getLatitude();
            this.longitude = locationService.location.getLongitude();
            SearchNearbyViewModel model = new ViewModelProvider(this).get(SearchNearbyViewModel.class);
            model.getRestaurantDetails(latitude,longitude).observe(getViewLifecycleOwner(), restaurantList -> {
                if (restaurantList != null) {
                    restaurants = restaurantList;
                    adapter = new SearchNearByAdapter(restaurants);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
        }
        else{
            Toast.makeText(getContext(),"Need location permissions to proceed.",Toast.LENGTH_SHORT);
        }

    }
}