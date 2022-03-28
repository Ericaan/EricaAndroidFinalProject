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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ericafinalproject.Classes.Category;
import com.example.ericafinalproject.DB.AllDao;
import com.example.ericafinalproject.DB.MyDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddCategory extends AppCompatActivity {
    EditText categoryName;
    Button submit, cancel, imageGallery, takeImg;
    ImageView img;
    Bitmap bmpImage;
    AllDao allDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        //references
        allDao = MyDatabase.getInstance(this).allDao();
        categoryName = findViewById(R.id.et_addCat_name);
        submit = findViewById(R.id.btn_addCat_submit);
        cancel = findViewById(R.id.btn_addCat_cancel);
        imageGallery = findViewById(R.id.btn_addCat_image);
        takeImg = findViewById(R.id.btn_addCat_takeImg);
        img = findViewById(R.id.iv_addCat_image);
        bmpImage = null;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checking if the inputs are null
                if(categoryName.getText().toString().isEmpty() ||
                        img == null){
                    Toast.makeText(AddCategory.this, "Category data is missing...", Toast.LENGTH_SHORT).show();
                } else {
                    //create a new category to add category
                    Category category = new Category();
                    //set category name and img
                    category.setCategoryName(categoryName.getText().toString());
                    category.setCategoryImg(DataConverter.convertImage2ByteArray(bmpImage));
                    //using allDao to insert category
                    allDao.insertCategory(category);
                    //showing toast message -> successful
                    Toast.makeText(AddCategory.this, "Insertion successful...", Toast.LENGTH_SHORT).show();
                    //after inserting the category, user will be back in MainActivity class
                    Intent intent = new Intent(AddCategory.this, MainActivity.class);
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
                //call the method here
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
                //cancel button will bring user back to MainActivity class
                Intent intent = new Intent(AddCategory.this, MainActivity.class);
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
                            Toast.makeText(AddCategory.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        //cancelled
                        Toast.makeText(AddCategory.this, "Cancelled...", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AddCategory.this, "Cancelled...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
}