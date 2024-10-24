package com.haridroid.dayremarker;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List; // If arrNames is a List

import java.util.ArrayList;

import kotlinx.coroutines.Dispatchers;

public class MainActivity extends AppCompatActivity {

    //variable declarations
    Button prev, next, save;
    TextView MonthText;
    RecyclerView recyclerView;
    RecyclerDayAdapter adapter;

    ArrayList<String> MonthArr = new ArrayList<>();;
    HashMap<String,Integer> MonthMap = new HashMap<>();
    ArrayList<String> dayOfWeeks= new ArrayList<>();

    Integer currMonth, currDay, currYear;

    ArrayList<itemStructure> arrayDays;
    ArrayList<itemStructure> year;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // shared pref variables day, month, year

        SharedPreferences prefs = getSharedPreferences("currMonth", Context.MODE_PRIVATE);
        currMonth = prefs.getInt("currMonth", 0);

        SharedPreferences prefDay= getSharedPreferences("currDay", Context.MODE_PRIVATE);
        currDay = prefDay.getInt("currDay", 1);

        SharedPreferences prefYear= getSharedPreferences("currYear", Context.MODE_PRIVATE);
        currYear = prefYear.getInt("currYear", 2024);

        // define+ fvbid
        prev = findViewById(R.id.PrevMonthBtn);
        next = findViewById(R.id.NextMonthBtn);
        save = findViewById(R.id.SaveBtn);
        MonthText = findViewById(R.id.MonthText);
        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        
        
        

        

//        // getting database on app start
//        dayDatabaseHelper db = dayDatabaseFunctions.initialiseDB(this);
//        dayDatabaseFunctions.initialiseYear( this, currYear, Month, MonthMap, dayOfWeeks);
//        List<dayEntity> days =



        //first UI on app start


// Important: currMonth and MonthArr using zero indexing start
        String s= MonthArr.get(currMonth)+"\n"+currYear;
        MonthText.setText(s);

        year = new ArrayList<>();
        arrayDays= new ArrayList<>();
        year= initialiseYear();
        arrayDays.clear();

        for (int i= 0 ; i<year.size(); i++){
            //printing log entry for this day including day, dayofweek, month
            Log.d("day", year.get(i).day+" "+year.get(i).dayOfWeek+" "+year.get(i).month);
            if(year.get(i).month.equals(MonthArr.get(currMonth))){
                arrayDays.add(year.get(i));
            }
        }

        adapter= new RecyclerDayAdapter(this, arrayDays);
        recyclerView.setAdapter(adapter);
//        recyclerView.scrollToPosition( 15 );




        // btn on click prev, next, save
        
        prev.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(currMonth-1>=0) {
                                            currMonth = (currMonth - 1 + 12) % 12;
                                            updateUI();
                                        }
                                    }
                                });
        
        
        next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(currMonth+1<12) {
                                            currMonth = (currMonth + 1) % 12;
                                            updateUI();
                                        }
                                    }
                                });
        
        save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                });



    }


    void updateUI(){
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

    public ArrayList<itemStructure> initialiseYear(){
        ArrayList<itemStructure> yy= new ArrayList<>();
        for (int i=1; i<=12; i++){
            String currMonthName = MonthArr.get(i-1);
            int daysInMonth = MonthMap.get(currMonthName);
            for (int j=1; j<=daysInMonth; j++){
                int dayOfWeek = getDayOfWeek(j, i, currYear);
                itemStructure day= new itemStructure(currMonthName, String.valueOf(j), "", dayOfWeeks.get(dayOfWeek));
                yy.add( day);
            }
        }
    return yy;
    }
}



