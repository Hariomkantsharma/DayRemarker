package com.haridroid.dayremarker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.HashMap;

@Database(entities = {dayEntity.class}, version = 1)
public abstract class dayDatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "day_database";

    private static dayDatabaseHelper instance;

    public static synchronized dayDatabaseHelper getDB(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), dayDatabaseHelper.class, DB_NAME).build();
        }
        return instance;
    }
    public abstract dayDAO dayDAO();
}

