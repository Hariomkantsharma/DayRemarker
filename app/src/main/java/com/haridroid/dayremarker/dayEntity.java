package com.haridroid.dayremarker;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "year_2024")
public class dayEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "day")
    private String day;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "month")
    private String month;

    @ColumnInfo(name = "dayOfWeek")
    private String dayOfWeek;

    public dayEntity(String day, String note, String month, String dayOfWeek) {
        this.day = day;
        this.note = note;
        this.month = month;
        this.dayOfWeek = dayOfWeek;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}