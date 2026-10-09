import java.time.LocalDate;
import java.time.LocalDateTime;

public class BudgetManager {

    static void main() {
        Repository<Transaction> repository = new Repository<>();

        Transaction transaction = new Transaction(
                LocalDateTime.now(),
                "Phone bill",
                399,
                TransactionType.SPENDING
        );
        repository.add(transaction);

        Transaction salary = new Transaction(
                LocalDateTime.now(),
                "Salary",
                53000,
                TransactionType.INCOME
        );

        repository.add(salary);

        IO.println(repository.findAll());


        IO.println(repository.findWhere(t ->
                transaction.type() == TransactionType.SPENDING
        ));


    }
}
