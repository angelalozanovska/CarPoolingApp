package com.example.carpooling;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MyViewHolder2 extends RecyclerView.ViewHolder {
    TextView driver;
    TextView startDestination;
    TextView endDestination;
    TextView date;
    Button buttonRate;
    EditText ratingInput;
    TextView valueRating;


    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);
        driver = itemView.findViewById(R.id.driver);
        startDestination = itemView.findViewById(R.id.startDestination);
        endDestination = itemView.findViewById(R.id.endDestination);
        date = itemView.findViewById(R.id.date);
        buttonRate = itemView.findViewById(R.id.buttonRate);
        ratingInput = itemView.findViewById(R.id.ratingInput);
        valueRating = itemView.findViewById(R.id.valueRating);
    }
}
