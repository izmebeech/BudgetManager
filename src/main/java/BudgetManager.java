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

        Transaction salary = new Transaction(
                LocalDate.now(),
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
