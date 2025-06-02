package valrun.demo.txn_visualizer.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import valrun.demo.txn_visualizer.enums.IsolationLevel;
import valrun.demo.txn_visualizer.enums.TransactionStatus;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "txn")
public class Transaction {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IsolationLevel isolationLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    private Integer delay;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "payee_id")
    private Account payee;

    public UUID getId() {
        return id;
    }

    public Instant getTime() {
        return time;
    }

    public IsolationLevel getIsolationLevel() {
        return isolationLevel;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Integer getDelay() {
        return delay;
    }

    public Account getSender() {
        return sender;
    }

    public Account getPayee() {
        return payee;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
