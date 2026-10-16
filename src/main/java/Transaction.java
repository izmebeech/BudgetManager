import java.time.LocalDateTime;

public record Transaction(
        LocalDateTime date,
        String category,
        double amount,
        TransactionType type
) {

    //Make sure a transaction cant be 0 or below.
    public Transaction {
        if (amount <= 0) {
            throw new InvalidTransactionException("Transaction must be greater than zero.");
        }
    }
    public String toDisplayString() {
        return String.format(
                "%s | %.2f kr | %s",
                category,
                amount,
                date.toLocalDate()
        );
    }
}
