package com.example.bakemeacake.ui.recipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.bakemeacake.R;
import com.example.bakemeacake.data.IngredientAdapter;
import com.example.bakemeacake.data.model.Ingredient;
import com.example.bakemeacake.util.DatabaseHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class IngredientsActivity extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter ingredientAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Ingredient> ingredients;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            ingredients = (ArrayList<Ingredient>) bundle.getSerializable("valuesIngredients");
            View rootView = inflater.inflate(R.layout.content_ingredients, container, false);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_ingredients);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            ingredientAdapter = new IngredientAdapter(ingredients);
            recyclerView.setAdapter(ingredientAdapter);
            return rootView;
        } else {
            //Returning the layout file after inflating
            //Change R.layout.tab1 in you classes
            return inflater.inflate(R.layout.content_ingredients, container, false);
        }
    }
}
