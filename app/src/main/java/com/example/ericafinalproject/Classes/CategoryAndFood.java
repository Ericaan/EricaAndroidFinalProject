package com.example.ericafinalproject.Classes;

import androidx.room.Embedded;

public class CategoryAndFood {

    @Embedded
    public Food food;
    public String category_name;
}
