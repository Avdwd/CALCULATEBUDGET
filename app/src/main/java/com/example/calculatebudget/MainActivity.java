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
import java.util.Calendar;
// додати перевірку місяця (інший місяць =  всі дані стають 0 в activity main )

public class MainActivity extends AppCompatActivity {
    private Calendar calendar = Calendar.getInstance(); // отримання календаря
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndUpdateMonth();
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


    // можна пізніше перенести це все в інший клас наприклад в бюджет а також операції з датами також в інший клас


    //скидування даних при зміні місяця
    private void checkAndUpdateMonth() {
        SharedPreferences sharedPreferences = getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE);
        int savedMonth;

        if (sharedPreferences.contains("CURRENT_MONTH")) {
            savedMonth = sharedPreferences.getInt("CURRENT_MONTH", -1);
        } else {
            savedMonth = calendar.get(Calendar.MONTH);
            updateCurrentMonth(savedMonth);
        }
        int currentMonth = calendar.get(Calendar.MONTH);

        if (savedMonth != currentMonth) {
            // Якщо місяць не співпадає, обнулити дані та оновити місяць
            resetAllData();
            updateCurrentMonth(currentMonth);
        }
    }

    private void resetAllData() {
        SharedPreferences.Editor budgetEditor = getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE).edit();
        budgetEditor.clear();
        budgetEditor.apply();
        SharedPreferences.Editor expenseEditor = getSharedPreferences("ExpensePrefs", Context.MODE_PRIVATE).edit();
        expenseEditor.clear();
        expenseEditor.apply();
        SharedPreferences.Editor percentEditor = getSharedPreferences("Persent", Context.MODE_PRIVATE).edit();
        percentEditor.clear();
        percentEditor.apply();
    }

    private void updateCurrentMonth(int currentMonth) {
        SharedPreferences.Editor editor = getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE).edit();
        editor.putInt("CURRENT_MONTH", currentMonth);
        editor.apply();
    }
}