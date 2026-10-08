import java.time.LocalDate;

public class BudgetManager {

    static void main() {
        Repository<Transaction> repository = new Repository<>();

        Transaction transaction = new Transaction(
                LocalDate.now(),
                "Phone bill",
                399,
                TransactionType.SPENDING
        );
        repository.add(transaction);

    }
}
