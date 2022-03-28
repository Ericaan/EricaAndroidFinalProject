/*
    Food class contains foreignkey which is category_id,
    when one of the category is deleted (contains food), the food will also be deleted,
    other than category_id, it contains food_id(primary key), food_name, food_description,
    food_price, and food_img
 */

package com.example.ericafinalproject.Classes;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "food", foreignKeys = {
                @ForeignKey(
                        onDelete = CASCADE,
                        onUpdate = CASCADE,
                        entity = Category.class,
                        parentColumns = {"category_id"},
                        childColumns = {"category_id"}

                )
}
)
public class Food {
    @PrimaryKey
    @ColumnInfo(name = "food_id")
    Long foodId;
    @ColumnInfo(name = "category_id", index = true)
    Long categoryId;
    @ColumnInfo(name = "food_name")
    String foodName;
    @ColumnInfo(name = "food_price")
    int foodPrice;
    @ColumnInfo(name = "food_description")
    String foodDescription;
    @ColumnInfo(name = "food_img")
    byte[] foodImg;


    public Food(){}


    @Ignore
    public Food(long categoryId, String foodName, int foodPrice, String foodDescription, byte[] foodImg) {
        this.categoryId = categoryId;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodDescription = foodDescription;
        this.foodImg = foodImg;
    }



    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public byte[] getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(byte[] foodImg) {
        this.foodImg = foodImg;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodId=" + foodId +
                ", categoryId=" + categoryId +
                ", foodName='" + foodName + '\'' +
                ", foodPrice=" + foodPrice +
                ", foodDescription='" + foodDescription + '\'' +
                ", foodImg=" + Arrays.toString(foodImg) +
                '}';
    }
}
