package com.haridroid.dayremarker;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlinx.coroutines.Dispatchers;

import kotlinx.coroutines.*;
import kotlinx.coroutines.android.HandlerDispatcherKt;

public class dayDatabaseFunctions {
    public static dayDatabaseHelper instance;
    public static dayDatabaseHelper initialiseDB(Context context){
        instance = dayDatabaseHelper.getDB(context);
        return instance;
    }

public static void initialiseYear(Context context, int currYear, ArrayList<String> Months, HashMap<String,Integer> MonthMap, ArrayList<String> dayOfWeeks){

        for (int i=1; i<=12; i++){
        String currMonthName = Months.get(i);
        int daysInMonth = MonthMap.get(currMonthName);
        for (int j=1; j<=daysInMonth; j++){
            int dayOfWeek = getDayOfWeek(j, i, currYear);
            dayEntity day = new dayEntity(String.valueOf(j), "", currMonthName, dayOfWeeks.get(dayOfWeek));

            //yha pe dekh lo bas
//            CoroutineScope(Dispatchers.IO).launch {
                instance.dayDAO().insertDay(day);
//            }

        }


    }

}

public static void insertDay(Context context, String day, String note, String month, String dayOfWeek){
        dayEntity dayEntity = new dayEntity(day, note, month, dayOfWeek);
//        CoroutineScope(IO).launch {
            instance.dayDAO().insertDay(dayEntity);
//        }
}

public  static void updateDay(Context context, String day, String note, String month, String dayOfWeek){
        dayEntity dayEntity = new dayEntity(day, note, month, dayOfWeek);

//        CoroutineScope(IO).launch {
        instance.dayDAO().updateDay(dayEntity);
//    }
}

public static List<dayEntity> getAllDays(Context context){
        return instance.dayDAO().getAllDay();
}

    public static int getDayOfWeek(int day, int month, int year) {
        if (month < 3) {
            month += 12;
            year--;
        }
        int J = year / 100;
        int K = year % 100;
        int h = (day + (13 * (month + 1)) / 5 + K + K / 4 + J / 4 - 2 * J) % 7;
        // Adjust h to match day of week (0 = Sunday, 1 = Monday, ..., 6 = Saturday)
        return (h + 6) % 7;
    }
}



