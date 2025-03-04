package com.example.carpooling;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.carpooling.Models.User;

public class DriverOrPassenger extends AppCompatActivity {
    private Button driverButton, passengerButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_or_passenger);

        driverButton = findViewById(R.id.driverButton);
        passengerButton = findViewById(R.id.passengerButton);

        // Retrieve the User object passed from the LoginActivity
        User user = (User) getIntent().getSerializableExtra("user");

        // Set the click listener for the driver button
        driverButton.setOnClickListener(v -> {
            updateUserIsDriverInDatabase(user);
            Intent intent = new Intent(DriverOrPassenger.this, DriverActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });

        passengerButton.setOnClickListener(v -> {
            updateUserIsPassengerInDatabase(user);
            Intent intent = new Intent(DriverOrPassenger.this, PassengerActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
    }

    private void updateUserIsPassengerInDatabase(User user) {
        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isDriver", 0);
        db.update("User", values, "email = ?", new String[]{user.getEmail()});
        //dbHelper.updateUser(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRating(),false, user.getVehicleId());
    }

    private void updateUserIsDriverInDatabase(User user) {
        dbHelper = new DatabaseHelper(this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isDriver", 1);  // Set isDriver to 1 (true)

        // Update the database for the user
        int rowsUpdated = db.update("User", values, "email = ?", new String[]{user.getEmail()});
        if (rowsUpdated > 0) {
            Log.d("DriverOrPassenger", "User updated successfully");
        } else {
            Log.d("DriverOrPassenger", "Failed to update user");
        }
    }
}
