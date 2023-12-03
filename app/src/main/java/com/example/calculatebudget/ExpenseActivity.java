package com.example.calculatebudget;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.calculatebudget.expense.Expense;
import java.io.File;

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

    }


    public void addExpense(View view) {
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

}
