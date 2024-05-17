package com.example.yeniprojekotlin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userDatabase";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Yeni tablo için sabitler
    public static final String TABLE_REGISTER = "register";
    public static final String COLUMN_AD = "ad";
    public static final String COLUMN_SOYAD = "soyad";
    public static final String COLUMN_TCKN = "tckn";
    public static final String COLUMN_GMAIL = "gmail";

    private static final String TABLE_CREATE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT);";

    private static final String TABLE_CREATE_REGISTER =
            "CREATE TABLE " + TABLE_REGISTER + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_AD + " TEXT, " +
                    COLUMN_SOYAD + " TEXT, " +
                    COLUMN_TCKN + " TEXT, " +
                    COLUMN_GMAIL + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(TABLE_CREATE_USERS);
            db.execSQL(TABLE_CREATE_REGISTER);

            // Admin kullanıcılarını ekle
            addAdminUser(db, "admin", "admin123");
            addAdminUser(db, "admin2", "admin456");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Veritabanı oluşturulurken hata: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);
        onCreate(db);
    }

    private void addAdminUser(SQLiteDatabase db, String username, String password) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    // Yeni kullanıcıyı eklemek için yöntem
    public boolean registerUser(String ad, String soyad, String tckn, String gmail, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues userValues = new ContentValues();
            userValues.put(COLUMN_USERNAME, username);
            userValues.put(COLUMN_PASSWORD, password);
            long userId = db.insert(TABLE_USERS, null, userValues);

            if (userId == -1) {
                return false;
            }

            ContentValues registerValues = new ContentValues();
            registerValues.put(COLUMN_AD, ad);
            registerValues.put(COLUMN_SOYAD, soyad);
            registerValues.put(COLUMN_TCKN, tckn);
            registerValues.put(COLUMN_GMAIL, gmail);
            db.insert(TABLE_REGISTER, null, registerValues);

            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Kayıt yapılırken hata: " + e.getMessage());
            return false;
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
