package com.example.java26;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final Path FILE_PATH =
            Path.of(System.getProperty("user.home"), "java26", "transactions.csv");

    static void main() {

        Repository<Transaction> repository = new Repository<>();
        BudgetCalculator calculator = new BudgetCalculator();
        loadTransactions(repository);

        try {
            Files.createDirectories(FILE_PATH.getParent());
        } catch (IOException e) {
            LOGGER.warning("Kunde inte skapa mappen för transaktionsfilen.");
        }

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
                    saveTransactions(repository);
                    running = false;
                }
                default -> {
                    IO.println("Ogiltigt val.");
                    LOGGER.warning("Ogiltigt menyval angavs.");
                }
            }

        } while (running);


    }



    static void loadTransactions(Repository<Transaction> repository) {
        if (!Files.exists(FILE_PATH)) {
            return;
        }

        try {
            List<String> lines = Files.readAllLines(FILE_PATH);

            for (String line : lines) {
                try {
                    String[] parts = line.split(";");
                    LocalDate date = LocalDate.parse(parts[0]);
                    String category = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    TransactionType type = TransactionType.valueOf(parts[3]);

                    Transaction transaction = new Transaction(date, category, amount, type);
                    repository.add(transaction);
                } catch (RuntimeException e) {
                    LOGGER.warning("Ogiltig rad i transaktionsfilen: " + line);
                }
            }

        }
        catch (IOException e) {
            LOGGER.warning("Kunde inte läsa transaktionsfilen.");
        }
    }



    static void saveTransactions(Repository<Transaction> repository) {
        List<Transaction> transactions = repository.findAll();
        List<String> lines = new ArrayList<>();

        for (Transaction transaction : transactions) {
            String line = transaction.date() + ";"
                    + transaction.category() + ";"
                    + transaction.amount() + ";"
                    + transaction.type();

            lines.add(line);
        }

        String content = String.join(System.lineSeparator(), lines);

        try {
            Files.writeString(FILE_PATH, content);
        } catch (IOException e) {
            LOGGER.warning("Kunde inte spara transaktionerna.");
        }

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
