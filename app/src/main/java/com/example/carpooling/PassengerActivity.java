package com.example.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpooling.Models.Trip;
import com.example.carpooling.Models.User;

import java.util.ArrayList;
import java.util.List;

public class PassengerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    private List<Trip> driverTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_passenger);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Get the user from intent
        User user = (User) getIntent().getSerializableExtra("user");

        // Initialize the list of trips
        driverTrips = new ArrayList<>();

        // Create DatabaseHelper instance
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        //Integer loggedInDriverId = user.getId();

        // Add trips for the logged-in driver to the list
        for (Trip trip : dbHelper.getAllTripsWithDriverAndVehicle()) {
            driverTrips.add(trip);
        }
        Button buttonMyTrips = findViewById(R.id.buttonMyTrips);

        // Set click listener to navigate to a new activity
        buttonMyTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PassengerActivity.this, MyTripsActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter = new MyAdapter(getApplicationContext(), driverTrips, user);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    }
