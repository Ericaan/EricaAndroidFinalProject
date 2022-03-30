/*
    Showing food list in Food Main Activity
    Click Listener for:
    - food name = to direct user to the Detail Food
 */
package com.example.ericafinalproject.RecycleView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ericafinalproject.Classes.Food;
import com.example.ericafinalproject.DataConverter;
import com.example.ericafinalproject.DetailMain;
import com.example.ericafinalproject.R;

import java.util.List;

public class RVFoodAdapter extends RecyclerView.Adapter<RVFoodHolder> {
    List<Food> foods;
    public RVFoodAdapter(List<Food> foods) {
        this.foods = foods;
    }

    public void setFoodList(List<Food> foodList){
        this.foods = foodList ;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RVFoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_food, parent, false);

        return new RVFoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVFoodHolder holder, @SuppressLint("RecyclerView") int position) {
        //getting and showing details of all food in adapter
        holder.foodName.setText(foods.get(position).getFoodName());
        holder.foodImg.setImageBitmap(
                DataConverter.converByteArray2Image(
                        foods.get(position).getFoodImg()));
        //this will bring user to DetailMain to see the details of specific food using food_id
        holder.food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailMain.class);
                intent.putExtra("food_id", foods.get(position).getFoodId().toString());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }
}
