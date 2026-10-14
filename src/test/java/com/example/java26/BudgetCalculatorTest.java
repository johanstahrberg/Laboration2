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


    @Test
    void shouldCalculateCategoryTotal() {

        BudgetCalculator calculator = new BudgetCalculator();

        Transaction food1 = new Transaction(
                LocalDate.now(),
                "Mat",
                250.0,
                TransactionType.EXPENSE
        );

        Transaction food2 = new Transaction(
                LocalDate.now(),
                "Mat",
                500.0,
                TransactionType.EXPENSE
        );

        Transaction rent = new Transaction(
                LocalDate.now(),
                "Hyra",
                7000.0,
                TransactionType.EXPENSE
        );

        Transaction salary = new Transaction(
                LocalDate.now(),
                "Lön",
                20000.0,
                TransactionType.INCOME
        );

        List<Transaction> transactions = List.of(food1, food2, salary, rent);

        double result = calculator.calculateCategoryTotal(transactions, "Mat");

        assertEquals(750.0, result);

    }


    @Test
    void shouldReturnZeroWhenCategoryDoesNotExist() {

        BudgetCalculator calculator = new BudgetCalculator();

        Transaction food = new Transaction(
                LocalDate.now(),
                "Mat",
                250.0,
                TransactionType.EXPENSE
        );

        List<Transaction> transactions = List.of(food);

        double result = calculator.calculateCategoryTotal(transactions, "Nöje");

        assertEquals(0.0, result);

    }


}
