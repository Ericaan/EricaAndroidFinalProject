package com.example.ericafinalproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ericafinalproject.Classes.Food;
import com.example.ericafinalproject.DB.AllDao;
import com.example.ericafinalproject.DB.MyDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class DetailMain extends AppCompatActivity {
    TextView foodId;
    AllDao allDao;
    EditText foodName, foodPrice, foodDes;
    Bitmap bitmap;
    Long food_id;
    ImageView img;
    Button update, delete, changeImg, takeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_main);

        //get food_id from previous intent
        foodId = findViewById(R.id.tv_detailMain_foodId);
        Intent intent = getIntent();
        String foodid = intent.getStringExtra("food_id");
        food_id = Long.parseLong(foodid);

        allDao = MyDatabase.getInstance(this).allDao();

        //references
        img = findViewById(R.id.iv_detail_changeImg);
        foodName = findViewById(R.id.et_detail_foodName);
        foodPrice = findViewById(R.id.et_detail_foodPrice);
        foodDes = findViewById(R.id.et_detail_foodDes);
        changeImg = findViewById(R.id.btn_detail_changeImg);
        update = findViewById(R.id.btn_detail_update);
        delete = findViewById(R.id.btn_detail_delete);
        takeImg = findViewById(R.id.btn_detail_takeImg);
        bitmap = null;

        //called food by their id to show their details
        //and showing them to the editText and imageView
        Food food= new Food();
        food = allDao.getFoodDetailsById(food_id);
        img.setImageBitmap(DataConverter.converByteArray2Image(food.getFoodImg()));
        foodName.setText(food.getFoodName());
        foodPrice.setText(""+food.getFoodPrice());
        foodDes.setText(food.getFoodDescription());

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
                //check if the inputs are empty
                if(foodName.getText().toString().isEmpty()||
                foodPrice.getText().toString().isEmpty()||
                foodDes.getText().toString().isEmpty()||
                img == null){
                    Toast.makeText(DetailMain.this, "Empty...", Toast.LENGTH_SHORT).show();
                }else {
                    //get all the details
                    String name = foodName.getText().toString();
                    int price = Integer.parseInt(foodPrice.getText().toString());
                    String des = foodDes.getText().toString();
                    byte[] image = DataConverter.convertImage2ByteArray(bitmap);
                    //update the details using dao
                    allDao.updateFoodName(food_id, name);
                    allDao.updateFoodPrice(food_id, price);
                    allDao.updateFoodDes(food_id, des);
                    allDao.updateFoodImg(food_id, image);
                    //showing toast message to show user it is successfully updated
                    Toast.makeText(DetailMain.this, "Updated food details...", Toast.LENGTH_SHORT).show();
                    //bring user back to MainActivity class
                    Intent intent = new Intent(DetailMain.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                builder.setMessage("CONFIRM THE DELETION");
                // Add the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //using all dao to delete food by their id
                        allDao.deleteFoodById(food_id);
                        //showing toast message
                        Toast.makeText(DetailMain.this, "Deletion successful...", Toast.LENGTH_SHORT).show();
                        //bring user back to MainActivity
                        Intent intent = new Intent(DetailMain.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        Toast.makeText(view.getContext(), "Cancelling...", Toast.LENGTH_SHORT).show();
                    }
                });

                // Create the AlertDialog
                builder.show();



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
                            Toast.makeText(DetailMain.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        //cancelled
                        Toast.makeText(DetailMain.this, "Cancelled...", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(DetailMain.this, "Cancelled...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
}