package com.example.calculatebudget;



import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.calculatebudget.expense.Expense;
import com.example.calculatebudget.operationsBudget.OperationsExpense;

import java.io.File;

public class WeekPercentage extends AppCompatActivity {
    private OperationsExpense operationsExpense;
    private SharedPreferences persent ;

    private Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_percentage);
        this.context = this;
        checkOrCreatePerc();
        persent = getSharedPreferences("Persent", Context.MODE_PRIVATE);
        createOperationsExpense(context);
        checkAndSavePercentData();
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

            int savedPers = persent.getInt(key, 0);
            String budgetShow = String.valueOf(savedPers);
            textPercWeek.setText("Ваші витрати за цей тиждень " + budgetShow + " % від бюджету");
            setTextViewColor(textPercWeek, savedPers);
        }
    }

    private void createOperationsExpense(Context context){
        operationsExpense = new OperationsExpense(context) {
            @Override
            public double expenseAddition(double expense) {
                return 0;
            }

            @Override
            public double allWeekExpense() {
                return 0;
            }
        };
    }

    private void checkAndSavePercentData() {
        SharedPreferences persent = getSharedPreferences("Persent", Context.MODE_PRIVATE);
        if (persent != null) {
            savePercentOFWeek(persent);
        }
    }

    private void savePercentOFWeek(SharedPreferences persent) {
        for (int i = 1; i <= 5; i++) {
            String key = String.valueOf(i);
            int value = operationsExpense.percentageWeak(i);
            SharedPreferences.Editor expenseEditor = persent.edit();
            expenseEditor.putInt(key, value);
            expenseEditor.apply();
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

    private void checkOrCreatePerc() {
        if (!isPercCreate()) {
            this.persent = context.getSharedPreferences("Persent", Context.MODE_PRIVATE);
        }
    }

    private void setTextViewColor(TextView textView, int percentage) {
        int color;

        if (percentage < 30) {
            color = getResources().getColor(R.color.green); // Зелений колір
        } else if (percentage < 60) {
            color = getResources().getColor(R.color.yellow); // Жовтий колір
        } else if (percentage < 80) {
            color = getResources().getColor(R.color.orange); // Помаранчевий колір
        } else if (percentage < 90) {
            color = getResources().getColor(R.color.red); // Червоний колір
        } else {
            color = getResources().getColor(R.color.brick_red); // Кирпично-червоний колір
        }

        textView.setBackgroundColor(color);

    }

}