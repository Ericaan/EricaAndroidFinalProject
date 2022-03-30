/*
    Main Activity will show the list of categories
    and display it to Food Main Activity
    User can click to a category to go to Food Main Activity
 */
package com.example.ericafinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ericafinalproject.Classes.Category;
import com.example.ericafinalproject.Classes.CategoryWithFood;
import com.example.ericafinalproject.DB.AllDao;
import com.example.ericafinalproject.DB.MyDatabase;
import com.example.ericafinalproject.RecycleView.RVCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AllDao allDao;
    RecyclerView rv;
    List<Category> categoryList;
    Button addCategory, addFood;
    RVCategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv_category);
        //showing all the categories
        categoryList = new ArrayList<Category>();
        allDao = MyDatabase.getInstance(this).allDao();
        categoryList = allDao.getAllCategories();

        //set adapter to recycle view
        adapter = new RVCategoryAdapter(categoryList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        //references
        addCategory = findViewById(R.id.btn_AddCategory);
        addFood = findViewById(R.id.btn_AddFood);

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //direct user to AddCategory
                Intent intent = new Intent(MainActivity.this, AddCategory.class);
                startActivity(intent);
            }
        });


        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //direct user to AddFood
                Intent intent = new Intent(MainActivity.this, AddFood.class);
                startActivity(intent);
            }
        });

        //printing the category with food
        List<CategoryWithFood> catWithFood = allDao.getCategoryWithFood();
        for(CategoryWithFood ssc: catWithFood) {
            Log.d("FOODINFO","Food Name = " + ssc.food.getFoodName() +
                    "\n\t ID=" + ssc.food.getFoodId() + " Category ID=" + ssc.food.getCategoryId() +
                    "\n\t\t Category Name = " + ssc.categoryList.get(0).getCategoryName()
            );
        }

    }
}