package com.example.bakemeacake;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bakemeacake.data.model.Recipe;
import com.example.bakemeacake.util.PrintHandler;
import com.example.bakemeacake.util.ShareHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class BMACRecipeListWindow extends AppCompatActivity {

    private String shareText = null;
    private Session session = null;
    private DatabaseHandler dbHandler = null;
    private ArrayList<Recipe> recipes = null;
    static final int NEW_RECIPE_ACTIVITY_INTENT_CODE = 100;
    static final int NEW_OPEN_ACTIVITY_INTENT_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_a_c_recipe_list_window);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //df_TextView = findViewById(R.id.df_textView);
        FloatingActionButton fab = findViewById(R.id.fab);
        this.session = new Session(this);
        this.dbHandler = new DatabaseHandler(this);

        this.populateRecipes();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabOnClick(view);
            }
        });

    }

    private void populateRecipes() {
         this.recipes = this.dbHandler.GetRecipes(session.GetUserID());
         ListView listView = findViewById(R.id.recipe_list);

         List<String> items = recipes.stream().map(x -> x.Name).collect(Collectors.toList());
         listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));

         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Recipe recipe = recipes.get(position);
                 OpenRecipeActivity(recipe);
             }
         });
    }

    public void OpenRecipeActivity(Recipe recipe){
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("Recipe", recipe);
        startActivityForResult(intent, this.NEW_OPEN_ACTIVITY_INTENT_CODE);
    }

    private void fabOnClick(View v) {
        Intent intent = new Intent(this, NewRecipeActivity.class);
        startActivityForResult(intent, this.NEW_RECIPE_ACTIVITY_INTENT_CODE);
        //showFabSnackbar(v);
        //shareText();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.NEW_RECIPE_ACTIVITY_INTENT_CODE) {
            Recipe recipe = (Recipe) data.getSerializableExtra("Recipe");
            if(recipe != null) {
                recipe.User_ID = this.session.GetUserID();
                long result = dbHandler.CreateRecipe(recipe);
                this.populateRecipes();
            }
        }
    }

    private void showFabSnackbar(View v) {
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
       .setAction("Action", null).show();
    }

    private void shareText() {
        //shareText = df_TextView.getText().toString();
        shareText = null;

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
            shareText = null;
            String htmlEncodedString = TextUtils.htmlEncode(shareText);
            PrintHandler printer = new PrintHandler(this);
            printer.doWebViewPrint(htmlEncodedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
