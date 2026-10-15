package com.example.java26;

import java.time.LocalDate;

public record Transaction(
        LocalDate date,
        String category,
        double amount,
        TransactionType type
) {

    public Transaction {
        if (amount < 0) {
            throw new InvalidTransactionException("Beloppet får inte vara negativt");
        }

    }
}