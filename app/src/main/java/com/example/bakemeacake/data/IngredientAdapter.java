package com.example.bakemeacake.data;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakemeacake.R;
import com.example.bakemeacake.data.model.Ingredient;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private ArrayList<Ingredient> ingredients;

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        private TextView ingTextView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            this.ingTextView = itemView.findViewById(R.id.ingTextView);
        }
    }

    public IngredientAdapter(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate((R.layout.frame_item_textview), parent, false);
        IngredientViewHolder vh = new IngredientViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.ingTextView.setText(ingredients.get(position).Ingredient);
    }

    public int getItemCount(){
        return ingredients.size();
    }
}
