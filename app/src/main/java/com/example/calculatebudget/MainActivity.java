package com.example.calculatebudget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
// додати перевірку місяця (інший місяць =  всі дані стають 0 в activity main )

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    @Override
    protected void onStart() {
        super.onStart();
        budgetShowFun();

    }

    public void startBudgetActivity(View view){
        Intent intent = new Intent(this, BudgetActivity.class);
        startActivity(intent);
    }

    public void startExpenseActivity(View view){
        Intent intent = new Intent(this, ExpenseActivity.class);
        startActivity(intent);
    }

    public void startWeekPercentage(View view){
        Intent intent = new Intent(this, WeekPercentage.class);
        startActivity(intent);
    }
    public void startMonthPercentage(View view){
        Intent intent = new Intent(this, MonthPercentage.class);
        startActivity(intent);
    }
    private boolean isBudgetCreate(){
        File file = new File(getFilesDir().getParent() + "/shared_prefs/BudgetPrefs.xml");
        if(file.exists()) {
            return true;
        } else return false;
    }
    private void budgetShowFun(){
        TextView textBudget = findViewById(R.id.textBudget);
        if(isBudgetCreate()){
            SharedPreferences sharedPreferences = getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE);
            double savedBudget = sharedPreferences.getFloat("ITS_BUDGET", 0.0f);
            String budgetShow = String.valueOf(savedBudget);
            textBudget.setText("Ваш бюджет = " + budgetShow);
        }
    }
}