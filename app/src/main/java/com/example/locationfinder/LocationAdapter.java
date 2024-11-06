package com.example.locationfinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationHolder> {
    private List<Location> locationsList;
    private LocationClickListener listener;

    public interface LocationClickListener {
        void onLocationClick(Location location);
    }

    public LocationAdapter(List<Location> locationsList, LocationClickListener listener) {
        this.locationsList = locationsList;
        this.listener = listener;
    }

    public static class LocationHolder extends RecyclerView.ViewHolder {
        public TextView textViewAddress;
        public TextView textViewLatitude;
        public TextView textViewLongitude;

        public LocationHolder(View itemView) {
            super(itemView);
            textViewAddress = itemView.findViewById(R.id.address);
            textViewLatitude = itemView.findViewById(R.id.latitude);
            textViewLongitude = itemView.findViewById(R.id.longitude);
        }
    }

    @NonNull
    @Override
    public LocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_location_view, parent, false);
        return new LocationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationHolder holder, int position) {
        Location location = locationsList.get(position);
        holder.textViewAddress.setText(location.getAddress());
        holder.textViewLatitude.setText(location.getLatitude());
        holder.textViewLongitude.setText(location.getLongitude());

        // Handle single click to edit note
        holder.itemView.setOnClickListener(v -> {
            listener.onLocationClick(location);
        });
    }

    @Override
    public int getItemCount() {
        return locationsList.size();
    }
}
