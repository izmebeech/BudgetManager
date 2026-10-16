import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    void calculateTotalIncomeReturnsSumTotalIncome() {
        Repository<Transaction> repository = new Repository<>();
        Transaction salary = new Transaction(
                LocalDateTime.of(2026, 10, 9, 9, 0),
                "Salary",
                30000,
                TransactionType.INCOME);

        Transaction phoneBill = new Transaction(
                LocalDateTime.of(2026, 10, 9, 10, 0),
                "Phone bill",
                399,
                TransactionType.EXPENSE);

        repository.add(salary);
        repository.add(phoneBill);

        Calculator calculator = new Calculator(repository);

        double result = calculator.calculateTotalIncome();

        assertEquals(30000.0, result, 0.001);
    }

    @Test
    void calculateTotalsByCategoryReturnsSumsForEachCategory() {
        Repository<Transaction> repository = new Repository<>();
        Transaction lunch = new Transaction(
                LocalDateTime.of(2026, 10, 8, 12, 5),
                "Food",
                149,
                TransactionType.EXPENSE);

        Transaction groceries = new Transaction(
                LocalDateTime.of(2026, 10, 8, 18, 39),
                "Food",
                487,
                TransactionType.EXPENSE);

        Transaction busTicket = new Transaction(
                LocalDateTime.of(2026, 10, 8, 18, 45),
                "Transport",
                60,
                TransactionType.EXPENSE);

        repository.add(lunch);
        repository.add(groceries);
        repository.add(busTicket);

        Calculator calculator = new Calculator(repository);
        Map<String, Double> totals = calculator.calculateTotalsByCategory();
        assertEquals(636.0, totals.get("Food"), 0.001);
        assertEquals(60.0, totals.get("Transport"), 0.001);

    }

    @Test
    void calculateBalanceReturnsAccountBalance() {
        Repository<Transaction> repository = new Repository<>();
        Transaction salary = new Transaction(
                LocalDateTime.of(2026, 10, 9, 9, 0),
                "Salary",
                30000,
                TransactionType.INCOME);

        Transaction phoneBill = new Transaction(
                LocalDateTime.of(2026, 10, 9, 10, 0),
                "Phone bill",
                399,
                TransactionType.EXPENSE);

        repository.add(salary);
        repository.add(phoneBill);

        Calculator calculator = new Calculator(repository);
        double result = calculator.calculateBalance();
        assertEquals(29601.0, result, 0.001);

    }

    @Test
    void calculateBalanceReturnsZeroWhenRepositoryIsEmpty() {
        Repository<Transaction> repository = new Repository<>();
        Calculator calculator = new Calculator(repository);

        assertEquals(0.0, calculator.calculateBalance());
    }

    @Test
    void calculateTotalSpendingReturnsSumOfExpenses() {
        Repository<Transaction> repository = new Repository<>();
        Transaction salary = new Transaction(
                LocalDateTime.of(2026, 10, 9, 9, 0),
                "Salary",
                30000,
                TransactionType.INCOME);

        Transaction phoneBill = new Transaction(
                LocalDateTime.of(2026, 10, 9, 10, 0),
                "Phone bill",
                399,
                TransactionType.EXPENSE);

        repository.add(salary);
        repository.add(phoneBill);

        Calculator calculator = new Calculator(repository);
        assertEquals(399.0, calculator.calculateTotalSpending(), 0.001);
    }

    @Test
    void calculateTotalSpendingReturnsZeroWhenRepositoryIsEmpty() {
        Repository<Transaction> repository = new Repository<>();
        Calculator calculator = new Calculator(repository);

        assertEquals(0.0, calculator.calculateTotalSpending());
    }

    @Test
    void calculateTotalsByCategoryReturnsEmptyMapWhenRepositoryIsEmpty() {
        Repository<Transaction> repository = new Repository<>();
        Calculator calculator = new Calculator(repository);
        Map<String, Double> result = calculator.calculateTotalsByCategory();

        assertTrue(result.isEmpty());
    }

}