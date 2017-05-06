package com.example.garrett.moodbooster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;


/**
 * Created by Garrett on 4/11/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public SQLiteDatabase db;
    public static String DATABASE_NAME = "users.db";
    public static int DATABASE_VERSION = 1;
    public static String TABLE_USER = "User";

    // IDENTIFYING CHARACTERISTICS
    public static String COL_NAME = "user_name";
    public static String COL_EMOTION = "emotion";
    public static String COL_ID = "_id";

    // PREFERENCE INFORMATION
    public static String COL_PANDA = "panda";
    public static String COL_MUSIC = "music";
    public static String COL_NATURE = "nature";
    public static String COL_RECIPES = "recipes";
    public static String COL_EXERCISE = "exercises";
    public static String COL_FAMILY = "family";
    public static String COL_FRIENDS = "friends";
    public static String COL_BOOKS = "books";
    public static String COL_QUOTES = "quotes";
    public static String COL_INSPIRE = "inspire";
    public static String COL_TRAVEL = "travel";
    public static String COL_FUNNY = "funny";


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_USER + " ( " + COL_ID + " INTEGER PRIMARY KEY," +
                COL_NAME + " TEXT," + COL_EMOTION + " TEXT," + COL_PANDA + " TEXT," +
                COL_MUSIC + " TEXT," + COL_NATURE + " TEXT," + COL_RECIPES + " TEXT," +
                COL_EXERCISE + " TEXT," + COL_FAMILY + " TEXT," + COL_FRIENDS + " TEXT," +
                COL_BOOKS + " TEXT," + COL_QUOTES + " TEXT," + COL_INSPIRE + " TEXT," +
                COL_TRAVEL + " TEXT," + COL_FUNNY + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists " + TABLE_USER );
        onCreate(db);
    }

    public void insertData(Mood mood) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, mood.getID());
        contentValues.put(COL_NAME, mood.getName());
        contentValues.put(COL_EMOTION, mood.getEmotion());
        contentValues.put(COL_PANDA, mood.getPanda());
        contentValues.put(COL_MUSIC, mood.getMusic());
        contentValues.put(COL_NATURE, mood.getNature());
        contentValues.put(COL_RECIPES, mood.getRecipes());
        contentValues.put(COL_EXERCISE, mood.getExercises());
        contentValues.put(COL_FAMILY, mood.getFamily());
        contentValues.put(COL_FRIENDS, mood.getFriends());
        contentValues.put(COL_BOOKS, mood.getBooks());
        contentValues.put(COL_QUOTES, mood.getQuotes());
        contentValues.put(COL_INSPIRE, mood.getInspire());
        contentValues.put(COL_TRAVEL, mood.getTravel());
        contentValues.put(COL_FUNNY, mood.getFunny());

        long ret = db.insert(TABLE_USER, null, contentValues );

        if (ret > -1) {
            System.out.println("Successfully inserted");
        } else {
            System.out.println("Insert Unsuccessful");
        }

        db.close();
    }

}
