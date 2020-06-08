package com.example.bakemeacake;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bakemeacake.data.model.LoggedInUser;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_BMACSql.db";
    private static final String DATABASE_TABLE_USERNAMES = "UserAccounts";
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_USERNAME = "username";
    private static final String COLUMN_NAME_PASSWORD = "password";
    private static final String CreateTable_UserAccounts = "Create Table " + DATABASE_TABLE_USERNAMES + " (" + COLUMN_NAME_ID + " Integer Primary Key AutoIncrement, " + COLUMN_NAME_USERNAME + " Text, " + COLUMN_NAME_PASSWORD + " Text)";

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

    // Get the user from the database based on the username
    public LoggedInUser getUser(String username) {
        LoggedInUser myUser = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] dbColumns = new String[]{COLUMN_NAME_ID, COLUMN_NAME_USERNAME, COLUMN_NAME_PASSWORD};

        try {
            Cursor dbCursor = db.query(DATABASE_TABLE_USERNAMES, dbColumns, "Username = ?", new String[]{username}, null, null, null);
            while(dbCursor.moveToNext()){
                int id = dbCursor.getInt(dbCursor.getColumnIndex(COLUMN_NAME_ID));
                String user = dbCursor.getString(dbCursor.getColumnIndex(COLUMN_NAME_USERNAME));
                String pass = dbCursor.getString(dbCursor.getColumnIndex(COLUMN_NAME_PASSWORD));
                myUser = new LoggedInUser(id, user, pass);
            }
        }
            catch (Exception error){
            Log.e("BMAC Error", error.toString());
        }


        return myUser;
    }

    // Add the user to the database
    public long insertUser(String userName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_USERNAME, userName);
        values.put(COLUMN_NAME_PASSWORD, password);

        return db.insert(DATABASE_TABLE_USERNAMES, null, values);
    }
}
