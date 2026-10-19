import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            LOGGER.info("Save directory is ready: " + savePath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not create save directory.", e);
        }
    }

    public void saveTransactions(List<Transaction> transactions) {
        // Implement saving
    }
}