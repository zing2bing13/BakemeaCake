package com.example.bakemeacake;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences preferences;

    public Session(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void SetUserName(String username){
        preferences.edit().putString("username", username).commit();
    }

    public void SetUserID(Integer userID){
        preferences.edit().putInt("userid", userID).commit();
    }

    public String GetUsername(){
        return preferences.getString("username","");
    }

    public Integer GetUserID(){
        return preferences.getInt("userid",0);
    }
}
