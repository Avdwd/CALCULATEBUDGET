package com.example.calculatebudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculatebudget.expense.Expense;

import java.io.File;
import java.util.Calendar;

public class ExpenseActivity extends AppCompatActivity {
    private Expense thisExpense;
    private SharedPreferences persent;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        this.context = this;
        thisExpense = new Expense(this);
        checkOrCreatePerc();
        checkAndSavePercentData();
    }
    protected void onStart() {
        super.onStart();
    }
    protected void onPause() {
        checkAndSavePercentData();
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        checkAndSavePercentData();
    }

    public void addExpense(View view){
        EditText editText = findViewById(R.id.editExpense);
        String chek = editText.getText().toString().trim();

        if (chek.isEmpty() || !isNumeric(chek)) {
            Toast.makeText(this, "Введіть число", Toast.LENGTH_SHORT).show();
            return;
        }
        double number = Double.parseDouble(chek);

        thisExpense.setExpense(number);
        editText.setText("");
        Toast.makeText(this, "Збережено", Toast.LENGTH_SHORT).show();
    }

    private boolean isNumeric(String str) {
        return str.matches("^[0-9]+(\\.[0-9]+)?$");
    }

    private boolean isPercCreate(){
        File file = new File(getFilesDir().getParent() + "/shared_prefs/Persent.xml");
        if(file.exists()) {
            return true;
        } else return false;
    }
    private void checkOrCreatePerc(){
        if(!isPercCreate()){
            this.persent = context.getSharedPreferences("Persent", Context.MODE_PRIVATE);
        }
    }
//записти обрахунок тижня
    private void savePerсentOFWeek(){
        for(int i= 1;i<=5;i++){
            String key = String.valueOf(i);
            int percent = thisExpense.percentageWeak(i);
            SharedPreferences.Editor percentEditor = persent.edit();
            percentEditor.putInt(key,percent);
            percentEditor.apply();
        }
    }
    // написати обрахунок місяця
    private void savePercentMonth(){
        String keyMonth = "month";
        int monthPerc = thisExpense.percentageMonth();
        SharedPreferences.Editor percentEditor = persent.edit();
        percentEditor.putInt(keyMonth,monthPerc);
        percentEditor.apply();
    }



    private void checkAndSavePercentData() {
        if (persent != null) {
            savePerсentOFWeek();
            savePercentMonth();
        }
    }

}