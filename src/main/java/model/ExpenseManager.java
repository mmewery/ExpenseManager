package model;

import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private final List<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void removeExpense(Expense expense) {
        expenses.remove(expense);
    }

    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    public void setExpenses(List<Expense> loadedExpenses) {
        expenses.clear();
        expenses.addAll(loadedExpenses);
    }

}
