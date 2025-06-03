package valrun.demo.txn_visualizer.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionLogTest {

    @Test
    void shouldCreateEmptyLog() {
        TransactionLog log = new TransactionLog();
        assertNull(log.getId());
        assertNull(log.getTime());
        assertNull(log.getTransaction());
        assertNull(log.getLog());
    }

    @Test
    void shouldMaintainTransactionRelationship() {
        Transaction txn = new Transaction();
        String longMessage = "A".repeat(1000);

        TransactionLog log = new TransactionLog(txn, longMessage);

        assertEquals(txn, log.getTransaction());
        assertEquals(longMessage, log.getLog());
    }
}