package com.example.bakemeacake.data.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Recipe implements Serializable {
    public int ID;
    public String Name;
    public int User_ID;

    public Recipe() { }

    public Recipe(int id, String name, int user_id)
    {
        this.ID = id;
        this.Name = name;
        this.User_ID = user_id;
    }

    @Override
    public String toString() {
        return this.Name;
    }
}
