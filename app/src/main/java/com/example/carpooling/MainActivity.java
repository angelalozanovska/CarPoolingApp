package com.example.carpooling;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declare the DatabaseHelper and SQLiteDatabase objects
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize the DatabaseHelper and get a writable database
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase(); // This will create the database if it doesn't exist
        dbHelper.deleteTrip(6);
        //dbHelper.insertExampleData();
        //dbHelper.addTrip(1,"Bitola", 41.9981,21.4254,41.0297,21.3292,10,"2024-12-08T10:00:00");


        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Intent to go to the LoginActivity
    public void gotologin(View v) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    // Intent to go to the RegisterActivity
    public void gotoregister(View v) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
