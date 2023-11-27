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

    private boolean isPercCreate(){
        File file = new File(getFilesDir().getParent() + "/shared_prefs/Persent.xml");
        if(file.exists()) {
            return true;
        } else return false;
    }

    private void PercShowFun(String week){
        String key = week;
        //TextView textBudget = findViewById(R.id. /*це замінити на текствью*/ );  //оце взяти
        if(isPercCreate()){
            SharedPreferences sharedPreferences = getSharedPreferences("ExpensePrefs", Context.MODE_PRIVATE);
            int savedPers = sharedPreferences.getInt(week, 0);
            String budgetShow = String.valueOf(savedPers);
            //textBudget.setText("Ваші витрати за цей тиджень" + budgetShow + "від бюджету");// і оце взяти і записати в ще один метод
        }
    }



}