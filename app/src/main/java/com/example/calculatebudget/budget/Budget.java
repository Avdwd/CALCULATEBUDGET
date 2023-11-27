package com.example.calculatebudget.budget;

import android.content.Context;
import android.content.SharedPreferences;
// додати перевірку місяця (інший місяць =  всі дані стають 0)
public class Budget {
    private double budget;
    private SharedPreferences setBudget;
    private static final String PREF_BUDG = "ITS_BUDGET"; //ключ до бюджета
    private Context context;

    public Budget(Context context){
        this.context = context;
        this.setBudget = context.getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE);// назва файлу BudgetPrefs
        this.budget = getBudget();
    }

    public void setBudget(double monthBudget){
        this.budget = monthBudget;
        saveBudget();
    }

    public double getBudget(){
        return getValueBudget();
    }

    private float getValueBudget(){
        if (setBudget != null) {
            return setBudget.getFloat(PREF_BUDG, 0);
        } else {
            return 0;
        }
    }

    private void saveBudget(){
        if (setBudget != null) {
            SharedPreferences.Editor budgetEditor = setBudget.edit();
            budgetEditor.putFloat(PREF_BUDG, (float) budget);
            budgetEditor.apply();
        }
    }
}

