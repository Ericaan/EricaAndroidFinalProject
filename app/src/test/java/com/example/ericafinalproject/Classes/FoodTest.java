package com.example.ericafinalproject.Classes;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FoodTest {
    private Food food = new Food();
    private List<Food> foodList = new ArrayList<>();

    @Test
    public void createListOfFood() {
        food.setFoodId(1L);
        food.setFoodName("Spagetti");
        food.setFoodPrice(15);
        food.setFoodDescription("The description of Spagetti");
        foodList.add(food);

        assertTrue(foodList.get(0) == food);
    }

    @Test
    public void checkFoodId() {
        food.setFoodId(1L);
        food.setFoodName("Spagetti");
        food.setFoodPrice(15);
        food.setFoodDescription("The description of Spagetti");
        foodList.add(food);

        assertTrue(foodList.get(0).foodId == food.getFoodId());
    }

    @Test
    public void checkFoodName() {
        food.setFoodId(1L);
        food.setFoodName("Spagetti");
        food.setFoodPrice(15);
        food.setFoodDescription("The description of Spagetti");
        foodList.add(food);

        assertTrue(foodList.get(0).foodName == food.getFoodName());

    }

    @Test
    public void checkFoodPrice() {
        food.setFoodId(1L);
        food.setFoodName("Spagetti");
        food.setFoodPrice(15);
        food.setFoodDescription("The description of Spagetti");
        foodList.add(food);

        assertTrue(foodList.get(0).foodPrice == food.getFoodPrice());

    }

    @Test
    public void checkFoodDescription() {
        food.setFoodId(1L);
        food.setFoodName("Spagetti");
        food.setFoodPrice(15);
        food.setFoodDescription("The description of Spagetti");
        foodList.add(food);

        assertTrue(foodList.get(0).foodDescription == food.getFoodDescription());

    }

}