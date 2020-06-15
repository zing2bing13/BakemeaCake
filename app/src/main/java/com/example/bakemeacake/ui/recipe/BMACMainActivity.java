package com.example.bakemeacake.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.bakemeacake.R;
import com.example.bakemeacake.Session;
import com.example.bakemeacake.data.model.LoggedInUser;

public class BMACMainActivity extends AppCompatActivity {

    private String shareText = null;
    private TextView df_TextView;
    private LoggedInUser loggedInUser = null;
    private Session session = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_a_c_main_activity);
        session = new Session(this.getApplicationContext());
        loggedInUser = new LoggedInUser(session.getUserID(), session.getUsername(), "");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        df_TextView = findViewById(R.id.df_textView);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabOnClick(view);
            }
        });
    }

    private void fabOnClick(View v){
        Intent BMACRecipeActivity = new Intent(BMACMainActivity.this, BMACAddRecipeActivity.class);
        startActivity(BMACRecipeActivity);
    }

    /*
        shareText = df_TextView.getText().toString();

        if (shareText != null) {
            ShareHandler shareIntent = new ShareHandler(this);
            //shareIntent.shareText(shareText);
            //shareIntent.shareHTML(null, "Cake Recipe", shareText);
            shareIntent.shareFile(shareText);
        }

         */


    private void showFabSnackbar(View v) {
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
       .setAction("Action", null).show();
    }
}
