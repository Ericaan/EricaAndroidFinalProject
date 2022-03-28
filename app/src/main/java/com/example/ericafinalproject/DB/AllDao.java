package com.example.ericafinalproject.DB;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ericafinalproject.Classes.Category;
import com.example.ericafinalproject.Classes.CategoryAndFood;
import com.example.ericafinalproject.Classes.CategoryWithFood;
import com.example.ericafinalproject.Classes.Food;

import java.util.List;

@Dao
public interface AllDao {
    //insert category
    @Insert
    void insertCategory(Category category);
    //insert food
    @Insert
    void insertFood(Food food);
    //get all categories
    @Query("SELECT * FROM category")
    List<Category> getAllCategories();

    @Query("SELECT category_name, category_id FROM category")
    List<Category> getAllCategoriesNames();

    @Query("SELECT * FROM food WHERE food_id=:food_id")
    Food getFoodDetailsById (long food_id);

    @Query("SELECT * FROM category WHERE category_id=:category_id")
    Category getCatDetailsById (long category_id);



    //get all foods
    @Query("SELECT * FROM food")
    List<Food> getAllFood();
    //delete all categories
    @Query("DELETE FROM category ")
    void deleteAllCategories();
    //delete all foods
    @Query("DELETE FROM food ")
    void deleteAllFood();

    @Query("UPDATE food set food_name = :food_name Where food_id =:food_id")
    void updateFoodName(long food_id, String food_name);

    @Query("UPDATE food set food_price=:food_price WHERE food_id=:food_id")
    void updateFoodPrice(long food_id, int food_price);

    @Query("UPDATE food set food_description=:food_des WHERE food_id=:food_id")
    void updateFoodDes(long food_id, String food_des);

    @Query("UPDATE food set food_img=:food_img WHERE food_id=:food_id")
    void updateFoodImg(long food_id, byte[]food_img);

    @Query("UPDATE category set category_name=:category_name WHERE category_id=:category_id")
    void updateCategoryName(long category_id, String category_name);

    @Query("UPDATE category set category_img=:category_img WHERE category_id=:category_id")
    void updateCategoryImg(long category_id, byte[] category_img);



    @Query("DELETE FROM food WHERE food_id=:food_id")
    void deleteFoodById(long food_id);

    @Query("DELETE FROM category WHERE category_id=:category_id")
    void deleteCategoryById(long category_id);

    @Query("SELECT * FROM category WHERE category_id = :category_id ")
    Category getCategoryById(int category_id);
    @Query("SELECT * FROM food WHERE food_id = :food_id")
    Food getFoodById(long food_id);
    @Query("SELECT * FROM food JOIN category ON category.category_id = food.category_id ")
    List<CategoryAndFood> getCategoryAndFood();
    @Query("SELECT * FROM food")
    List<CategoryWithFood> getCategoryWithFood();

    @Query("SELECT * FROM food WHERE category_id=:category_id")
    List<Food> getFoodByCategoryId(long category_id);

    @Query("SELECT * FROM food WHERE food_id=:food_id")
    List<Food> getFoodDetailsByFoodId(long food_id);
}
