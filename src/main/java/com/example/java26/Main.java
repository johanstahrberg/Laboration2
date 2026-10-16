package com.example.java26;

import java.time.LocalDate;
import java.util.List;

public class Main {
    static void main() {

        Repository<Transaction> repository = new Repository<>();
        BudgetCalculator calculator = new BudgetCalculator();

        boolean running = true;

        do {
            printMenu();
            String choice = IO.readln("Välj ett alternativ: ");

            switch (choice) {
                case "1" -> {
                    String dateText = IO.readln("Datum (ÅÅÅÅ-MM-DD): ");
                    String category = IO.readln("Kategori: ");
                    String amountText = IO.readln("Belopp: ");
                    double amount = Double.parseDouble(amountText);
                    LocalDate date = LocalDate.parse(dateText);

                    String typeText = IO.readln("Typ (INCOME/EXPENSE): ");
                    TransactionType type = TransactionType.valueOf(typeText);

                    Transaction transaction = new Transaction(date, category, amount, type);
                    repository.add(transaction);

                    IO.println("Transaktionen har lagts till.");
                }
                case "2" -> {
                    List<Transaction> transactions = repository.findAll();

                    if (transactions.isEmpty()) {
                        IO.println("Det finns inga transaktioner.");
                    }

                    for (Transaction transaction : transactions) {
                        IO.println(transaction);
                    }
                }
                case "3" -> {
                    List<Transaction> transactions = repository.findAll();
                    double balance = calculator.calculateBalance(transactions);
                    IO.println("Saldo: " + balance);
                    String category = IO.readln("Kategori att summera: ");
                    double categoryTotal = calculator.calculateCategoryTotal(transactions, category);
                    IO.println("Summa för " + category + ": " + categoryTotal);
                }
                case "4" -> {
                    String typeText = IO.readln("Typ att filtrera på (INCOME/EXPENSE): ");
                    TransactionType type = TransactionType.valueOf(typeText);
                    List<Transaction> result = repository.findWhere(
                            transaction -> transaction.type() == type
                    );

                    if (result.isEmpty()) {
                        IO.println("Inga transaktioner hittades.");
                    }

                    for (Transaction transaction : result) {
                        IO.println(transaction);
                    }
                }
                case "e" -> {
                    running = false;
                }
                default -> {
                    IO.println("Ogiltigt val.");
                }
            }

        } while (running);



    }

    static void printMenu() {
        IO.println("Budgethanteraren");
        IO.println("=================");
        IO.println("1. Lägg till transaktion");
        IO.println("2. Visa alla transaktioner");
        IO.println("3. Visa saldo och kategorisumma");
        IO.println("4. Filtrera transaktioner");
        IO.println("e. Avsluta");
    }


}
