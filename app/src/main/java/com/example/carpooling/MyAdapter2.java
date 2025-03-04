package com.example.carpooling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpooling.Models.Trip;
import com.example.carpooling.Models.User;

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyViewHolder2> {

    Context context;
    private List<Trip> items;
    private User loggedInUser;
    private DatabaseHelper dbHelper;

    public MyAdapter2(Context context, List<Trip> items, User loggedInUser) {
        this.context = context;
        this.items = items;
        this.loggedInUser = loggedInUser;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view2, parent, false);
        return new MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        Trip trip = items.get(position);

        holder.driver.setText(trip.getDriver().getName());
        holder.startDestination.setText(trip.getLatStart() + ", " + trip.getLonStart());
        holder.endDestination.setText(trip.getLatFinish() + ", " + trip.getLonFinish());
        holder.date.setText(trip.getStart().toString());
        holder.buttonRate.setVisibility(View.VISIBLE);
        holder.valueRating.setText(String.valueOf(trip.getDriver().getRating()));

        holder.buttonRate.setOnClickListener(v -> {
            String ratingText = holder.ratingInput.getText().toString().trim();

            if (ratingText.isEmpty()) {
                Toast.makeText(context, "Please enter a rating", Toast.LENGTH_SHORT).show();
                return;
            }

            int newRating;
            try {
                newRating = Integer.parseInt(ratingText);
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newRating < 1 || newRating > 5) {
                Toast.makeText(context, "Rating must be between 1 and 5", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseHelper dbHelper = new DatabaseHelper(context);

            int driverId = trip.getDriver().getId();
            User user = dbHelper.getUserById(driverId);
            int updatedRating;
            if (user != null) {
                // If you want to accumulate ratings:
                int currentRating = user.getRating();
                if(currentRating==0){
                    updatedRating = newRating;
                }else {
                    updatedRating = (currentRating + newRating) / 2; // Modify this logic as needed (e.g., average)
                }
                dbHelper.updateUser(user.getId(), user.getName(), user.getEmail(), user.getPassword(), updatedRating, user.getDriver(), user.getVehicleId());

                // Clear input field after updating rating
                holder.ratingInput.setText("");
                holder.valueRating.setText(String.valueOf(updatedRating));
                Toast.makeText(context, "Rating updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Driver not found", Toast.LENGTH_SHORT).show();
            }
//

        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

}
