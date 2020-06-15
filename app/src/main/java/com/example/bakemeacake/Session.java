package com.example.bakemeacake;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences preferences;

    public Session(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUserName(String username){
        preferences.edit().putString("username", username).apply();
    }

    public void setUserID(Integer userID){
        preferences.edit().putInt("userid", userID).apply();
    }

    public String getUsername(){
        return preferences.getString("username","");
    }

    public Integer getUserID(){
        return preferences.getInt("userid",0);
    }

    public void clearSession() { preferences.edit().clear().apply(); }
}
