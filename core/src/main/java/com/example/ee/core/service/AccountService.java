package com.example.ee.core.service;

import com.example.ee.core.model.Account;

public interface AccountService {


    void createAccount(Account account);
    void deleteAccount(Long id);
    void creditToAccount(String accountNo,double amount);
    void debitFromAccount(String accountNo, double amount);
    Account getAccountByAccountNo(String accountNo);
    Account getAccountByCustomerNic(String nic);
    boolean transferMoney(String fromAccountNo, String toAccountNo, double amount);
    boolean transferMoneyScheduled(String fromAccountNo, String toAccountNo, double amount);
    long getTotalAccountCount();
    double getTotalBankBalance();
    // long countAccountsByCustomerId(Long customerId); // If you want accurate account count per customer
}
