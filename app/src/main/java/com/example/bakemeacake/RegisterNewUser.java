package com.example.bakemeacake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bakemeacake.util.DatabaseHandler;

public class RegisterNewUser extends AppCompatActivity {

    private DatabaseHandler myDB = null;
    private String username = null;
    private String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
        Button saveButton = findViewById(R.id.button_save);
        Button cancelButton = findViewById(R.id.button_Cancel);

        saveButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                myDB = new DatabaseHandler(v.getContext());
                EditText usernameText = findViewById(R.id.editText_user_email);
                EditText passwordText = findViewById(R.id.editText_user_password);
                username = usernameText.getText().toString();
                password = passwordText.getText().toString();
                myDB.insertUser(username, password);
                myDB.close();
                finish();
            }
        });
        cancelButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
