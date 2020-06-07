package com.example.bakemeacake;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_BMACSql.db";
    private static final String DATABASE_TABLE_USERNAMES = "UserAccounts";
    private static final String CreateTable_UserAccounts = "Create Table DATABASE_TABLE_USERNAMES(ID Integer Primary Key AutoIncrement, UserName Text, Password Text)";

    private static DatabaseHandler myInstance = null;
    public static DatabaseHandler getMyInstance(Context context) {
        if(myInstance == null) {
            myInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return myInstance;
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateTable_UserAccounts);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onCreate(db);
    }

    public long insertIntoUserAccts(String userName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("UserName", userName);
        values.put("Password", password);

        return db.insert("DATABASE_TABLE_USERNAMES", null, values);
    }
}
