package valrun.demo.txn_visualizer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import valrun.demo.txn_visualizer.entity.Account;
import valrun.demo.txn_visualizer.repository.AccountRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void createAccount_shouldSuccessfullyCreateAccount() {
        String name = "Test Account";
        long balance = 1000;
        Account expectedAccount = new Account(name, balance);

        when(accountRepository.save(any(Account.class))).thenReturn(expectedAccount);

        Account result = accountService.createAccount(name, balance);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(balance, result.getBalance());
        assertEquals(0L, result.getVersion());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void createAccount_shouldThrowExceptionWhenBalanceNegative() {
        String name = "Invalid Account";
        long negativeBalance = -100;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> accountService.createAccount(name, negativeBalance));

        assertEquals("Balance cannot be negative", exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void save_shouldUpdateAccountSuccessfully() {
        String name = "Original";
        long balance = 500;
        long updatedBalance = 500;
        UUID accountId = UUID.randomUUID();

        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account account = invocation.getArgument(0);
            if (account.getId() == null) {
                ReflectionTestUtils.setField(account, "id", accountId);
            }
            return account;
        });

        Account result = accountService.createAccount(name, balance);
        result.setBalance(updatedBalance);

        Account updatedResult = accountService.save(result);

        assertEquals(name, updatedResult.getName());
        assertEquals(updatedBalance, updatedResult.getBalance());
        assertEquals(accountId, updatedResult.getId());
        assertEquals(1L, updatedResult.getVersion());
    }

    @Test
    void save_shouldThrowExceptionForNegativeBalance() {
        Account invalidAccount = new Account("Invalid", -100);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> accountService.save(invalidAccount));

        assertEquals("Balance cannot be negative", exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void getAccount_shouldReturnAccountWhenExists() {
        UUID accountId = UUID.randomUUID();
        Account expectedAccount = new Account("Test", 1000);
        ReflectionTestUtils.setField(expectedAccount, "id", accountId);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(expectedAccount));

        Account result = accountService.getAccount(accountId);

        assertNotNull(result);
        assertEquals(accountId, result.getId());
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void save_shouldAllowZeroBalance() {
        Account account = new Account("Zero Balance", 0);

        when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.save(account);

        assertEquals(0, result.getBalance());
    }

    @Test
    void createAccount_shouldHandleLargeBalance() {
        long largeBalance = 10_000_000_000L;
        Account account = new Account("Millionaire", largeBalance);

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.createAccount("Millionaire", largeBalance);

        assertEquals(largeBalance, result.getBalance());
    }
}