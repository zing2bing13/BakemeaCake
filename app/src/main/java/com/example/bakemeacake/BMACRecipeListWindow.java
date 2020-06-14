package com.example.bakemeacake;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.bakemeacake.util.PrintHandler;
import com.example.bakemeacake.util.ShareHandler;

import com.example.bakemeacake.data.LoginRepository;
import com.example.bakemeacake.data.model.LoggedInUser;

public class BMACRecipeListWindow extends AppCompatActivity {

    private String shareText = null;
    private TextView df_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_a_c_recipe_list_window);
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

    private void fabOnClick(View v) {
        //showFabSnackbar(v);
        //shareText();
        printHTML();
    }

    private void showFabSnackbar(View v) {
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
       .setAction("Action", null).show();
    }

    private void shareText() {
        shareText = df_TextView.getText().toString();

        if (shareText != null) {
            ShareHandler shareIntent = new ShareHandler(this);
            //shareIntent.shareText(shareText);
            //shareIntent.shareHTML(null, "Cake Recipe", shareText);
            shareIntent.shareFile(shareText);
        }
    }

    private void printHTML() {
        try {
            shareText = df_TextView.getText().toString();
            String htmlEncodedString = TextUtils.htmlEncode(shareText);
            PrintHandler printer = new PrintHandler(this);
            printer.doWebViewPrint(htmlEncodedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
