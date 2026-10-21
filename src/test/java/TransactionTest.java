import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
        void transactionInvalidIfBelowZero() {
            LocalDateTime date = LocalDateTime.now();
        assertThrows(InvalidTransactionException.class, () -> {
            new Transaction(
                    date, "Swish", -14, TransactionType.EXPENSE
            );
        });
        }

    @Test
    void transactionInvalidIfZero() {
        LocalDateTime date = LocalDateTime.now();
        assertThrows(InvalidTransactionException.class, () -> {
            new Transaction(
                    date, "Swish", 0, TransactionType.EXPENSE
            );
        });
    }

    @Test
    void transactionValidIfAmountIsPositive() {
        LocalDateTime date = LocalDateTime.now();
        Transaction transaction = new Transaction(
                date, "Swish", 14, TransactionType.EXPENSE);
        assertEquals(14, transaction.amount());
    }
}