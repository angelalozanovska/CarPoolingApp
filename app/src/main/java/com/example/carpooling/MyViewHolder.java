package com.example.carpooling;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView driver;
    TextView startDestination;
    TextView endDestination;
    TextView date;
    Button buttonJoin;
    Button buttonRate;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        driver = itemView.findViewById(R.id.driver);
        startDestination = itemView.findViewById(R.id.startDestination);
        endDestination = itemView.findViewById(R.id.endDestination);
        date = itemView.findViewById(R.id.date);
        buttonJoin = itemView.findViewById(R.id.buttonJoin);
        buttonRate = itemView.findViewById(R.id.buttonJoin);
    }
}
