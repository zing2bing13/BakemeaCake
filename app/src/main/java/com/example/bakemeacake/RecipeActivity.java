package com.example.bakemeacake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bakemeacake.data.model.Ingredient;
import com.example.bakemeacake.data.model.Instruction;
import com.example.bakemeacake.data.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    private Recipe recipe = null;
    private List<Ingredient> ingredients = null;
    private List<Instruction> instructions = null;
    private DatabaseHandler dbHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        this.dbHandler = new DatabaseHandler(this);

        Intent intent = getIntent();
        this.recipe = (Recipe) intent.getSerializableExtra("Recipe");
        this.ingredients = dbHandler.GetIngredients(recipe.ID);
        this.instructions = dbHandler.GetInstructions(recipe.ID);
    }
}
