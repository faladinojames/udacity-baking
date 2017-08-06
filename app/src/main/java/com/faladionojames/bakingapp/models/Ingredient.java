package com.faladionojames.bakingapp.models;

/**
 * Created by jamesfalade on 05/08/2017.
 */

public class Ingredient {
    private String measure,ingredient;
    private int quantity;
    public Ingredient(String measure, String ingredient, int  quantity)
    {
        this.measure=measure;
        this.ingredient=ingredient;
        this.quantity=quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }


    public int getQuantity() {
        return quantity;
    }

}
