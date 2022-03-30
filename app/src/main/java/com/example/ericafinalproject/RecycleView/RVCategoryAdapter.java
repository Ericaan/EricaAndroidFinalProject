/*
    Showing category list, edit button, and delete button in Main Activity
    Click Listener for:
    - category name = to direct user to the List Item
    - edit = Edit Category Activity
    - delete = delete the category
 */

package com.example.ericafinalproject.RecycleView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ericafinalproject.Classes.Category;
import com.example.ericafinalproject.DB.AllDao;
import com.example.ericafinalproject.DB.MyDatabase;
import com.example.ericafinalproject.DataConverter;
import com.example.ericafinalproject.FoodMain;
import com.example.ericafinalproject.MainActivity;
import com.example.ericafinalproject.R;
import com.example.ericafinalproject.UpdateCategory;

import java.util.List;

public class RVCategoryAdapter extends RecyclerView.Adapter<RVCategoryHolder> {
    List<Category> categoryList;
    AllDao allDao;

    public RVCategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public void setCategoryList(List<Category> categoryList){
        this.categoryList = categoryList ;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RVCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_category, parent, false);

        return new RVCategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVCategoryHolder holder, @SuppressLint("RecyclerView") int position) {
        //getting and showing details of all categories in adapter
        holder.categoryName.setText(categoryList.get(position).getCategoryName());
        holder.categoryImg.setImageBitmap(
                DataConverter.converByteArray2Image(
                        categoryList.get(position).getCategoryImg()));

        //user will be directed to FoodMain with specific category_id
        holder.categoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FoodMain.class);
                //bring the category_id to the next intent
                intent.putExtra("category_id", categoryList.get(position).getCategoryId().toString());
                view.getContext().startActivity(intent);
            }
        });

        //user will be directed to UpdateCategory with their category_id
        holder.editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpdateCategory.class);
                intent.putExtra("category_id", categoryList.get(position).getCategoryId().toString());
                view.getContext().startActivity(intent);

            }
        });

        //this will delete the specific category with their category_id
        holder.deleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                builder.setMessage("CONFIRM THE DELETION");
                // Add the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        allDao = MyDatabase.getInstance(view.getContext()).allDao();
                        allDao.deleteCategoryById(categoryList.get(position).getCategoryId());
                        Intent intent1 = new Intent(view.getContext(), MainActivity.class);
                        view.getContext().startActivity(intent1);
                        Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
