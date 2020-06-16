package com.example.bakemeacake.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bakemeacake.data.model.Ingredient;
import com.example.bakemeacake.data.model.Instruction;
import com.example.bakemeacake.data.model.LoggedInUser;
import com.example.bakemeacake.data.model.Recipe;

import java.util.ArrayList;
import java.util.List;

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
    // DB Schema for the Recipe Table
    private static final String DATABASE_TABLE_RECIPES = "Recipes";
    private static final String RECIPE_COLUMN_NAME_RECIPE_ID = "ID";
    private static final String RECIPE_COLUMN_NAME_RECIPE_NAME = "Name";
    private static final String RECIPE_COLUMN_NAME_USER_ID = "User_ID";
    private static final String CreateTable_RecipeList = "Create Table " + DATABASE_TABLE_RECIPES + " (" + RECIPE_COLUMN_NAME_RECIPE_ID + " Integer Primary Key AutoIncrement, " + RECIPE_COLUMN_NAME_RECIPE_NAME + " Text, " + RECIPE_COLUMN_NAME_USER_ID + " Text)";

    // DB Schema for the Recipe Ingredients table
    private static final String DATABASE_TABLE_INGREDIENTS = "Ingredients";
    private static final String RECIPE_COLUMN_NAME_INGREDIENT_ID = "ID";
    private static final String RECIPE_COLUMN_NAME_RECIPE_ID_FK = "Recipe_ID";
    private static final String RECIPE_COLUMN_NAME_INGREDIENT_AMOUNT = "Amount";
    private static final String RECIPE_COLUMN_NAME_INGREDIENT_NAME = "Name";
    private static final String CreateTable_IngredientList = "Create Table " + DATABASE_TABLE_INGREDIENTS + " (" + RECIPE_COLUMN_NAME_INGREDIENT_ID + " Integer Primary Key AutoIncrement, " + RECIPE_COLUMN_NAME_RECIPE_ID_FK + " Integer, " + RECIPE_COLUMN_NAME_INGREDIENT_AMOUNT + " Text, " + RECIPE_COLUMN_NAME_INGREDIENT_NAME + " Text)";

    // DB Schema for the Recipe Instructions table
    private static final String DATABASE_TABLE_INSTRUCTIONS = "Instructions";
    private static final String RECIPE_COLUMN_NAME_INSTRUCTION_ID = "ID";
    private static final String RECIPE_COLUMN_NAME_INSTRUCTION_STEP = "Step";
    private static final String RECIPE_COLUMN_NAME_INSTRUCTIONS = "Instruction";
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

    // Creates a recipe
    public long CreateRecipe(Recipe model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPE_COLUMN_NAME_RECIPE_NAME, model.Name);
        values.put(RECIPE_COLUMN_NAME_USER_ID, model.User_ID);

        return db.insert(DATABASE_TABLE_RECIPES, null, values);
    }

    // Gets all recipes for a user
    public ArrayList<Recipe> GetRecipes(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] dbColumns = new String[]{RECIPE_COLUMN_NAME_RECIPE_ID, RECIPE_COLUMN_NAME_INGREDIENT_NAME, RECIPE_COLUMN_NAME_USER_ID};

        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        try {
            Cursor dbCursor = db.query(DATABASE_TABLE_RECIPES, dbColumns, "User_ID = ?", new String[]{ Integer.toString(userId) }, null, null, null);
            while(dbCursor.moveToNext()) {
                Recipe recipe = new Recipe();
                recipe.ID = dbCursor.getInt(dbCursor.getColumnIndex(RECIPE_COLUMN_NAME_RECIPE_ID));
                recipe.Name = dbCursor.getString(dbCursor.getColumnIndex(RECIPE_COLUMN_NAME_RECIPE_NAME));
                recipe.User_ID = dbCursor.getInt(dbCursor.getColumnIndex(RECIPE_COLUMN_NAME_USER_ID));
                recipes.add(recipe);
            }
        }
        catch (Exception error){
            Log.e("BMAC Error", error.toString());
        }
        return recipes;
    }

    // Update a recipe
    public int UpdateRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPE_COLUMN_NAME_RECIPE_NAME, recipe.Name);
        values.put(RECIPE_COLUMN_NAME_USER_ID, recipe.User_ID);

        return db.update(DATABASE_TABLE_RECIPES, values, "ID = ?", new String[] { Integer.toString(recipe.ID) });
    }

    // Delete a recipe
    public int DeleteRecipe (Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DATABASE_TABLE_RECIPES, "ID = ?", new String[] { Integer.toString(ingredient.ID) });
    }

    // Create Ingredients
    public long CreateIngredient(Ingredient model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPE_COLUMN_NAME_INGREDIENT_NAME, model.Name);
        values.put(RECIPE_COLUMN_NAME_INGREDIENT_AMOUNT, model.Amount);
        values.put(RECIPE_COLUMN_NAME_RECIPE_ID_FK, model.Recipe_ID);

        return db.insert(DATABASE_TABLE_INGREDIENTS, null, values);
    }

    // Get all ingredients for a recipe
    public List<Ingredient> GetIngredients(int recipeId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] dbColumns = new String[]{RECIPE_COLUMN_NAME_RECIPE_ID, RECIPE_COLUMN_NAME_INGREDIENT_NAME, RECIPE_COLUMN_NAME_INGREDIENT_AMOUNT, RECIPE_COLUMN_NAME_RECIPE_ID_FK};

        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        try {
            Cursor dbCursor = db.query(DATABASE_TABLE_INGREDIENTS, dbColumns, "Recipe_ID = ?", new String[]{ Integer.toString(recipeId) }, null, null, null);
            while(dbCursor.moveToNext()){
                Ingredient ingredient = new Ingredient();
                ingredient.ID = dbCursor.getInt(dbCursor.getColumnIndex(RECIPE_COLUMN_NAME_RECIPE_ID));
                ingredient.Name = dbCursor.getString(dbCursor.getColumnIndex(RECIPE_COLUMN_NAME_INGREDIENT_NAME));
                ingredient.Amount = dbCursor.getString(dbCursor.getColumnIndex(RECIPE_COLUMN_NAME_INGREDIENT_AMOUNT));
                ingredient.Recipe_ID  = dbCursor.getInt(dbCursor.getColumnIndex(RECIPE_COLUMN_NAME_RECIPE_ID_FK));
                ingredients.add(ingredient);
            }
        }
        catch (Exception error){
            Log.e("BMAC Error", error.toString());
        }

        return ingredients;
    }

    // Update Ingredients
    public int UpdateIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPE_COLUMN_NAME_INGREDIENT_NAME, ingredient.Name);
        values.put(RECIPE_COLUMN_NAME_INGREDIENT_AMOUNT, ingredient.Amount);
        values.put(RECIPE_COLUMN_NAME_RECIPE_ID_FK, ingredient.Recipe_ID);

        return db.update(DATABASE_TABLE_INGREDIENTS, values, "ID = ?", new String[] { Integer.toString(ingredient.ID) });
    }

    // Delete an ingredient
    public int DeleteIngredient (Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DATABASE_TABLE_INGREDIENTS, "ID = ?", new String[] { Integer.toString(ingredient.ID) });
    }

    // Create Instruction
    public long CreateInstruction(Instruction model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPE_COLUMN_NAME_INSTRUCTION_STEP, model.Step);
        values.put(RECIPE_COLUMN_NAME_INSTRUCTIONS, model.Instruction);
        values.put(RECIPE_COLUMN_NAME_RECIPE_ID_FK, model.Recipe_ID);

        return db.insert(DATABASE_TABLE_INSTRUCTIONS, null, values);
    }

    // Get all instructions for a recipe
    public List<Instruction> GetInstructions(int recipeId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] dbColumns = new String[]{RECIPE_COLUMN_NAME_RECIPE_ID, RECIPE_COLUMN_NAME_INSTRUCTION_STEP, RECIPE_COLUMN_NAME_INSTRUCTIONS, RECIPE_COLUMN_NAME_RECIPE_ID_FK};

        ArrayList<Instruction> instructions = new ArrayList<Instruction>();
        try {
            Cursor dbCursor = db.query(DATABASE_TABLE_INGREDIENTS, dbColumns, "Recipe_ID = ?", new String[]{ Integer.toString(recipeId) }, null, null, null);
            while(dbCursor.moveToNext()){
                Instruction instruction = new Instruction();
                instruction.ID = dbCursor.getInt(dbCursor.getColumnIndex(RECIPE_COLUMN_NAME_RECIPE_ID));
                instruction.Step = dbCursor.getInt(dbCursor.getColumnIndex(RECIPE_COLUMN_NAME_INSTRUCTION_STEP));
                instruction.Instruction = dbCursor.getString(dbCursor.getColumnIndex(RECIPE_COLUMN_NAME_INSTRUCTIONS));
                instruction.Recipe_ID  = dbCursor.getInt(dbCursor.getColumnIndex(RECIPE_COLUMN_NAME_RECIPE_ID_FK));
                instructions.add(instruction);
            }
        }
        catch (Exception error){
            Log.e("BMAC Error", error.toString());
        }

        return instructions;
    }

    // Updates an instruction
    public int UpdateInstruction(Instruction instruction) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPE_COLUMN_NAME_INSTRUCTIONS, instruction.Instruction);
        values.put(RECIPE_COLUMN_NAME_INSTRUCTION_STEP, instruction.Step);
        values.put(RECIPE_COLUMN_NAME_RECIPE_ID_FK, instruction.Recipe_ID);

        return db.update(DATABASE_TABLE_INSTRUCTIONS, values, "ID = ?", new String[] { Integer.toString(instruction.ID) });
    }

    // Deletes an instruction
    public int DeleteInstruction (Instruction Instruction) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DATABASE_TABLE_INSTRUCTIONS, "ID = ?", new String[] { Integer.toString(Instruction.ID) });
    }
}
