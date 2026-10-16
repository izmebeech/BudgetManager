import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {
    //check if an item can be stored and retrieved
    @Test
    void findAllAndAddReturnsStoredTransaction() {
        Repository<Transaction> repository = new Repository<>();
        Transaction transaction = new Transaction(
                LocalDateTime.of(2026, 10, 9, 14, 35),
                "Phone bill",
                399,
                TransactionType.EXPENSE);

        repository.add(transaction);
        List<Transaction> transactions = repository.findAll();

        assertEquals(1, transactions.size());
        assertEquals(transaction, transactions.getFirst());
    }


    //check whether the repository can distinguish between matching and non matching items
    @Test
    void findWhereReturnsOnlyMatchingTransactions() {
        Repository<Transaction> repository = new Repository<>();
        Transaction phoneBill = new Transaction(
                LocalDateTime.of(2026, 10, 9, 14, 35),
                "Phone bill",
                399,
                TransactionType.EXPENSE);

        Transaction salary = new Transaction(
                LocalDateTime.of(2026, 10, 9, 15, 0),
                "Salary",
                53000,
                TransactionType.INCOME);

        repository.add(salary);
        repository.add(phoneBill);

        List<Transaction> result = repository.findWhere(
                t -> t.type() == TransactionType.EXPENSE);
        assertEquals(1, result.size());  //the filtered list must contain exactly one item
        assertEquals(phoneBill, result.get(0));   // said item must be the phone bill
    }

    @Test
    void findAllReturnsEmptyListWhenRepositoryIsEmpty() {
        Repository<Transaction> repository = new Repository<>();
        List<Transaction> transactions = repository.findAll();

        assertTrue(transactions.isEmpty());
    }

    @Test
    void findWhereReturnsEmptyListWhenNoTransactionsMatch() {
        Repository<Transaction> repository = new Repository<>();
        List<Transaction> result = repository.findWhere(
                t -> t.type() == TransactionType.EXPENSE);

        assertTrue(result.isEmpty());
    }
    @Test
    void findAllReturnsUnmodifiableList() {
        Repository<Transaction> repository = new Repository<>();
        Transaction transaction = new Transaction(
                LocalDateTime.of(2026, 10, 9, 14, 35),
                "Phone bill",
                399,
                TransactionType.EXPENSE);

        repository.add(transaction);
        List<Transaction> transactions = repository.findAll();

        assertThrows(UnsupportedOperationException.class, transactions::clear);
        assertEquals(1, repository.findAll().size());
    }
}