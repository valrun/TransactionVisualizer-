package valrun.demo.txn_visualizer.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import valrun.demo.txn_visualizer.entity.Account;
import valrun.demo.txn_visualizer.repository.AccountRepository;

import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private void validateBalance(long balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
    }

    @Transactional
    public Account createAccount(String name, long balance) {
        validateBalance(balance);
        Account account = new Account(name, balance);
        return accountRepository.save(account);
    }

    @Transactional
    public Account save(Account account) {
        validateBalance(account.getBalance());
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    Account getAccount(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
    }
}