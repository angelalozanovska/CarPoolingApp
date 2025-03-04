package com.example.carpooling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carpooling.Models.User;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private DatabaseHelper dbHelper; // Your SQLite helper class for database interaction

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        emailEditText = findViewById(R.id.loginEmail);
        passwordEditText = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.login);

        // Initialize the database helper
        dbHelper = new DatabaseHelper(this);

        // Set padding for the main view to handle system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Login button click listener
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Validate login credentials
            User user = validateLogin(email, password);

            if (user != null) {
                Intent intent = new Intent(LoginActivity.this, DriverOrPassenger.class);
                intent.putExtra("user", user);  // Pass the User object if needed
                startActivity(intent);
                finish();  // Close LoginActivity
            } else {
                // Invalid credentials
                Log.d("Login", "User not found or invalid credentials.");
                Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

    }

  private User validateLogin(String email, String password) {
      if (email.isEmpty() || password.isEmpty()) {
          return null;
      }
      User user = dbHelper.getUser(email, password);
      return user;
  }


}
