/*
    Add food will adding new food and display it to Food Main Activity
    User will choose a photo from gallery or take a picture (not working for me)
    User cannot leave any fields empty
 */
package com.example.ericafinalproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ericafinalproject.Classes.Category;
import com.example.ericafinalproject.Classes.Food;
import com.example.ericafinalproject.DB.AllDao;
import com.example.ericafinalproject.DB.MyDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddFood extends AppCompatActivity {
    EditText foodName, foodPrice, foodDes;
    Button submit, cancel, imageGallery, takeImg;
    ImageView img;
    Bitmap bmpImage;
    AllDao allDao;
    Spinner spinnerCategories;
    List<Category> categoryList;
    ArrayAdapter<Category> allCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        //connect dao to the database
        allDao = MyDatabase.getInstance(this).allDao();
        //references to layout
        foodName = findViewById(R.id.et_addFood_name);
        foodPrice = findViewById(R.id.et_addFood_price);
        foodDes = findViewById(R.id.et_addFood_description);
        submit = findViewById(R.id.btn_addFood_submit);
        cancel = findViewById(R.id.btn_addFood_cancel);
        imageGallery = findViewById(R.id.btn_addFood_image);
        takeImg = findViewById(R.id.btn_addFood_takeImg);
        img = findViewById(R.id.iv_addFood_image);
        spinnerCategories = findViewById(R.id.spinner_addFood_category);
        //set bitmap to null
        bmpImage = null;

        //get all categories to show them on the spinner
        categoryList = new ArrayList<Category>();
        categoryList = allDao.getAllCategoriesNames();

        //spinner
        allCategories = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                categoryList);
        allCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(allCategories);

        //for submit new food
        Food food = new Food();

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //if item is selected, it will set the category_id to the Food class
                categoryList.get(i).getCategoryId();
                //showing the category_id of the spinner's item
                Toast.makeText(AddFood.this, "Category ID: "+ categoryList.get(i).getCategoryId(),
                        Toast.LENGTH_SHORT).show();
                food.setCategoryId(categoryList.get(i).getCategoryId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if the inputs are null/empty
                if(foodName.getText().toString().isEmpty() ||
                        foodPrice.getText().toString().isEmpty() ||
                        foodDes.getText().toString().isEmpty() ||
                        img == null){
                    Toast.makeText(AddFood.this, "Food Data is missing...", Toast.LENGTH_SHORT).show();
                } else {
                    //set all details/inputs to food (Food)
                    food.setFoodName(foodName.getText().toString());
                    food.setFoodDescription(foodDes.getText().toString());
                    food.setFoodPrice(Integer.parseInt(foodPrice.getText().toString()));
                    food.setFoodImg(DataConverter.convertImage2ByteArray(bmpImage));
                    //using allDao to insert the food
                    allDao.insertFood(food);
                    //showing successful insertion with toast message
                    Toast.makeText(AddFood.this, "Insertion successful...", Toast.LENGTH_SHORT).show();
                    //user will be directed back to MainActivity
                    Intent intent = new Intent(AddFood.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        imageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent to pick image from gallery
                Intent intent = new Intent(Intent.ACTION_PICK);
                //set type
                intent.setType("image/*");
                galleryActivityResultLauncher.launch(intent);
            }
        });

        takeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageInt = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if(imageInt.resolveActivity(getPackageManager()) !=null){
                    Toast.makeText(
                            getApplicationContext(),
                            "Success",
                            Toast.LENGTH_LONG
                    ).show();
                    cameraActivityResultLauncher.launch(imageInt);
                }
                else
                    Toast.makeText(
                            getApplicationContext(),
                            "Error opening camera",
                            Toast.LENGTH_LONG
                    ).show();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //the page will bring user back to FoodMain class
                Intent intent = new Intent(AddFood.this, FoodMain.class);
                startActivity(intent);
            }
        });
    }

    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        //image picked
                        Intent data = result.getData();
                        try {
                            //get uri of image
                            final Uri imageUri = data.getData();
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            bmpImage = BitmapFactory.decodeStream(imageStream);
                            img.setImageBitmap(bmpImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(AddFood.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        //cancelled
                        Toast.makeText(AddFood.this, "Cancelled...", Toast.LENGTH_SHORT).show();
                    }
                }
            }

    );

    private ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        //take picture
                        Intent data = result.getData();
                        Bundle extras = data.getExtras();
                        Bitmap imgBM = (Bitmap)extras.get("data");
                        img.setImageBitmap(imgBM);
                    }
                    else {
                        //cancelled
                        Toast.makeText(AddFood.this, "Cancelled...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

}