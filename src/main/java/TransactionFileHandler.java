import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.charset.StandardCharsets;

public class TransactionFileHandler {
    private static final Logger LOGGER =
            Logger.getLogger(TransactionFileHandler.class.getName());

    private final Path filePath;

    public TransactionFileHandler() {
        String homeFolder = System.getProperty("user.home");
        Path homePath = Path.of(homeFolder);
        Path savePath = homePath.resolve("BudgetTransactions");
        this.filePath = savePath.resolve("transactions.txt");

        try {
            Files.createDirectories(savePath);
            LOGGER.info("Save directory created: " + savePath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not create save directory.", e);
        }
    }

    public void saveTransactions(List<Transaction> transactions) {
        List<String> lines = transactions.stream()
                .map(transaction -> String.join(";",
                        transaction.date().toString(),
                        transaction.category(),
                        Double.toString(transaction.amount()),
                        transaction.type().name()))
                .toList();

        // Creates the file, or replaces its contents with the current transaction history
        try {
            Files.write(filePath, lines, StandardCharsets.UTF_8);
            LOGGER.info("Transactions saved successfully.");
        } catch (IOException e) {
            // Logs an error if saving fails instead of crashing
            LOGGER.log(Level.SEVERE, "Could not save transactions.", e);
        }
    }

    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        if (!Files.exists(filePath)) {
            LOGGER.info("No saved transactions found. Starting with an empty history.");
            return transactions;
        }

        try {
            // Read all lines from the file
            List<String> lines = Files.readAllLines(
                    filePath, StandardCharsets.UTF_8);

            // Go through each line and recreate the transaction
            for (String line : lines) {
                try {
                    String[] parts = line.split(";", -1);
                    if (parts.length != 4) { // Check that the line contains all four fields
                        LOGGER.warning("Malformation found, skipping line: " + line);
                        continue;
                    }

                    LocalDateTime date = LocalDateTime.parse(parts[0]);
                    String category = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    TransactionType type = TransactionType.valueOf(parts[3]);

                    // Create a transaction and add it to the list
                    Transaction transaction =
                            new Transaction(date, category, amount, type);

                    transactions.add(transaction);

                } catch (RuntimeException e) {
                    // Skip the line if something is wrong with the data
                    LOGGER.log(Level.WARNING,
                            "Malformation found in transaction line: " + line, e);
                }
            }
        } catch (IOException e) {
            // Log an error if the file can't be read
            LOGGER.log(Level.SEVERE, "Could not load transactions.", e);
        }

        // Return the transactions that were loaded successfully
        return transactions;
    }
}