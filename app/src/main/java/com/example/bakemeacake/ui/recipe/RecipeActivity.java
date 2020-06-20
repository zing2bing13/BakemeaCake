package com.example.bakemeacake.ui.recipe;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.bakemeacake.R;
import com.example.bakemeacake.data.model.Ingredient;
import com.example.bakemeacake.data.model.Instruction;
import com.example.bakemeacake.data.model.Recipe;
import com.example.bakemeacake.util.DatabaseHandler;
import com.example.bakemeacake.util.Pager;
import com.example.bakemeacake.util.PrintHandler;
import com.example.bakemeacake.util.ShareHandler;

import java.util.ArrayList;
import java.util.Iterator;

public class RecipeActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    private String shareText = null;
    private Recipe recipe = null;
    private DatabaseHandler dbHandler = null;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        this.dbHandler = new DatabaseHandler(this);

        Button printButton = findViewById(R.id.button_print);
        Button shareButton = findViewById(R.id.button_share);

        Intent intent = getIntent();
        this.recipe = (Recipe) intent.getSerializableExtra("Recipe");

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

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print(v);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(v);
            }
        });
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

    private void print(View v){
    ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) dbHandler.GetIngredients(recipe.ID);
    String ingredients = "";
    for (Ingredient ing : ingredientList) {
        ingredients += ing.Ingredient + "\r\n";
    }
    shareText = ingredients;
    shareText += "\r\n\r\n\r\n";
    ArrayList<Instruction> instructionList = (ArrayList<Instruction>) dbHandler.GetInstructions(recipe.ID);
    for (Instruction ins : instructionList) {
        shareText += ins.Instruction + "\r\n";
        }
    printHTML();
    }

    private void share(View v){
        ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) dbHandler.GetIngredients(recipe.ID);
        String ingredients = "";
        for (Ingredient ing : ingredientList) {
            ingredients += ing.Ingredient + "\r\n";
        }
        shareText = ingredients;
        shareText += "\r\n\r\n\r\n";
        ArrayList<Instruction> instructionList = (ArrayList<Instruction>) dbHandler.GetInstructions(recipe.ID);
        for (Instruction ins : instructionList) {
            shareText += ins.Instruction + "\r\n";
        }
        shareText();
    }

    private void shareText() {
        //shareText = df_TextView.getText().toString();
        //shareText = null;

        if (shareText != null) {
            ShareHandler shareIntent = new ShareHandler(this);
            //shareIntent.shareText(shareText);
            //shareIntent.shareHTML(null, "Cake Recipe", shareText);
            shareIntent.shareFile(shareText);
        }
    }

    private void printHTML() {

        try {
            //shareText = df_TextView.getText().toString();
            //shareText = null;
            String htmlEncodedString = TextUtils.htmlEncode(shareText);
            PrintHandler printer = new PrintHandler(this);
            printer.doWebViewPrint(htmlEncodedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}