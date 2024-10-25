package com.haridroid.dayremarker;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ArrayAdapter;
import android.content.Context; // If not already imported
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List; // If arrNames is a List

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import kotlinx.coroutines.Dispatchers;

public class MainActivity extends AppCompatActivity {

    //1. variable declarations
    Button prev, next, save;
    TextView MonthText;
    RecyclerView recyclerView;
    RecyclerDayAdapter adapter;
    ProgressBar progressBar;

    static ArrayList<String> MonthArr = new ArrayList<>();;
    static HashMap<String,Integer> MonthMap = new HashMap<>();
    static ArrayList<String> dayOfWeeks= new ArrayList<>();

    static Integer currMonth, currDay, currYear;

    static ArrayList<itemStructure> arrayDays= new ArrayList<>();
    static ArrayList<itemStructure>  year =new ArrayList<>();
    MyDBhelper DBhelper;
    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //2. get shared pref variables day, month, year
        getSharedPreferenceVaribles();

        //3. find view by ids for views + RV layoutset
        FVBI();
        progressBar= findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        //4. set values of monthNames, daysInMonth, namesOfDaysOfWeek
        setValuseOfNames();

        // Important: currMonth and MonthArr using zero indexing start
        //5. set month text
        String s= MonthArr.get(currMonth)+"\n"+currYear;
        MonthText.setText(s);


