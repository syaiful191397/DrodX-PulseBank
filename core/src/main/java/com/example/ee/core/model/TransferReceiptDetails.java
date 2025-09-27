package com.example.ee.core.model;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransferReceiptDetails implements Serializable {
    private String senderName;
    private String senderAccountNo;
    private String senderAccountType;
    private String recipientAccountNo;
    private String transactionType;
    private double amount;
    private LocalDateTime transactionDate;
    private String description;


    public TransferReceiptDetails(String senderName, String senderAccountNo, String senderAccountType,
                                  String recipientAccountNo, String transactionType, double amount,
                                  LocalDateTime transactionDate, String description) {
        this.senderName = senderName;
        this.senderAccountNo = senderAccountNo;
        this.senderAccountType = senderAccountType;
        this.recipientAccountNo = recipientAccountNo;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
    }


    public String getSenderName() {
        return senderName;
    }

    public String getSenderAccountNo() {
        return senderAccountNo;
    }

    public String getSenderAccountType() {
        return senderAccountType;
    }

    public String getRecipientAccountNo() {
        return recipientAccountNo;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getDescription() {
        return description;
    }


    public String getFormattedTransactionDate() {
        if (transactionDate == null) {
            return "";
        }

        return transactionDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy, hh:mm a"));
    }


    public String getFormattedAmount() {
        return String.format("%.2f", amount);
    }

    @Override
    public String toString() {
        return "TransferReceiptDetails{" +
                "senderName='" + senderName + '\'' +
                ", senderAccountNo='" + senderAccountNo + '\'' +
                ", senderAccountType='" + senderAccountType + '\'' +
                ", recipientAccountNo='" + recipientAccountNo + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", description='" + description + '\'' +
                '}';
    }
}
