package com.haridroid.dayremarker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class MyDBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="DayRemarker.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME= "2024";

    private static final String COL_1="ID";
    private static final String COL_2="MONTH";
    private static final String COL_3="DAY";
    private static final String COL_4="NOTE";
    private static final String COL_5="DAYOFWEEK";


    public MyDBhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table TABLE_NAME(COL_1 Integer Primary Key, COL_2 Text, COL_3 Text, COL_4 Text, COL_5 Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists TABLE_NAME");
        onCreate(db);
    }

    public  void insert (String month, String day, String note, String dayOfWeek){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COL_2, month);
        values.put(COL_3, day);
        values.put(COL_4, note);
        values.put(COL_5, dayOfWeek);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public  void update (String month, String day, String note, String dayOfWeek){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COL_2, month);
        values.put(COL_3, day);
        values.put(COL_4, note);
        values.put(COL_5, dayOfWeek);
        //update all values passed wherever day and month match
        db.update(TABLE_NAME, values, "COL_2 = ? AND COL_3 = ?", new String[]{month, day});
        db.close();
    }
    public  void delete (String month, String day, String note, String dayOfWeek){}

    public ArrayList<itemStructure> Fetchdb(){
        SQLiteDatabase db= this.getWritableDatabase();
        ArrayList<itemStructure> array_list= new ArrayList<>();
        Cursor cr= db.rawQuery("select * from TABLE_NAME", null);
        while(cr.moveToNext()){
            itemStructure day= new itemStructure(cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4));
            array_list.add(day);
        }
        db.close();
        return array_list;

    }
}
