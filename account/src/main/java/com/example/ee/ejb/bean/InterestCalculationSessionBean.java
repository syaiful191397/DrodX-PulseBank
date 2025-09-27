package com.example.ee.ejb.bean;

import com.example.ee.core.model.Account;
import com.example.ee.core.model.Transaction;
import com.example.ee.core.service.AccountService;
import com.example.ee.core.service.InterestCalculationService;
import com.example.ee.core.service.TransactionService;
import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class InterestCalculationSessionBean implements InterestCalculationService {

    private static final Logger logger = Logger.getLogger(InterestCalculationSessionBean.class.getName());

    private static final double INTEREST_RATE = 0.05;

    @PersistenceContext
    private EntityManager em;

    @EJB
    private TransactionService transactionService;


    @Schedule(dayOfMonth = "30", hour = "2", minute = "0", second = "0", persistent = true)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void applyMonthlyInterest() {
        logger.info("Starting monthly interest calculation and application at " + LocalDateTime.now());


        List<Account> accounts = null;
        try {

            TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a WHERE a.accountStatus = 'ACTIVE'",
                    Account.class);
            accounts = query.getResultList();
        } catch (Exception e) {
            logger.severe("Error fetching accounts for interest calculation: " + e.getMessage());
            e.printStackTrace();
            throw new EJBException("Failed to fetch accounts for interest calculation.", e);
        }

        if (accounts == null || accounts.isEmpty()) {
            logger.info("No active accounts found for interest calculation.");
            return;
        }

        for (Account account : accounts) {
            try {
                double currentBalance = account.getBalance();
                double interestAmount = currentBalance * (INTEREST_RATE / 12);

                account.setBalance(currentBalance + interestAmount);
                em.merge(account);
                logger.info(String.format("Applied $%.2f interest to account %s. New balance: $%.2f",
                        interestAmount, account.getAccountNo(), account.getBalance()));


                Transaction interestTransaction = new Transaction();
                interestTransaction.setToAccount(account);
                interestTransaction.setAmount(interestAmount);
                interestTransaction.setTransactionType("Interest Credit");
                interestTransaction.setTransactionDate(LocalDateTime.now());
                interestTransaction.setDescription("Monthly interest payment for account " + account.getAccountNo());

                Transaction interestRecord = new Transaction(
                        null,
                        account,
                        interestAmount,
                        "Interest Credit",
                        "Monthly interest payment"
                );
                transactionService.recordTransaction(interestRecord);

            } catch (Exception e) {
                logger.severe("Error applying interest to account " + account.getAccountNo() + ": " + e.getMessage());
                e.printStackTrace();

            }
        }
        logger.info("Finished monthly interest calculation and application.");
    }
}