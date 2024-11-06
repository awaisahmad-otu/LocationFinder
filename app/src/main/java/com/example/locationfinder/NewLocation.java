package com.example.locationfinder;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NewLocation extends AppCompatActivity {
    public DBHelper DBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location);

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
    }

    // add the new location to the database
    public void saveLocation(View v) {
        EditText addressInput = findViewById(R.id.inputAddress);
        EditText latitudeInput = findViewById(R.id.inputLatitude);
        EditText longitudeInput = findViewById(R.id.inputLongitude);

        String address = addressInput.getText().toString();
        String latitude = latitudeInput.getText().toString();
        String longitude = longitudeInput.getText().toString();

        if (address.isEmpty() || latitude.isEmpty() || longitude.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields!", Toast.LENGTH_LONG).show();
            return;
        }

        Location location = new Location(0, address, latitude, longitude);
        boolean isInserted = DBHelper.insertLocation(location);
        if (isInserted) {
            Toast.makeText(this, "Location saved!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Error saving location!", Toast.LENGTH_LONG).show();
        }
    }
}
