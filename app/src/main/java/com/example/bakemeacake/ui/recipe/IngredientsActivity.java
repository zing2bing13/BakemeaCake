package com.example.bakemeacake.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.bakemeacake.R;
import com.example.bakemeacake.data.IngredientAdapter;
import com.example.bakemeacake.data.model.Ingredient;
import com.example.bakemeacake.data.model.Recipe;
import com.example.bakemeacake.util.DatabaseHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.getIntent;

public class IngredientsActivity extends Fragment {

    private Recipe recipe = new Recipe();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter ingredientAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseHandler dbHandler = null;
    private ArrayList<Ingredient> ingredients;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.dbHandler = new DatabaseHandler(this.getContext());

        this.ingredients = (ArrayList) dbHandler.GetIngredients(recipe.ID);


        View ingredientView = inflater.inflate(R.layout.content_ingredients, container, false);
        recyclerView = ingredientView.findViewById(R.id.recycler_ingredients);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ingredientAdapter = new IngredientAdapter(ingredients);
        recyclerView.setAdapter(ingredientAdapter);
        Button addButton = ingredientView.findViewById(R.id.button_add_ingredient);

        addButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                dbHandler = new DatabaseHandler(v.getContext());
                EditText ingredientText = getView().findViewById(R.id.editIngredient);
                String ingredientName = ingredientText.getText().toString();
                Ingredient ingredient = new Ingredient();
                ingredient.Ingredient = ingredientName;
                ingredient.Recipe_ID = recipe.ID;
                dbHandler.CreateIngredient(ingredient);
                dbHandler.close();
                ingredientAdapter.notifyDataSetChanged();
            }
        });

            return ingredientView;
            //Returning the layout file after inflating
            //Change R.layout.tab1 in you classes
          //  return inflater.inflate(R.layout.content_ingredients, container, false);

    }

    public void setRecipeID(int recipeId){
        recipe.ID = recipeId;
    }
}
