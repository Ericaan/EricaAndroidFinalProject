package com.example.ericafinalproject.Classes;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CategoryTest {
    private Category category = new Category();
    private List<Category> categoryList = new ArrayList<>();

    @Test
    public void createListOfCategory() {
        category.setCategoryId(1L);
        category.setCategoryName("Main Dish");
        categoryList.add(category);

        assertTrue(categoryList.get(0) == category);
    }

    @Test
    public void checkCategoryId() {
        category.setCategoryId(1L);
        category.setCategoryName("Main Dish");
        categoryList.add(category);
        assertTrue(categoryList.get(0).categoryId == category.getCategoryId());
    }

    @Test
    public void checkCategoryName() {
        category.setCategoryId(1L);
        category.setCategoryName("Main Dish");
        categoryList.add(category);
        assertTrue(categoryList.get(0).categoryName == category.getCategoryName());
    }
}