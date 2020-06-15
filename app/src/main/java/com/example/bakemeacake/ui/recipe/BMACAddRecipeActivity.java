package com.example.bakemeacake.ui.recipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bakemeacake.R;
import com.example.bakemeacake.Session;
import com.example.bakemeacake.data.DAO.DatabaseHandler;
import com.example.bakemeacake.data.model.LoggedInUser;
import com.example.bakemeacake.data.model.Recipe;

public class BMACAddRecipeActivity extends AppCompatActivity {

    private LoggedInUser loggedInUser = null;
    private Session session = null;
    private DatabaseHandler myDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_a_c_add_recipe);

        Button addButton = findViewById(R.id.button_add);
        Button cancelButton = findViewById(R.id.button_cancel);

        addButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe();
            }
        });
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addRecipe() {
        String ingAmount = ((EditText)findViewById(R.id.edit_ing_amount)).getText().toString();
        String ingredient = ((EditText)findViewById(R.id.edit_ingredient)).getText().toString();
        String instructions = ((EditText)findViewById(R.id.edit_instructions)).getText().toString();
        String recipeName = ((EditText)findViewById(R.id.edit_recipe_name)).getText().toString();
        myDB = new DatabaseHandler(this.getApplicationContext());
        session = new Session(this.getApplicationContext());
        loggedInUser = new LoggedInUser(session.getUserID(), session.getUsername(), "");
        Recipe recipe = new Recipe(0, recipeName, loggedInUser.getUserId());
        myDB.CreateRecipe(recipe);

    }




}
