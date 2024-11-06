package com.example.locationfinder;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditLocation extends AppCompatActivity {
    private DBHelper DBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);

        // set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // enable the back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        DBHelper = new DBHelper(this);

        // pre-fill the fields with the existing location data
        Intent i = getIntent();
        int locationId = i.getIntExtra("LOCATION_ID", -1);
        String locationAddress = i.getStringExtra("LOCATION_ADDRESS");
        String locationLatitude = i.getStringExtra("LOCATION_LATITUDE");
        String locationLongitude = i.getStringExtra("LOCATION_LONGITUDE");

        EditText addressInput = findViewById(R.id.textInputEditAddress);
        EditText latitudeInput = findViewById(R.id.textInputEditLatitude);
        EditText longitudeInput = findViewById(R.id.textInputEditLongitude);

        addressInput.setText(locationAddress);
        latitudeInput.setText(locationLatitude);
        longitudeInput.setText(locationLongitude);

        // set up the save button
        findViewById(R.id.updateLocation).setOnClickListener(view -> saveLocation(locationId, addressInput, latitudeInput, longitudeInput));
    }

    // update the location's information
    private void saveLocation(int locationId, EditText addressInput, EditText latitudeInput, EditText longitudeInput) {
        String address = addressInput.getText().toString();
        String latitude = latitudeInput.getText().toString();
        String longitude = longitudeInput.getText().toString();

        if (address.isEmpty() || latitude.isEmpty() || longitude.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields!", Toast.LENGTH_LONG).show();
            return;
        }

        Location location = new Location(locationId, address, latitude, longitude);
        boolean isUpdated = DBHelper.updateData(location);
        if (isUpdated) {
            Toast.makeText(this, "Location updated!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Error updating location!", Toast.LENGTH_LONG).show();
        }
    }

    // delete the location from the database
    public void deleteLocation(View v) {
        Intent i = getIntent();
        int locationId = i.getIntExtra("LOCATION_ID", -1);

        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this location?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    DBHelper.deleteLocationId(locationId);
                    Toast.makeText(this, "Location deleted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
