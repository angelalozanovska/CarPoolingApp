package com.example.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.carpooling.DatabaseHelper;  // Make sure this is the correct path to your DatabaseHelper class


public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        usernameEditText = findViewById(R.id.registerUsername);
        emailEditText = findViewById(R.id.registerEmail);
        passwordEditText = findViewById(R.id.registerPassword);
        confirmPasswordEditText = findViewById(R.id.registerConfirmPassword);
        registerButton = findViewById(R.id.registerButton);

        // Set click listener for register button
        registerButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            // Validate inputs
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Call method to handle registration logic (e.g., save to database)
            registerUser(username, email, password);
        });
    }

    // Method to handle user registration
    private void registerUser(String username, String email, String password) {
        // Here you can either:
        // 1. Save the user to a local database
        // 2. Send the user details to a backend server for registration

        // Example for saving to a local database (replace with your own logic):
         DatabaseHelper databaseHelper = new DatabaseHelper(this);
        long userId = databaseHelper.addUser(username, email, password);
        if (userId != -1) {
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
            // Navigate to the LoginActivity after successful registration
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();  // Close the registration activity
        } else {
            Toast.makeText(this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
        }

        // After successful registration, you can navigate to another activity
        Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class)); // Navigate to login screen
        finish();  // Close the registration activity
    }
}
