package com.example.calculatebudget.expense;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.calculatebudget.operationsBudget.operationsWithBudget;


import java.util.Calendar;


public class Expense extends operationsWithBudget {
    private double expense;// витрати
    private Calendar calendar = Calendar.getInstance(); // отримання календаря
    private int WeekOfMonth ;//для тижня
    private SharedPreferences Expense;//для даних витрат в пам'яті
    private Context context;//для створення файлу та доступу до нього

    public Expense(Context context){//конструктор
        this.context = context;
        this.Expense = context.getSharedPreferences("ExpensePrefs", Context.MODE_PRIVATE);// назва файлу
        this.expense = getExpense();
    }
    public void  setExpense(double expense){//сетер
        this.expense = expense;
        saveExpense();
    }

    public double getExpense(){return getValueExpense();}//гетер

    private float getValueExpense(){//отримання значення по тижням
        String key = getWeekOfMonth();
        if (Expense != null) {
            return Expense.getFloat(key, 0);
        } else {
            return 0;
        }
    }
    private void saveExpense(){//збереження значення витрати
        String key = getWeekOfMonth();
        if (!hasExpensesForCurrentWeek()) {//якщо на цьому тижні ще не було витрат
            if(Expense != null){
                SharedPreferences.Editor expenseEditor = Expense.edit();
                expenseEditor.putFloat(key , (float) expense);
                expenseEditor.apply();
            }
        } else{//якщо витрати були
            double ex = expense;
            float addedExpense = (float) expenseAddition(ex);
            SharedPreferences.Editor expenseEditor = Expense.edit();
            expenseEditor.putFloat(key , addedExpense);
            expenseEditor.apply();

        }

    }
    @Override
    public double expenseAddition(double expense){//додавання витрат між собою по тижням
        double tempExp = expense;
        tempExp+=getExpense();
        return tempExp;
    }
    @Override
    public double allWeekExpense(){//витрати за всі тижні
        double allWeekEx = 0.0;

        for(int i = 0;i<=4;i++){
            allWeekEx = allWeekEx + getValueExpenseForWeek(i);
        }

        return allWeekEx;
    }
    @Override
    public int percentageWeak(int week){//проценти за тиждень(вход з тижнем)
        int percentage = 0;
        int weekNow = week;
        float valueFWeek;
        double savedBudget = getValBudget();

        valueFWeek = getValueExpenseForWeek(weekNow);

        percentage = (int) (valueFWeek*100/savedBudget);

        return percentage;
    }



    @Override
    public int percentageMonth(){//процент за всі тижні відносно бюджету
        int percent = 0;
        float valueMonth = (float) allWeekExpense();
        double savedBudget = getValBudget();
        percent = (int)(valueMonth*100/savedBudget);
        return percent;
    }


    private boolean hasExpensesForCurrentWeek() {//перевірка на наявність витрат
        String currentWeekKey = getWeekOfMonth();
        float currentWeekExpense = getValueExpenseForWeek(Integer.parseInt(currentWeekKey));
        return currentWeekExpense > 0;
    }

    private float getValueExpenseForWeek(int week){//отриманя значення для певного тижня
        String key = String.valueOf(week);

        if (Expense != null) {
            return Expense.getFloat(key, 0);
        } else {
            return 0;
        }
    }
    private String getWeekOfMonth(){//отримання тижня

        WeekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
        String key = String.valueOf(WeekOfMonth);

        return key;
    }

    private double getValBudget(){// значення бюджету
        SharedPreferences sharedPreferences = context.getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE);
        double savedBudget = sharedPreferences.getFloat("ITS_BUDGET", 0.0f);
        return savedBudget;
    }

}
