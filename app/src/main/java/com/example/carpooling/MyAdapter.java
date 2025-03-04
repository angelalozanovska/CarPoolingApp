package com.example.carpooling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpooling.Models.Trip;
import com.example.carpooling.Models.User;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    private List<Trip> items;
    private User loggedInUser;
    private DatabaseHelper dbHelper;

    public MyAdapter(Context context, List<Trip> items, User loggedInUser) {
        this.context = context;
        this.items = items;
        this.loggedInUser = loggedInUser;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Trip trip = items.get(position);

        holder.driver.setText(trip.getDriver().getName());
        holder.startDestination.setText(trip.getLatStart() + ", " + trip.getLonStart());
        holder.endDestination.setText(trip.getLatFinish() + ", " + trip.getLonFinish());
        holder.date.setText(trip.getStart().toString());

        if (trip.getDriver().getId().equals(loggedInUser.getId())) {
            holder.buttonJoin.setVisibility(View.GONE);
        } else {
            if (dbHelper.isUserAttendee(loggedInUser.getId(), trip.getId())) {
                holder.buttonJoin.setVisibility(View.GONE);  // Hide the button if the user is an attendee
            } else {
                holder.buttonJoin.setVisibility(View.VISIBLE);  // Show the button if the user is not an attendee
            }
        }

        holder.buttonJoin.setOnClickListener(v -> {
            dbHelper.addAttendee(loggedInUser.getId(), trip.getId());
            holder.buttonJoin.setVisibility(View.GONE);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
