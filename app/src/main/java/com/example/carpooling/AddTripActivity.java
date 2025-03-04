package com.example.carpooling;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carpooling.Models.User;
import com.example.carpooling.Models.Trip;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddTripActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private LatLng startLocation;
    private LatLng endLocation;
    private EditText description;
    private EditText price;
    private Button saveTripButton;
    private DatabaseHelper dbHelper;
    private TextView chosenTime;
    private Button chooseButton;

    private int selectedYear, selectedMonth, selectedDay;
    private int selectedHour, selectedMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_trip);

        // Setting up system bar insets to handle the layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize views
        description = findViewById(R.id.editTextDescription);
        price = findViewById(R.id.editTextPrice);
        saveTripButton = findViewById(R.id.buttonSaveTrip);
        dbHelper = new DatabaseHelper(this);

        chosenTime = findViewById(R.id.textViewChosenDateTime);
        chooseButton = findViewById(R.id.buttonChooseDateTime);
        // Get the user passed from the previous activity
        User user = (User) getIntent().getSerializableExtra("user");

        chooseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openDialog();
            }
        }
        );

        // Set up save button listener
        saveTripButton.setOnClickListener(v -> {
            // Get values from input fields
            String tripDescription = description.getText().toString();
            String priceString = price.getText().toString();
            double tripPrice = 0.0;

            try {
                tripPrice = Double.parseDouble(priceString);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price", Toast.LENGTH_SHORT).show();
                return;
            }

            if (startLocation != null && endLocation != null && !tripDescription.isEmpty()) {
                Trip newTrip = new Trip();
                dbHelper.addTrip(user.getId(),tripDescription,startLocation.latitude, startLocation.longitude, endLocation.latitude, endLocation.longitude, tripPrice, chosenTime.getText().toString());

                Intent intent = new Intent(AddTripActivity.this, DriverTrips.class);
                intent.putExtra("user", user);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please fill all the fields and select locations.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void openDialog(){
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            selectedYear = year;
            selectedMonth = month+1;
            selectedDay = day;

            openDialog2();
            }
        },2024,12,18);
        dialog.show();
    }
    private void openDialog2(){
        TimePickerDialog dialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                selectedHour = hour;
                selectedMinute = minute;
                // Concatenate and display the full date-time result
                String result = selectedYear + "-"
                        + String.format("%02d", selectedMonth + 1) + "-"
                        + String.format("%02d", selectedDay) + "T"
                        + String.format("%02d:%02d:%02d", selectedHour, selectedMinute, 0);

                chosenTime.setText(result);
            }
        },15,00,true);
        dialog.show();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        // Clear any existing markers and set the camera to a default location (e.g., centered on the user's current location or map)
        myMap.clear();
        LatLng macedoniaCenter = new LatLng(41.6086, 21.7453);
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(macedoniaCenter, 7));
        myMap.setOnMapClickListener(latLng -> {
            if (startLocation == null) {
                // First click: set start location
                startLocation = latLng;
                myMap.addMarker(new MarkerOptions().position(startLocation).title("Start Location"));
            } else if (endLocation == null) {
                // Second click: set end location
                endLocation = latLng;
                myMap.addMarker(new MarkerOptions().position(endLocation).title("End Location"));
            } else {
                // If both locations are already selected, show a message
                Toast.makeText(this, "Start and End locations are already selected.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
