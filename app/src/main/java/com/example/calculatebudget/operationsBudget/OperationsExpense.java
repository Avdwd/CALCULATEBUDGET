package com.example.calculatebudget.operationsBudget;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class OperationsExpense {

    private SharedPreferences EXPENCE;
    private Context CONTEXT;
    protected OperationsExpense() {
    }
    public OperationsExpense(Context context) {
        this.CONTEXT = context;
    }



    public abstract double expenseAddition(double expense);
    public abstract double allWeekExpense();
    public int percentageWeak(int week){//проценти за тиждень(вход з тижнем)

        float valueFWeek = getValueExpenseForWeek(week);
        double savedBudget = getValBudget();
        int percentage = (int) ((valueFWeek*100)/savedBudget);

        return percentage;
    }

    public int percentageMonth(){//процент за всі тижні відносно бюджету
        float valueMonth = allWeek();
        double savedBudget = getValBudget();
        int percent = (int)(valueMonth*100/savedBudget);
        return percent;
    }

    private double getValBudget(){// значення бюджету
        SharedPreferences sharedPreferences = CONTEXT.getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE);
        double savedBudget = sharedPreferences.getFloat("ITS_BUDGET", 0.0f);
        return savedBudget;
    }

    private float getValueExpenseForWeek(int week){
        String key = String.valueOf(week);
        EXPENCE = CONTEXT.getSharedPreferences("ExpensePrefs", Context.MODE_PRIVATE);
        float value;
        if (EXPENCE != null) {
            value  =  EXPENCE.getFloat(key, 0f);
            return value;
        } else {
            return 0;
        }
    }

    private float allWeek(){
        float all = 0;

        for(int i=1;i<=5;i++){
            all+=getValueExpenseForWeek(i);
        }
        return all;
    }

}
