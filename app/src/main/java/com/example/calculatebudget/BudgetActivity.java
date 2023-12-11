package com.example.calculatebudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculatebudget.budget.Budget;

import java.io.File;
import java.util.Calendar;


public class BudgetActivity extends AppCompatActivity {
    private Budget budgetForM;
    private Button buttonAddBudget;
    private Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        budgetForM = new Budget(this);
        buttonAddBudget = findViewById(R.id.buttonAddBudget);
        budgetShowFun();
        checkAndUpdateButton();
    }
    @Override
    protected void onStart() {
        super.onStart();
        budgetShowFun();

    }
    @Override
    protected void onResume() {
        super.onResume();
        budgetShowFun();
    }

    public void addBudgetClick(View view){

        EditText editText = findViewById(R.id.addBudget);
        String chek = editText.getText().toString().trim();

        if (chek.isEmpty() || !isNumeric(chek)) {
            Toast.makeText(this, "Введіть число", Toast.LENGTH_SHORT).show();
            return;
        }

        double number = Double.parseDouble(chek);
        budgetForM.setBudget(number);
        TextView textView = findViewById(R.id.showBudget);
        String text = String.valueOf(budgetForM.getBudget());
        textView.setText(text);
        buttonAddBudget.setClickable(false);
        editText.setText("");
    }
    public void changeBudgetClick(View view){
        EditText editText = findViewById(R.id.addBudget);
        String chek = editText.getText().toString().trim();

        if (chek.isEmpty() || !isNumeric(chek)) {
            Toast.makeText(this, "Введіть число", Toast.LENGTH_SHORT).show();
            return;
        }

        double number = Double.parseDouble(chek);
        budgetForM.setBudget(number);
        TextView textView = findViewById(R.id.showBudget);
        String text = String.valueOf(budgetForM.getBudget());
        textView.setText(text);
        editText.setText("");

    }

    private boolean isNumeric(String str) {
        return str.matches("^[0-9]+(\\.[0-9]+)?$");
    }

    private boolean isBudgetCreate(){
        File file = new File(getFilesDir().getParent() + "/shared_prefs/BudgetPrefs.xml");
        boolean flag = false;
        if(file.exists()) {
            SharedPreferences sharedPreferences = getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE);
            if(sharedPreferences.contains("ITS_BUDGET")){
                flag = true;
            }
        }
        return flag;
    }
    private void budgetShowFun(){
        if(isBudgetCreate()){
            TextView showBudget = findViewById(R.id.showBudget);
            SharedPreferences sharedPreferences = getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE);
            double savedBudget = sharedPreferences.getFloat("ITS_BUDGET", 0.0f);
            String budgetShow = String.valueOf(savedBudget);
            showBudget.setText("Ваш бюджет = " + budgetShow);
            buttonAddBudget.setClickable(false);
        }
    }

    private void checkAndUpdateButton() {
        SharedPreferences sharedPreferences = getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE);
        int savedMonth=sharedPreferences.getInt("CURRENT_MONTH", -1);
        int currentMonth = calendar.get(Calendar.MONTH);

        if (savedMonth != currentMonth) {
            buttonAddBudget.setClickable(true);
        }
    }
}