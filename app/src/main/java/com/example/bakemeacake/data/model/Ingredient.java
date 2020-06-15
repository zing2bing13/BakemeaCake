package com.example.bakemeacake.data.model;

public class Ingredient {
    public int ID;
    public String Name;
    public String Amount;
    public int Recipe_ID;

    public Ingredient() {
        this.ID = 0;
        this.Name = "";
        this.Amount = "";
        this.Recipe_ID = 0;
    }

    public Ingredient(int id, String name, String amount, int recipe_id){
        this.ID = id;
        this.Name = name;
        this.Amount = amount;
        this.Recipe_ID = recipe_id;
    }
}
