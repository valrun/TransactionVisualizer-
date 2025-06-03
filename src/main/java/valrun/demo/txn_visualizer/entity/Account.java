package valrun.demo.txn_visualizer.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long balance;

    @Column(nullable = false)
    private Long version;

    public Account() {
    }

    public Account(String name, long balance) {
        this();
        this.name = name;
        this.balance = balance;
        this.version = 0L;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getBalance() {
        return balance;
    }

    public Long getVersion() {
        return version;
    }

    public void setBalance(Long balance) {
        increaseVersion();
        this.balance = balance;
    }

    private void increaseVersion() {
        this.version++;
    }
}