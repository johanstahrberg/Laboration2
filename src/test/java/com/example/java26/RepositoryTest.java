package com.example.java26;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
