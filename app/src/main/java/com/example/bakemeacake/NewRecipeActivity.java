package com.example.bakemeacake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.bakemeacake.data.model.Recipe;

public class NewRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
    }

    public void onNewClick(View view) {
        Recipe recipe = new Recipe();
        recipe.Name = ((EditText)findViewById(R.id.recipeNameText)).getText().toString();

        Intent intent = getIntent();
        intent.putExtra("Recipe", recipe);
        setResult(RESULT_OK, intent);
        finish();
    }
}
