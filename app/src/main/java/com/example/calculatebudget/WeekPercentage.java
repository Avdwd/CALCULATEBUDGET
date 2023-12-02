package com.example.calculatebudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;

public class WeekPercentage extends AppCompatActivity {
    private SharedPreferences persent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_percentage);
    }
    @Override
    protected void onStart() {
        PercShowFun("1");
        PercShowFun("2");
        PercShowFun("3");
        PercShowFun("4");
        PercShowFun("5");
        super.onStart();
    }


    private boolean isPercCreate(){
        File file = new File(getFilesDir().getParent() + "/shared_prefs/Persent.xml");
        if(file.exists()) {
            return true;
        } else return false;
    }

    private void PercShowFun(String week){
        String key = week;
        int weekNow = Integer.parseInt(week);
        TextView textPercWeek = getTextForPers(weekNow);
        if(isPercCreate()){
            SharedPreferences sharedPreferences = getSharedPreferences("Persent", Context.MODE_PRIVATE);
            int savedPers = sharedPreferences.getInt(week, 0);
            String budgetShow = String.valueOf(savedPers);
            textPercWeek.setText("Ваші витрати за цей тиджень " + budgetShow + " % від бюджету");
        }
    }



    private TextView getTextForPers(int week) {
        TextView percentWeek = null;
        switch (week) {
            case 1:
                percentWeek = findViewById(R.id.week1);
                break;
            case 2:
                percentWeek = findViewById(R.id.week2);
                break;
            case 3:
                percentWeek = findViewById(R.id.week3);
                break;
            case 4:
                percentWeek = findViewById(R.id.week4);
                break;
            case 5:
                percentWeek = findViewById(R.id.week5);
                break;

        }
        return percentWeek;
    }


}