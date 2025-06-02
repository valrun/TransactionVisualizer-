package valrun.demo.txn_visualizer.enums;

public enum TransactionStatus {
    STARTED,          // Transaction created and initialized
    LOCK_ACQUIRED,    // Required row locks successfully obtained
    VALIDATED,        // Pre-conditions verified (sufficient funds)
    PROCESSING,       // Funds transfer in progress
    COMMITTED,        // Successfully committed
    ROLLBACK_STARTED, // Rollback initiated
    ROLLED_BACK,      // Fully rolled back
    FAILED,           // General failure
    DEADLOCK_VICTIM,  // Chosen as deadlock victim
    TIMEOUT,          // Lock wait timeout
    CONFLICT,         // Serializable isolation violation
    RETRYING          // Optimistic concurrency retry
}
