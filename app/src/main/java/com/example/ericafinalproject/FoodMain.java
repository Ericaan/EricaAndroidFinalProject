package com.example.ericafinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.ericafinalproject.Classes.Food;
import com.example.ericafinalproject.DB.AllDao;
import com.example.ericafinalproject.DB.MyDatabase;
import com.example.ericafinalproject.RecycleView.RVFoodAdapter;

import java.util.ArrayList;
import java.util.List;

public class FoodMain extends AppCompatActivity {
    AllDao allDao;
    RecyclerView rv;
    List<Food> foods;
    RVFoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_main);
        rv = findViewById(R.id.rv_food);
        //get the category_id from previous intent
        Intent intent = getIntent();
        String cat_id = intent.getStringExtra("category_id");
        Long category_id = Long.parseLong(cat_id);
        //categoryId.setText("Category ID: "+category_id);

        //showing all foods by category_id
        foods = new ArrayList<Food>();
        allDao = MyDatabase.getInstance(this).allDao();
        foods = allDao.getFoodByCategoryId(category_id);

        //set the adapter to recycle view
        adapter = new RVFoodAdapter(foods);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }
}