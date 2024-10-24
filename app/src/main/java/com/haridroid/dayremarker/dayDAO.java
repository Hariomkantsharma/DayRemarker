package com.haridroid.dayremarker;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import kotlinx.coroutines.Dispatchers;

import kotlinx.coroutines.*;
import kotlinx.coroutines.android.HandlerDispatcherKt;


@Dao
public interface dayDAO {
    @Query("SELECT * FROM year_2024")
        List<dayEntity> getAllDay();

    @Update
    void updateDay(dayEntity day);

    @Insert
    void insertDay(dayEntity day);

}
