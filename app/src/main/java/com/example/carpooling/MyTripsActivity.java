package com.example.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class MyTripsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter2 adapter;

    private List<Trip> myTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_trips);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        User user = (User) getIntent().getSerializableExtra("user");
        myTrips = new ArrayList<>();


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Integer loggedInUser = user.getId();
        List<Integer> tripIds = dbHelper.getTripIdsByUserId(loggedInUser);
        for (Trip trip : dbHelper.getAllTripsWithDriverAndVehicle()) {
            if (tripIds.contains(trip.getId())) {
                myTrips.add(trip);
            }

        }



            recyclerView = findViewById(R.id.recyclerview);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new MyAdapter2(getApplicationContext(), myTrips, user);
            recyclerView.setAdapter(adapter);
        }
}
