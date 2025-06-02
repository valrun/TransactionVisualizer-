package valrun.demo.txn_visualizer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import valrun.demo.txn_visualizer.entity.Transaction;
import valrun.demo.txn_visualizer.entity.TransactionLog;
import valrun.demo.txn_visualizer.repository.TransactionLogRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionLogService {

    final TransactionLogRepository transactionLogRepository;

    TransactionLogService(TransactionLogRepository transactionLogRepository) {
        this.transactionLogRepository = transactionLogRepository;
    }

    @Transactional
    public TransactionLog createTransactionLog(Transaction txn, String logMessage) {
        TransactionLog transactionLog = new TransactionLog(txn, logMessage);
        return transactionLogRepository.save(transactionLog);
    }

    @Transactional(readOnly = true)
    public List<TransactionLog> getTransactionLogs() {
        return transactionLogRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<TransactionLog> getLogsByTransaction(UUID txnId) {
        return transactionLogRepository.findByTransactionId(txnId);
    }
}
