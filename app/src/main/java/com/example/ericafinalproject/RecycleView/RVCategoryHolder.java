package com.example.ericafinalproject.RecycleView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ericafinalproject.R;

public class RVCategoryHolder extends RecyclerView.ViewHolder {
    TextView categoryName;
    ImageView categoryImg, editCategory, deleteCategory;
    CardView category;
    public RVCategoryHolder(@NonNull View itemView) {
        super(itemView);
        //references
        categoryName = itemView.findViewById(R.id.cv_category_name);
        categoryImg = itemView.findViewById(R.id.cv_category_img);
        category = itemView.findViewById(R.id.cv_category);
        editCategory = itemView.findViewById(R.id.iv_editCat);
        deleteCategory = itemView.findViewById(R.id.iv_deleteCat);
    }
}
