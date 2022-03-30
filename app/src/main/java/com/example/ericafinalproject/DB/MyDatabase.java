/*
    Creating database with tables(Category and Food classes)
 */

package com.example.ericafinalproject.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ericafinalproject.Classes.Category;
import com.example.ericafinalproject.Classes.Food;

@Database(entities = {Category.class, Food.class}, version = 3, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract AllDao allDao();
    public static MyDatabase myDatabase = null;

    public static MyDatabase getInstance(Context context){
        if(myDatabase==null){
            myDatabase = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class,
                    "finalProjectDB")
                    .allowMainThreadQueries().build();

        }
        return myDatabase;
    }

}