        //6.getting database on app start
        DBhelper= MyDBhelper.getInstance(this);
        //7. check if first time app run
        if(DBhelper.isFirstRun()){
            // if yes-> initialise year db in background thread+ set flag=1(no more first time run)
            DBhelper.setFirstRunFlag();
            initialiseYearDB();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                //8. get year data from db
                year = new ArrayList<>();
                year= DBhelper.Fetchdb();

                // 9. getting days for currMonth from year into arr
                arrayDays.clear();
                for (int i= 0 ; i<year.size(); i++){
                    if(year.get(i).month.equals(MonthArr.get(currMonth))){
                        arrayDays.add(year.get(i));
                    }
                }
                // when getting back
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        //10. arr-> adapter-> RV
                        adapter = new RecyclerDayAdapter(MainActivity.this, arrayDays);
                        recyclerView.setAdapter(adapter);

                        //11. scroll to current day
                        recyclerView.scrollToPosition(currDay - 1);
                    }
                });
            }
        }).start();


    // EVENTS

        // btn on click => prev, next, monthText-> updateMonth
        prev.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(currMonth-1>=0) {
                                            //to stop infinite prev/next button

                                            //set currMonth-1
                                            currMonth = (currMonth - 1 + 12) % 12;
                                            updateMonth();
                                        }
                                    }
                                });
        
        
        next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(currMonth+1<12) {
                                            //to stop infinite prev/next button

                                            //set currMonth+1
                                            currMonth = (currMonth + 1) % 12;
                                            updateMonth();
                                        }
                                    }
                                });

        // monthText on click => month_selection_dialog
        MonthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Month_selection_dialog dialog = new Month_selection_dialog(MainActivity.this);
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateMonth();
                    }
                });

                progressBar.setVisibility(View.GONE);
            }
        });


        //save btn click
        save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        save.setBackgroundResource(R.drawable.custom_btn_2);
                                        progressBar.setVisibility(View.VISIBLE);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                for (int i=0; i<year.size(); i++){
                                                    DBhelper.update(year.get(i).month, year.get(i).day, year.get(i).note, year.get(i).dayOfWeek);
                                                }

                                                save.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        save.setBackgroundResource(R.drawable.custom_btn_1);
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                });
                                            }
                                        }).start();

                                    }
                                });
    }


    //mathods used
    void getSharedPreferenceVaribles(){
        Calendar calendar = Calendar.getInstance();

        int thisMonth= calendar.get(Calendar.MONTH);
        SharedPreferences prefs = getSharedPreferences("currMonth", Context.MODE_PRIVATE);
        currMonth = prefs.getInt("currMonth", thisMonth);


        int today= calendar.get(Calendar.DAY_OF_MONTH);
        SharedPreferences prefDay= getSharedPreferences("currDay", Context.MODE_PRIVATE);
        currDay = prefDay.getInt("currDay", today );

        int thisYear= calendar.get(Calendar.YEAR);
        SharedPreferences prefYear= getSharedPreferences("currYear", Context.MODE_PRIVATE);
        currYear = prefYear.getInt("currYear", thisYear);
    }
    void FVBI(){
        prev = findViewById(R.id.PrevMonthBtn);
        next = findViewById(R.id.NextMonthBtn);
        save = findViewById(R.id.SaveBtn);
        MonthText = findViewById(R.id.MonthText);
        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    void setValuseOfNames(){

        MonthArr.add("Jan");
        MonthArr.add("Feb");
        MonthArr.add("Mar");
        MonthArr.add("Apr");
        MonthArr.add("May");
        MonthArr.add("Jun");
        MonthArr.add("Jul");
        MonthArr.add("Aug");
        MonthArr.add("Sep");
        MonthArr.add("Oct");
        MonthArr.add("Nov");
        MonthArr.add("Dec");


        MonthMap.put("Jan",31);
        if(currYear%4==0) {
            MonthMap.put("Feb",29);
        }
        else{
            MonthMap.put("Feb",28);
        }
        MonthMap.put("Mar",31);
        MonthMap.put("Apr",30);
        MonthMap.put("May",31);
        MonthMap.put("Jun",30);
        MonthMap.put("Jul",31);
        MonthMap.put("Aug",31);
        MonthMap.put("Sep",30);
        MonthMap.put("Oct",31);
        MonthMap.put("Nov",30);
        MonthMap.put("Dec",31);

        dayOfWeeks.add("Sun");
        dayOfWeeks.add("Mon");
        dayOfWeeks.add("Tue");
        dayOfWeeks.add("Wed");
        dayOfWeeks.add("Thu");
        dayOfWeeks.add("Fri");
        dayOfWeeks.add("Sat");

    }


    public void updateMonth(){
        progressBar.setVisibility(View.VISIBLE);
        //update recent entries - Month, day, year in shared preferecne
        SharedPreferences.Editor editor= getSharedPreferences("currMonth", Context.MODE_PRIVATE).edit();
        editor.putInt("currMonth", currMonth);
        editor.apply();

        SharedPreferences.Editor editorDay= getSharedPreferences("currDay", Context.MODE_PRIVATE).edit();
        editorDay.putInt("currDay", 1);
        editorDay.apply();

        SharedPreferences.Editor editorYear= getSharedPreferences("currYear", Context.MODE_PRIVATE).edit();
        editorYear.putInt("currYear", currYear);
        editorYear.apply();

        // set UI changes
        String s= MonthArr.get(currMonth)+"\n"+currYear;
        MonthText.setText(s);


        arrayDays.clear();
        for (int i= 0 ; i<year.size(); i++){
            if(year.get(i).month.equals(MonthArr.get(currMonth))){
                arrayDays.add(year.get(i));
            }
        }


        adapter= new RecyclerDayAdapter(this, arrayDays);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);

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
    public void initialiseYearDB(){
        CountDownLatch latch= new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i=1; i<=12; i++) {
                    String currMonthName = MonthArr.get(i - 1);
                    int daysInMonth = MonthMap.get(currMonthName);
                    for (int j = 1; j <= daysInMonth; j++) {
                        int dayOfWeek = getDayOfWeek(j, i, currYear);
                        itemStructure day = new itemStructure(currMonthName, String.valueOf(j), "", dayOfWeeks.get(dayOfWeek));
                        DBhelper.insert(currMonthName, String.valueOf(j), "", dayOfWeeks.get(dayOfWeek));
                    }
                }
                latch.countDown();
            }
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setMonth(int month){
        //to set month on dialog box btn clicks
        currMonth=month;
    }

}



