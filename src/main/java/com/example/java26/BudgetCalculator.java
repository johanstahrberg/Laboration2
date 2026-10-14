package com.example.java26;

import java.util.List;

public class BudgetCalculator {

    public double calculateBalance(List<Transaction> transactions) {
        double balance = 0;

        for (Transaction transaction : transactions) {
            if (transaction.type() == TransactionType.INCOME) {
                balance += transaction.amount();
            }

            else {
                balance -= transaction.amount();
            }
        }

       return balance;
    }


    public double calculateCategoryTotal(List<Transaction> transactions, String category) {
        double total = 0;

        for (Transaction transaction : transactions) {
            if (transaction.category().equals(category)) {
                total += transaction.amount();
            }

        }

        return total;
    }


}
