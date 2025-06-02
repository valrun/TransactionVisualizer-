package valrun.demo.txn_visualizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import valrun.demo.txn_visualizer.entity.TransactionLog;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Integer> {
    List<TransactionLog> findByTransactionId(UUID txnId);
}
