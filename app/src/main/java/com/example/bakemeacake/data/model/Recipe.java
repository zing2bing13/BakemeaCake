package com.example.bakemeacake.data.model;

public class Recipe {
    public int ID;
    public String Name;
    public int User_ID;

    public Recipe() {
        ID = 0;
        Name = "";
        User_ID = 0;
    }

    public Recipe (int id, String name, int user_id) {
        this.ID = id;
        this.Name = name;
        this.User_ID = user_id;
    }
}
