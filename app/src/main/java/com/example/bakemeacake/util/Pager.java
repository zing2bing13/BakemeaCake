package com.example.bakemeacake.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.bakemeacake.R;
import com.example.bakemeacake.ui.recipe.IngredientsActivity;
import com.example.bakemeacake.ui.recipe.InstructionsActivity;

public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;
    int recipeId;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount, int recipeId) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
        this.recipeId = recipeId;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                IngredientsActivity activity = new IngredientsActivity();
                activity.setRecipeID(recipeId);
                return activity;
            case 1:
                return new InstructionsActivity();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Ingredients";
            case 1:
                return "Instructions";
            default:
                return null;
        }
    }

}
