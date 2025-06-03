package valrun.demo.txn_visualizer.entity;

import org.junit.jupiter.api.Test;
import valrun.demo.txn_visualizer.enums.IsolationLevel;
import valrun.demo.txn_visualizer.enums.TransactionStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TransactionTest {

    @Test
    void shouldCreateEmptyTxn() {
        Transaction txn = new Transaction();

        assertNull(txn.getId());
        assertNull(txn.getTime());
        assertNull(txn.getIsolationLevel());
        assertNull(txn.getStatus());
        assertNull(txn.getDelay());
        assertNull(txn.getSender());
        assertNull(txn.getPayee());
    }

    @Test
    void shouldCreateTxnWithRequiredFields() {
        Account sender = new Account();
        Account payee = new Account();
        Transaction txn = new Transaction(IsolationLevel.READ_COMMITTED, sender, payee);

        assertEquals(IsolationLevel.READ_COMMITTED, txn.getIsolationLevel());
        assertEquals(TransactionStatus.STARTED, txn.getStatus());
        assertEquals(0, txn.getDelay());
        assertEquals(sender, txn.getSender());
        assertEquals(payee, txn.getPayee());
    }

    @Test
    void shouldUpdateStatusCorrectly() {
        Account sender = new Account();
        Account payee = new Account();
        Transaction txn = new Transaction(IsolationLevel.READ_COMMITTED, sender, payee);

        txn.setStatus(TransactionStatus.COMMITTED);

        assertEquals(TransactionStatus.COMMITTED, txn.getStatus());
    }
}
