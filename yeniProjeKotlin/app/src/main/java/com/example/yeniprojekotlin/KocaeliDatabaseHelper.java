package com.example.yeniprojekotlin;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KocaeliDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "kocaeli.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_TICKETS = "tickets";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_COUNT = "count";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TICKETS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CATEGORY + " TEXT, " +
                    COLUMN_COUNT + " INTEGER" +
                    ");";

    public KocaeliDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
        onCreate(db);
    }
}


