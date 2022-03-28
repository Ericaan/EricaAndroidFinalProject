package com.example.ericafinalproject.Classes;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class CategoryWithFood {
    @Embedded
    public Food food;

    @Relation(entity = Category.class, parentColumn = "category_id", entityColumn = "category_id")
    public List<Category> categoryList;
}
