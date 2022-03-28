package com.example.ericafinalproject.RecycleView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ericafinalproject.R;

public class RVFoodHolder extends RecyclerView.ViewHolder {
    TextView foodName;
    ImageView foodImg;
    CardView food;
    public RVFoodHolder(@NonNull View itemView) {
        super(itemView);
        //reference
        food = itemView.findViewById(R.id.cv_food);
        foodName = itemView.findViewById(R.id.cv_food_name);
        foodImg = itemView.findViewById(R.id.cv_food_img);
    }
}
