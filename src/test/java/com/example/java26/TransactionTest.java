package com.example.java26;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {


    @Test
    void shouldThrowExceptionForNegativeAmount() {

        assertThrows(InvalidTransactionException.class, () -> {
            new Transaction(
                    LocalDate.now(),
                    "Mat",
                    -100.0,
                    TransactionType.EXPENSE
            );
        });

    }


    @Test
    void shouldAllowZeroAmount() {

        Transaction transaction = new Transaction(
                LocalDate.now(),
                "Mat",
                0.0,
                TransactionType.EXPENSE
        );

        assertEquals(0.0, transaction.amount());


    }




}
