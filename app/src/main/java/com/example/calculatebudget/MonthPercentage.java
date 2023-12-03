package com.example.calculatebudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.calculatebudget.operationsBudget.OperationsExpense;

import java.io.File;

public class MonthPercentage extends AppCompatActivity {
    private OperationsExpense operationsExpense;
    private SharedPreferences persent ;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_percentage);
        this.context = this;
        isPercCreate();
        persent = getSharedPreferences("Persent", Context.MODE_PRIVATE);
        createOperationsExpense(context);
        savePercentMonth();
        PercShowFun();
    }

    private void PercShowFun(){
        String key ="month";
        TextView textPercM = findViewById(R.id.textViewForMonth);
        if(isPercCreate()){

            int savedPers = persent.getInt(key, 0);
            String budgetShow = String.valueOf(savedPers);
            textPercM.setText("Ви витратили " + budgetShow + " %");
            setTextViewColor(textPercM,savedPers);
        }
    }
    private boolean isPercCreate(){
        File file = new File(getFilesDir().getParent() + "/shared_prefs/Persent.xml");
        if(file.exists()) {
            return true;
        } else return false;
    }
    private void savePercentMonth() {
        String key = "month";
        int value = operationsExpense.percentageMonth();
        SharedPreferences.Editor expenseEditor = persent.edit();
        expenseEditor.putInt(key, value);
        expenseEditor.apply();
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