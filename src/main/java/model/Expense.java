package model;

import java.time.LocalDate;

public class Expense {
    private String category;
    private double amount;
    private LocalDate date;
    private String comment;

    public Expense() {
    }

    public Expense(String category, double amount, LocalDate date, String comment) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.comment = comment;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
