package valrun.demo.txn_visualizer.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "txn_log")
public class TransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant time;

    @ManyToOne
    @JoinColumn(name = "txn_id", nullable = false)
    private Transaction transaction;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String log;

    public TransactionLog() {
    }

    public TransactionLog(Transaction txn, String log) {
        this();
        this.transaction = txn;
        this.log = log;
    }

    public Long getId() {
        return id;
    }

    public Instant getTime() {
        return time;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public String getLog() {
        return log;
    }
}