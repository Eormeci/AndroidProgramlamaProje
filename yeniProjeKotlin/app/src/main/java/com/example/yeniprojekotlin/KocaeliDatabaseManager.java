package com.example.yeniprojekotlin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class KocaeliDatabaseManager {

    private KocaeliDatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public KocaeliDatabaseManager(Context context) {
        dbHelper = new KocaeliDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void addTicketCategory(String category, int count) {
        ContentValues values = new ContentValues();
        values.put(KocaeliDatabaseHelper.COLUMN_CATEGORY, category);
        values.put(KocaeliDatabaseHelper.COLUMN_COUNT, count);
        database.insert(KocaeliDatabaseHelper.TABLE_TICKETS, null, values);
    }

    public int getTicketCount(String category) {
        Cursor cursor = database.query(
                KocaeliDatabaseHelper.TABLE_TICKETS,
                new String[]{KocaeliDatabaseHelper.COLUMN_COUNT},
                KocaeliDatabaseHelper.COLUMN_CATEGORY + "=?",
                new String[]{category},
                null, null, null
        );
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(cursor.getColumnIndexOrThrow(KocaeliDatabaseHelper.COLUMN_COUNT));
            cursor.close();
            return count;
        }
        return 0;
    }

    public void updateTicketCount(String category, int newCount) {
        ContentValues values = new ContentValues();
        values.put(KocaeliDatabaseHelper.COLUMN_COUNT, newCount);
        database.update(
                KocaeliDatabaseHelper.TABLE_TICKETS,
                values,
                KocaeliDatabaseHelper.COLUMN_CATEGORY + "=?",
                new String[]{category}
        );
    }

    public void close() {
        dbHelper.close();
    }
}