package com.example.calculatebudget.expense;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.calculatebudget.operationsBudget.OperationsExpense;


import java.util.Calendar;


public class Expense extends OperationsExpense {
    private double expense;// витрати
    private Calendar calendar = Calendar.getInstance(); // отримання календаря
    private int WeekOfMonth ;//для тижня
    private SharedPreferences Expense;//для даних витрат в пам'яті
    private Context context;//для створення файлу та доступу до нього

    public Expense(Context context){
        //конструктор
        this.context = context;
        this.Expense = context.getSharedPreferences("ExpensePrefs", Context.MODE_PRIVATE);// назва файлу
        this.expense = getExpense();
    }
    public void  setExpense(double expense){//сетер
        this.expense = expense;
        saveExpense();
    }

    public double getExpense(){return getValueExpense();}//гетер

    private float getValueExpense(){//отримання значення на даний тиждень
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
        double allWeekEx = 0;
        for(int i = 1;i<=5;i++){
            allWeekEx = allWeekEx + getValueExpenseForWeek(i);
        }
        return allWeekEx;
    }

    private boolean hasExpensesForCurrentWeek() {//перевірка на наявність витрат
        String currentWeekKey = getWeekOfMonth();
        float currentWeekExpense = getValueExpenseForWeek(Integer.parseInt(currentWeekKey));
        return currentWeekExpense > 0;
    }

    private float getValueExpenseForWeek(int week){//отриманя значення для певного тижня
        String key = String.valueOf(week);
        Expense = context.getSharedPreferences("ExpensePrefs", Context.MODE_PRIVATE);
        float value;
        if (Expense != null) {
            value  =  Expense.getFloat(key, 0f);
            return value;
        } else {
            return 0;
        }
    }
    private String getWeekOfMonth(){//отримання тижня

        WeekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
        String key = String.valueOf(WeekOfMonth);

        return key;
    }

}
