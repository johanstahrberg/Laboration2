package com.example.java26;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetCalculatorTest {


    @Test
    void shouldCalculateBalance() {

        BudgetCalculator calculator = new BudgetCalculator();

        Transaction income = new Transaction(
                LocalDate.now(),
                "Lön",
                20000.0,
                TransactionType.INCOME
        );

        Transaction expense = new Transaction(
                LocalDate.now(),
                "Mat",
                5000.0,
                TransactionType.EXPENSE
        );

        List<Transaction> transactions = List.of(income, expense);

        double result = calculator.calculateBalance(transactions);

        assertEquals(15000.0, result);

    }


    @Test
    void shouldReturnZeroForEmptyTransactionList() {

        BudgetCalculator calculator = new BudgetCalculator();

        List<Transaction> transactions = List.of();

        double result = calculator.calculateBalance(transactions);

        assertEquals(0.0, result);

    }


}
