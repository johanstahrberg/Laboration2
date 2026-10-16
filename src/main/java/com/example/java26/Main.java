package com.example.java26;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    static void main() {

        Repository<Transaction> repository = new Repository<>();
        BudgetCalculator calculator = new BudgetCalculator();

        boolean running = true;

        do {
            printMenu();
            String choice = IO.readln("Välj ett alternativ: ");

            switch (choice) {
                case "1" -> {
                    try {
                    String dateText = IO.readln("Datum (ÅÅÅÅ-MM-DD): ");
                    LocalDate date = LocalDate.parse(dateText);
                    String category = IO.readln("Kategori: ");
                    String amountText = IO.readln("Belopp: ");
                    double amount = Double.parseDouble(amountText);


                    String typeText = IO.readln("Typ (INCOME/EXPENSE): ");
                    TransactionType type = TransactionType.valueOf(typeText);

                    Transaction transaction = new Transaction(date, category, amount, type);
                    repository.add(transaction);

                    IO.println("Transaktionen har lagts till.");
                    }
                    catch (DateTimeParseException e) {
                        IO.println("Ogiltigt datum.");
                        LOGGER.warning("Ogiltigt datum angavs.");
                    }
                    catch (NumberFormatException e) {
                        IO.println("Ogiltigt belopp.");
                        LOGGER.warning("Ogiltigt belopp angavs.");
                    }
                    catch (InvalidTransactionException e) {
                        IO.println(e.getMessage());
                        LOGGER.warning("Ogiltig transaktion: " + e.getMessage());
                    }
                    catch (IllegalArgumentException e) {
                        IO.println("Ogiltig typ. Skriv INCOME eller EXPENSE.");
                        LOGGER.warning("Ogiltig transaktionstyp angavs.");
                    }
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
                    try {
                        String typeText = IO.readln("Typ att filtrera på (INCOME/EXPENSE): ");
                        TransactionType type = TransactionType.valueOf(typeText);
                        List<Transaction> result = repository.findWhere(
                                transaction -> transaction.type() == type
                        );

                        result.sort(Comparator.comparing(Transaction::date).reversed());

                        if (result.isEmpty()) {
                            IO.println("Inga transaktioner hittades.");
                        }

                        for (Transaction transaction : result) {
                            IO.println(transaction);
                        }

                    }
                    catch (IllegalArgumentException e) {
                        IO.println("Ogiltig typ. Skriv INCOME eller EXPENSE.");
                        LOGGER.warning("Ogiltig transaktionstyp vid filtrering.");
                    }
                }
                case "e" -> {
                    running = false;
                }
                default -> {
                    IO.println("Ogiltigt val.");
                    LOGGER.warning("Ogiltigt menyval angavs.");
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
