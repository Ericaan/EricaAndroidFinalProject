/*
    Category class contains category_id which will be generated manually,
    category_name, and category_img (byte[])
*/
package com.example.ericafinalproject.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "category")
public class Category {
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    Long categoryId;
    @ColumnInfo(name = "category_name")
    String categoryName;
    @ColumnInfo(name = "category_img")
    byte[] categoryImg;

    public Category(){}



    @Ignore
    public Category(String categoryName, byte[] categoryImg) {
        this.categoryName = categoryName;
        this.categoryImg = categoryImg;
    }


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public byte[] getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(byte[] categoryImg) {
        this.categoryImg = categoryImg;
    }


    //to string is used when I called category Name for spinner
    @Override
    public String toString() {
        return ""
                 + categoryName;
    }


}
