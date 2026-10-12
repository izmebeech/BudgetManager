import java.util.Map;
import java.util.stream.Collectors;

public class Calculator {

    private final Repository<Transaction> repository;

    public Calculator(Repository<Transaction> repository) {
        this.repository = repository;
    }

    // receives transaction -> stream, keeps only INCOME-> extract amounts and adds them together
    public double calculateTotalIncome() {
        return repository.findAll().stream()
                .filter(t -> t.type() == TransactionType.INCOME)
                .mapToDouble(Transaction::amount)
                .sum();
    }

    public double calculateTotalSpending() {
        return repository.findAll().stream()
                .filter(t -> t.type() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::amount)
                .sum();
    }

    public double calculateBalance() {
        return calculateTotalIncome() - calculateTotalSpending();
    }

    // groups transactions of same category together, adds the amounts, maps holds total result of each category name
    public Map<String, Double> calculateTotalsByCategory() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Transaction::category,
                        Collectors.summingDouble(Transaction::amount)
                ));
    }
}
