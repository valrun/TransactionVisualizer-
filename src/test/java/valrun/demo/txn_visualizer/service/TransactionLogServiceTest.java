package valrun.demo.txn_visualizer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import valrun.demo.txn_visualizer.entity.Transaction;
import valrun.demo.txn_visualizer.entity.TransactionLog;
import valrun.demo.txn_visualizer.repository.TransactionLogRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionLogServiceTest {

    @Mock
    TransactionLogRepository transactionLogRepository;

    @InjectMocks
    TransactionLogService transactionLogService;

    @Test
    void createTransactionLog_shouldSuccessfullyCreateLog() {
        Transaction txn = new Transaction();
        String logMessage = "Test log message";
        TransactionLog expectedLog = new TransactionLog(txn, logMessage);

        when(transactionLogRepository.save(any(TransactionLog.class))).thenReturn(expectedLog);

        TransactionLog result = transactionLogService.createTransactionLog(txn, logMessage);

        assertNotNull(result);
        assertEquals(txn, result.getTransaction());
        assertEquals(logMessage, result.getLog());
        verify(transactionLogRepository, times(1)).save(any(TransactionLog.class));
    }

    @Test
    void getTransactionLogs_shouldReturnAllLogs() {
        Transaction txn1 = new Transaction();
        Transaction txn2 = new Transaction();
        List<TransactionLog> expectedLogs = List.of(
                new TransactionLog(txn1, "Log 1"),
                new TransactionLog(txn2, "Log 2")
        );

        when(transactionLogRepository.findAll()).thenReturn(expectedLogs);

        List<TransactionLog> result = transactionLogService.getTransactionLogs();

        assertEquals(2, result.size());
        verify(transactionLogRepository, times(1)).findAll();
    }

    @Test
    void getLogsByTransaction_shouldReturnFilteredLogs() {
        UUID txnId = UUID.randomUUID();
        Transaction txn = new Transaction();
        List<TransactionLog> expectedLogs = List.of(
                new TransactionLog(txn, "Log 1"),
                new TransactionLog(txn, "Log 2")
        );

        when(transactionLogRepository.findByTransactionId(txnId)).thenReturn(expectedLogs);

        List<TransactionLog> result = transactionLogService.getLogsByTransaction(txnId);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(log -> log.getTransaction().equals(txn)));
        verify(transactionLogRepository, times(1)).findByTransactionId(txnId);
    }

    @Test
    void getLogsByTransaction_shouldReturnEmptyListForNonExistingTransaction() {
        UUID nonExistingTxnId = UUID.randomUUID();
        when(transactionLogRepository.findByTransactionId(nonExistingTxnId)).thenReturn(List.of());

        List<TransactionLog> result = transactionLogService.getLogsByTransaction(nonExistingTxnId);

        assertTrue(result.isEmpty());
        verify(transactionLogRepository, times(1)).findByTransactionId(nonExistingTxnId);
    }
}
