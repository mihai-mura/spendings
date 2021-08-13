package com.mwewghwai.moneyspend_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Records";
    public static final String TABLE1_NAME = "Categories";
    public static final String TABLE2_NAME = "Expenses";
    public static final String COL1 = "ID";
    public static final String T1_COL2 = "category";
    public static final String T2_COL2 = "type";// 0 is cash, 1 is card
    public static final String T2_COL3 = "amount";
    public static final String T2_COL4 = "category";
    public static final String T2_COL5 = "note";
    public static final String T2_COL6 = "date";
    public static final String T2_COL7 = "time";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable1 = "CREATE TABLE " + TABLE1_NAME + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + T1_COL2 + " TEXT)";
        String createTable2 = "CREATE TABLE " + TABLE2_NAME + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + T2_COL2 + " INTEGER, " + T2_COL3 + " FLOAT, " +
                T2_COL4 + " TEXT, " + T2_COL5 + " TEXT, " + T2_COL6 + " TEXT, " + T2_COL7 + " TEXT)";
        db.execSQL(createTable1);
        db.execSQL(createTable2);
        Log.d("DataBase", "Created tables");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        onCreate(db);
    }


    public boolean addDataCategories(String category){
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

    public boolean addDataExpenses(boolean type, float amount, String category, String note, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(type == false){
            values.put(T2_COL2, 0);
        }
        else
            values.put(T2_COL2, 1);

        values.put(T2_COL3, amount);
        values.put(T2_COL4, category);
        values.put(T2_COL5, note);
        values.put(T2_COL6, date);
        values.put(T2_COL7, time);



        long result = db.insert(TABLE2_NAME, null, values);
        if(result == -1){
            return  false;
        }
        else {
            return true;
        }
    }

    public Cursor getContent(String table){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = null;
        if(db != null) {
            data = db.rawQuery("SELECT * FROM " + table, null);
        }
        return data;
    }

    public boolean removeFromCategories(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String del_query = "DELETE FROM " + TABLE1_NAME + " WHERE " + T1_COL2 + " = '" + name + "'";
        db.execSQL(del_query);

        return true;
    }

    public boolean itemExistsInCategories(String itemToCheck){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME + " WHERE " + T1_COL2 + " = '" + itemToCheck + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean isEmpty(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + table;
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int item_count = cursor.getInt(0);
        if(item_count>0){
            return false;
        }
        else{
            return true;
        }
    }
}
