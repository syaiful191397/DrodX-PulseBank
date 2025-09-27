package com.example.ee.core.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "scheduledTransfers")
public class ScheduledTransfers implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "from_account_no", referencedColumnName = "accountNo", nullable = false)
    private Account fromAccount;


    @ManyToOne
    @JoinColumn(name = "to_account_no", referencedColumnName = "accountNo", nullable = false)
    private Account toAccount;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "description")
    private String description;

    @Column(name = "transfer_date", nullable = false)
    private LocalDate transferDate;

    @Column(name = "recurrence", nullable = false)
    private String recurrence;


    public ScheduledTransfers() {
    }


    public ScheduledTransfers(Account fromAccount, Account toAccount, double amount,
                              String description, LocalDate transferDate, String recurrence) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.description = description;
        this.transferDate = transferDate;
        this.recurrence = recurrence;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    @Override
    public String toString() {
        return "ScheduledTransfers{" +
                "id=" + id +
                ", fromAccountNo=" + (fromAccount != null ? fromAccount.getAccountNo() : "N/A") +
                ", toAccountNo=" + (toAccount != null ? toAccount.getAccountNo() : "N/A") +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", transferDate=" + transferDate +
                ", recurrence='" + recurrence + '\'' +
                '}';
    }
}