package com.example.java26;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class RepositoryTest {


    @Test
    void shouldAddTransaction() {

        Repository<Transaction> repository = new Repository<>();


        Transaction transaction = new Transaction(
                LocalDate.now(),
                "Mat",
                250.0,
                TransactionType.EXPENSE
        );

        repository.add(transaction);

        assertEquals(1, repository.findAll().size());

    }

    @Test
    void shouldReturnAllTransactions() {

        Repository<Transaction> repository = new Repository<>();

        Transaction firstTransaction = new Transaction(
                LocalDate.now(),
                "Mat",
                250.0,
                TransactionType.EXPENSE
        );

        Transaction secondTransaction = new Transaction(
                LocalDate.now(),
                "Lön",
                20000.0,
                TransactionType.INCOME
        );

        repository.add(firstTransaction);
        repository.add(secondTransaction);

        assertEquals(2, repository.findAll().size());
    }

    @Test
    void shouldFindMatchingTransactions() {

        Repository<Transaction> repository = new Repository<>();

        Transaction expenseTransaction = new Transaction(
                LocalDate.now(),
                "Mat",
                250.0,
                TransactionType.EXPENSE
        );

        Transaction incomeTransaction = new Transaction(
                LocalDate.now(),
                "Lön",
                20000.0,
                TransactionType.INCOME
        );

        repository.add(expenseTransaction);
        repository.add(incomeTransaction);

        List<Transaction> result = repository.findWhere(
                transaction -> transaction.type() == TransactionType.EXPENSE
        );

        assertEquals(1, result.size());

    }

}
