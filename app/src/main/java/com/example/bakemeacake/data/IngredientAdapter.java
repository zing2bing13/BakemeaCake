package com.example.bakemeacake.data;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakemeacake.R;
import com.example.bakemeacake.data.model.Ingredient;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {
    private ArrayList<Ingredient> ingredients;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ingTextView;
        public MyViewHolder(TextView v) {
            super(v);
            ingTextView = v;
        }
    }

    public IngredientAdapter(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public IngredientAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate((R.layout.content_ingredients), parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.ingTextView.setText(ingredients.get(position).Ingredient);
    }

    public int getItemCount(){
        return ingredients.size();
    }
}
