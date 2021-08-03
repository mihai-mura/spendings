package com.mwewghwai.moneyspend_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Records";
    public static final String TABLE1_NAME = "Categories";
    public static final String TABLE2_NAME = "MoneySpent";
    public static final String COL1 = "ID";
    public static final String T1_COL2 = "category";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable1 = "CREATE TABLE " + TABLE1_NAME + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + T1_COL2 + " TEXT)";
        db.execSQL(createTable1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        onCreate(db);
    }

    public boolean addDataTable1(String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(T1_COL2, category);

        long result = db.insert(TABLE1_NAME, null, values);
        if(result == -1){
            return  false;
        }
        else {
            return true;
        }
    }

    public Cursor getContentTable1(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE1_NAME, null);
        return data;
    }

    public boolean removeCategory(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String del_query = "DELETE FROM " + TABLE1_NAME + " WHERE " + T1_COL2 + " = '" + name + "'";
        db.execSQL(del_query);

        return true;
    }
}
