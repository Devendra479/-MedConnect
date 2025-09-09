package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserFiles.db";
    private static final String TABLE_NAME = "files";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_TYPE = "type";
    private static final String COL_DATA = "data"; // BLOB for storing image/PDF

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_TYPE + " TEXT, " +
                COL_DATA + " BLOB)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert file (image or PDF)
    public boolean insertFile(String name, String type, byte[] data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_TYPE, type);
        contentValues.put(COL_DATA, data);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // Retrieve all files
    public Cursor getAllFiles() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
