package com.example.carpooling;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carpooling.Models.User;
import com.example.carpooling.Models.Vehicle;

public class DriverActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView firstTextView;
    private TextView secondTextView;
    private EditText brand, model;
    private Button addVehicle;
    private ImageView arrowImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        brand = findViewById(R.id.vehicleBrand);
        model = findViewById(R.id.vehicleModel);

        firstTextView = findViewById(R.id.firstTextView);
        secondTextView = findViewById(R.id.secondTextView);
        arrowImage = findViewById(R.id.arrowImage);


        dbHelper = new DatabaseHelper(this);


        addVehicle = findViewById(R.id.addVehicleButton);
        dbHelper = new DatabaseHelper(this);

        User user = (User) getIntent().getSerializableExtra("user");
        Integer vid = user.getVehicleId();
        if(vid==-1){
            firstTextView.setText("Не е пронајдено возило");
            secondTextView.setText("");
        }
        else {
            Vehicle vehicle = dbHelper.getVehicleById(vid);
            if (vehicle != null) {
                firstTextView.setText("Бренд: " + vehicle.getBrand());
                secondTextView.setText("Модел: " + vehicle.getModel());
            } else {
                firstTextView.setText("Не е пронајдено возило");
                secondTextView.setText("");
            }
        }

        arrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverActivity.this, DriverTrips.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });


        addVehicle.setOnClickListener(v -> {
            String vehicleBrand = brand.getText().toString().trim();
            String vehicleModel = model.getText().toString().trim();

            // Check if the fields are not empty
            if (!vehicleBrand.isEmpty() && !vehicleModel.isEmpty()) {
                // Add the vehicle and get the vehicleId
                int vehicleId = dbHelper.addVehicle(vehicleBrand, vehicleModel);

                // Check if the vehicle was added successfully
                if (vehicleId != -1) {
                    // Update the user's vehicle ID
                    dbHelper.updateUser(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                            user.getRating(), user.getDriver(), vehicleId);

                    // Update UI with the new vehicle details
                    firstTextView.setText("Бренд: " + vehicleBrand);
                    secondTextView.setText("Модел: " + vehicleModel);

                    // Show success toast
                    Toast.makeText(DriverActivity.this, "Возилото е додадено", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DriverActivity.this, "Грешка.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Show error toast if fields are empty
                Toast.makeText(DriverActivity.this, "Пополни ги другите полиња.", Toast.LENGTH_SHORT).show();
            }
        });


    }

}