import java.time.LocalDateTime;

public record Transaction(
        LocalDateTime date,
        String category,
        double amount,
        TransactionType type
) {

    //Make sure a transaction cant be 0 or below.
    public Transaction {
        if (!Double.isFinite(amount)||amount <= 0) {   //rejects if amount is infinite, 0 or negative
            throw new InvalidTransactionException("Transaction must be greater than zero.");
        }
    }
    public String toDisplayString() {
        // Add + for income and - for expenses
        var signedAmount = type == TransactionType.INCOME ? amount : -amount;

        // Format the values into a readable string
        return String.format(
                "%s | %s | %s | %+.2f kr", // Output layout and number formatting
                date.toLocalDate(),         // Date only
                type,                       // Income or expense
                category,                   // Transaction category
                signedAmount                // Signed amount, 2 decimals
        );
    }
}
