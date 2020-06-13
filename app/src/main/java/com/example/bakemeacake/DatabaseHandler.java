package com.example.bakemeacake;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bakemeacake.data.model.LoggedInUser;

public class DatabaseHandler extends SQLiteOpenHelper {
    // DB Schema for the UserAccounts database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_BMACSql.db";
    private static final String DATABASE_TABLE_USERNAMES = "UserAccounts";
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_USERNAME = "username";
    private static final String COLUMN_NAME_PASSWORD = "password";
    private static final String CreateTable_UserAccounts = "Create Table " + DATABASE_TABLE_USERNAMES + " (" + COLUMN_NAME_ID + " Integer Primary Key AutoIncrement, " + COLUMN_NAME_USERNAME + " Text, " + COLUMN_NAME_PASSWORD + " Text)";

    // DB Schema for the Recipes database
    // DB Schema for the RecipeList Table
    private static final String DATABASE_TABLE_RECIPES = "Recipes";
    private static final String RECIPE_COLUMN_NAME_RECIPE_ID = "recipeid";
    private static final String RECIPE_COLUMN_NAME_RECIPE_NAME = "recipename";
    private static final String RECIPE_COLUMN_NAME_USER_ID_FK = "userid";
    private static final String CreateTable_RecipeList = "Create Table " + DATABASE_TABLE_RECIPES + " (" + RECIPE_COLUMN_NAME_RECIPE_ID + " Integer Primary Key AutoIncrement, " + RECIPE_COLUMN_NAME_RECIPE_NAME + " Text, " + RECIPE_COLUMN_NAME_USER_ID_FK + " Text)";

    // DB Schema for the Recipe Ingredients table
    private static final String DATABASE_TABLE_INGREDIENTS = "Ingredients";
    private static final String RECIPE_COLUMN_NAME_INGREDIENT_ID = "ingredientid";
    private static final String RECIPE_COLUMN_NAME_RECIPE_ID_FK = "recipeid";
    private static final String RECIPE_COLUMN_NAME_INGREDIENT_AMOUNT = "amount";
    private static final String RECIPE_COLUMN_NAME_INGREDIENT_NAME = "ingredientname";
    private static final String CreateTable_IngredientList = "Create Table " + DATABASE_TABLE_INGREDIENTS + " (" + RECIPE_COLUMN_NAME_INGREDIENT_ID + " Integer Primary Key AutoIncrement, " + RECIPE_COLUMN_NAME_RECIPE_ID_FK + " Integer, " + RECIPE_COLUMN_NAME_INGREDIENT_AMOUNT + " Text, " + RECIPE_COLUMN_NAME_INGREDIENT_NAME + " Text)";

    // DB Schema for the Recipe Instructions table
    private static final String DATABASE_TABLE_INSTRUCTIONS = "Instructions";
    private static final String RECIPE_COLUMN_NAME_INSTRUCTION_ID = "instructionid";
    private static final String RECIPE_COLUMN_NAME_INSTRUCTION_STEP = "step";
    private static final String RECIPE_COLUMN_NAME_INSTRUCTIONS = "instruction";
    private static final String CreateTable_InstructionList = "Create Table " + DATABASE_TABLE_INSTRUCTIONS + " (" + RECIPE_COLUMN_NAME_INSTRUCTION_ID + " Integer Primary Key AutoIncrement, " + RECIPE_COLUMN_NAME_RECIPE_ID_FK + " Integer, " + RECIPE_COLUMN_NAME_INSTRUCTION_STEP + " Integer, " + RECIPE_COLUMN_NAME_INSTRUCTIONS + " Text)";

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
        db.execSQL(CreateTable_RecipeList);
        db.execSQL(CreateTable_IngredientList);
        db.execSQL(CreateTable_InstructionList);
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
