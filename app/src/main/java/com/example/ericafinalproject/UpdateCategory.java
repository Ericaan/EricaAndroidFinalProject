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

public class UpdateCategory extends AppCompatActivity {

    Button changeImg, update, cancel, takeImg;
    EditText catName;
    ImageView img;
    Long category_id;
    AllDao allDao;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);
        allDao = MyDatabase.getInstance(this).allDao();

        //get category_id from intent
        Intent intent = getIntent();
        String cat_id = intent.getStringExtra("category_id");
        category_id = Long.parseLong(cat_id);

        //references
        changeImg = findViewById(R.id.btn_upCat_image);
        update = findViewById(R.id.btn_upCat_update);
        cancel = findViewById(R.id.btn_upCat_cancel);
        catName = findViewById(R.id.et_upCat_name);
        img = findViewById(R.id.iv_upCat_image);
        takeImg = findViewById(R.id.btn_upCat_takeImg);
        bitmap = null;

        //get details about the category and showing it to ImageView and EditText
        Category category = new Category();
        category = allDao.getCatDetailsById(category_id);
        img.setImageBitmap(DataConverter.converByteArray2Image(category.getCategoryImg()));
        catName.setText(category.getCategoryName());

        changeImg.setOnClickListener(new View.OnClickListener() {
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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if inputs are empty
                if(img == null || catName.getText().toString().isEmpty()){
                    Toast.makeText(UpdateCategory.this, "Empty data", Toast.LENGTH_SHORT).show();
                }else{
                    //get all details
                    String name = catName.getText().toString();
                    byte[] image = DataConverter.convertImage2ByteArray(bitmap);
                    //update the category
                    allDao.updateCategoryName(category_id, name);
                    allDao.updateCategoryImg(category_id, image);
                    //showing toast message
                    Toast.makeText(UpdateCategory.this, "Updated selected category", Toast.LENGTH_SHORT).show();
                    //direct user back to MainActivity
                    Intent intent = new Intent(UpdateCategory.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //direct user to MainActivity
                Intent intent = new Intent(UpdateCategory.this, MainActivity.class);
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
                            bitmap = BitmapFactory.decodeStream(imageStream);
                            img.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(UpdateCategory.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        //cancelled
                        Toast.makeText(UpdateCategory.this, "Cancelled...", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(UpdateCategory.this, "Cancelled...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
}