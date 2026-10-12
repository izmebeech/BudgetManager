import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
        void transactionInvalidIfBelowZero() {
            LocalDateTime date = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(
                    date, "Swish", -14, TransactionType.EXPENSE
            );
        });
        }

    @Test
    void transactionInvalidIfZero() {
        LocalDateTime date = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(
                    date, "Swish", 0, TransactionType.EXPENSE
            );
        });
    }
}