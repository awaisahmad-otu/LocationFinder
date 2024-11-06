package com.example.locationfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationAdapter.LocationClickListener {

    private DBHelper DBHelper;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // set up list view, search bar, and initiate database with 100 locations
        recyclerView = findViewById(R.id.locationsList);
        DBHelper = new DBHelper(this);
        DBHelper.insertInitialLocations(this);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                loadLocations(s);
                return false;
            }
        });

        loadLocations("");
    }

    // reload locations when coming back
    @Override
    protected void onResume() {
        super.onResume();
        loadLocations("");
    }

    // create new location activity
    public void launchNewLocation(View v) {
        Intent i = new Intent(this, NewLocation.class);
        startActivity(i);
    }

    // load all the locations from the database
    private void loadLocations(String query) {
        List<Location> locations = new ArrayList<>();
        Cursor cursor;
        if (query.isEmpty()) {
            cursor = DBHelper.retrieveAllLocations();
        } else {
            cursor = DBHelper.retrieveLocationsByAddress(query);
        }

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(com.example.locationfinder.DBHelper.COLUMN_ID));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(com.example.locationfinder.DBHelper.COLUMN_ADDRESS));
                @SuppressLint("Range") String latitude = cursor.getString(cursor.getColumnIndex(com.example.locationfinder.DBHelper.COLUMN_LATITUDE));
                @SuppressLint("Range") String longitude = cursor.getString(cursor.getColumnIndex(com.example.locationfinder.DBHelper.COLUMN_LONGITUDE));

                locations.add(new Location(id, address, latitude, longitude));
        } while (cursor.moveToNext());
        cursor.close();
        }
        LocationAdapter locationAdapter = new LocationAdapter(locations, (LocationAdapter.LocationClickListener) this);
        recyclerView.setAdapter(locationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // edit a location by clicking on it
    public void onLocationClick(Location location) {
        Intent i = new Intent(this, EditLocation.class);
        i.putExtra("LOCATION_ID", location.getId());
        i.putExtra("LOCATION_ADDRESS", location.getAddress());
        i.putExtra("LOCATION_LATITUDE", location.getLatitude());
        i.putExtra("LOCATION_LONGITUDE", location.getLongitude());
        startActivity(i);
    }
}