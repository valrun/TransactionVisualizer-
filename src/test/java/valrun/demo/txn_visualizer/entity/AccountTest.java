package valrun.demo.txn_visualizer.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void shouldCreateEmptyAccount() {
        Account account = new Account();

        assertNull(account.getId());
        assertNull(account.getBalance());
        assertNull(account.getName());
        assertNull(account.getVersion());
    }

    @Test
    void shouldCreateAccountWithRequiredFields() {
        Account account = new Account("Test Account", 1000L);

        assertNull(account.getId());
        assertEquals("Test Account", account.getName());
        assertEquals(1000L, account.getBalance());
        assertEquals(0L, account.getVersion());
    }

    @Test
    void shouldUpdateBalanceCorrectly() {
        Account account = new Account("Test", 300L);
        account.setBalance(600L);

        assertEquals(600L, account.getBalance());
        assertEquals(1L, account.getVersion());
    }

}