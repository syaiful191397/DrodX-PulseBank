package com.example.ee.core.service;


import com.example.ee.core.model.Transaction;
import java.util.List;

public interface TransactionService {
    void recordTransaction(Transaction transaction);
    Transaction getTransactionById(Long id);
    List<Transaction> getTransactionsByAccountId(Long accountId);

    long getTodaysTransactionCount();
}
