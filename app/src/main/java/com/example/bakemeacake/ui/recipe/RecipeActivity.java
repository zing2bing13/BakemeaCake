package com.example.bakemeacake.ui.recipe;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.bakemeacake.util.DatabaseHandler;
import com.example.bakemeacake.R;
import com.example.bakemeacake.data.model.Ingredient;
import com.example.bakemeacake.data.model.Instruction;
import com.example.bakemeacake.data.model.Recipe;
import com.example.bakemeacake.util.Pager;

import java.io.Serializable;
import java.util.List;

public class RecipeActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    private Recipe recipe = null;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);


        Bundle bundle = new Bundle();
        Intent intent = getIntent();
        this.recipe = (Recipe) intent.getSerializableExtra("Recipe");

        Log.d("BMAC_debug", "The recipe id is " + Integer.toString(recipe.ID));

        //Initializing the tablayout
        tabLayout = findViewById(R.id.tab_switchPanes);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.lbl_ingredients)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.lbl_instructions)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = findViewById(R.id.tab_viewPanes);
        tabLayout.setupWithViewPager(viewPager);

        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount(), recipe.ID);

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}