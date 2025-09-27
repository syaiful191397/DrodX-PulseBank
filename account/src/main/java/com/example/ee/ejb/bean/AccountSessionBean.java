package com.example.ee.ejb.bean;


import com.example.ee.core.model.Account;
import com.example.ee.core.model.Customer;
import com.example.ee.core.model.Transaction;
import com.example.ee.core.service.AccountService;
import com.example.ee.core.service.CustomerService;
import com.example.ee.core.service.TransactionService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.logging.Logger;


@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
public class AccountSessionBean implements AccountService {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private TransactionService transactionService;

    private static final Logger logger = Logger.getLogger(AccountSessionBean.class.getName());
    @Override
    public void createAccount(Account account) {

        em.persist(account);

    }

    @Override
    public void deleteAccount(Long accountId) {

        System.out.println("Deleting account with ID: " + accountId);

         Account account = em.find(Account.class, accountId);
         if (account != null) {
             em.remove(account);
         }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void creditToAccount(String accountNo, double amount) {
        try {
            Account account = em.createNamedQuery("Account.findByAccountNo", Account.class)
                    .setParameter("accountNo", accountNo)
                    .getSingleResult();
            if (amount > 0) {
                account.setBalance(account.getBalance() + amount);
            }
            em.merge(account);
        } catch (NoResultException e) {
            e.printStackTrace();
        }

    }

    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void debitFromAccount(String accountNo, double amount) {
        try {
            Account account = em.createNamedQuery("Account.findByAccountNo", Account.class)
                    .setParameter("accountNo", accountNo)
                    .getSingleResult();
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                em.merge(account);
            }
        }catch (NoResultException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account getAccountByAccountNo(String accountNo) {
        try {
             return em.createNamedQuery("Account.findByAccountNo", Account.class)
            .setParameter("accountNo", accountNo)
            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    @Override
    public Account getAccountByCustomerNic(String nic) {

        try {
            // Using the named query defined in your Account model
            return em.createNamedQuery("Account.findByCustomerNic", Account.class)
                    .setParameter("nic", nic)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null; // No account found for the given NIC
        } catch (Exception e) {
            System.err.println("Error fetching account by customer NIC: " + nic + " - " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }  @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean transferMoney(String fromAccountNo, String toAccountNo, double amount) {
        Account fromAccount = getAccountByAccountNo(fromAccountNo);
        Account toAccount = getAccountByAccountNo(toAccountNo);

        if (fromAccount == null || toAccount == null) {
            logger.warning("Transfer failed: One or both accounts not found. From: " + fromAccountNo + ", To: " + toAccountNo);
            return false;
        }

        if (fromAccount.getBalance() < amount) {
            logger.warning("Transfer failed: Insufficient balance in account " + fromAccountNo);
            return false;
        }

        try {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);

            em.merge(fromAccount);
            em.merge(toAccount);


            Transaction transactionRecord = new Transaction(
                    fromAccount,
                    toAccount,
                    amount,
                    "Transfer",
                    "Money transfer from " + fromAccountNo + " to " + toAccountNo
            );
            transactionService.recordTransaction(transactionRecord);
            logger.info("Transaction recorded successfully for transfer: " + transactionRecord.getId());

            return true;
        } catch (Exception e) {
            logger.severe("Error during money transfer transaction: " + e.getMessage());
            e.printStackTrace();

            return false;
        }

    }
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean transferMoneyScheduled(String fromAccountNo, String toAccountNo, double amount) {
        Account fromAccount = getAccountByAccountNo(fromAccountNo);
        Account toAccount = getAccountByAccountNo(toAccountNo);

        if (fromAccount == null || toAccount == null) {
            logger.warning("Transfer failed: One or both accounts not found. From: " + fromAccountNo + ", To: " + toAccountNo);
            return false;
        }

        if (fromAccount.getBalance() < amount) {
            logger.warning("Transfer failed: Insufficient balance in account " + fromAccountNo);
            return false;
        }

        try {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);

            em.merge(fromAccount);
            em.merge(toAccount);

            return true;
        } catch (Exception e) {
            logger.severe("Error during money transfer transaction: " + e.getMessage());
            e.printStackTrace();

            return false;
        }

    }
    @Override
    public long getTotalAccountCount() {
        return (Long) em.createQuery("SELECT COUNT(a) FROM Account a").getSingleResult();
    }

    @Override
    public double getTotalBankBalance() {
        Double totalBalance = (Double) em.createQuery("SELECT SUM(a.balance) FROM Account a").getSingleResult();
        return totalBalance != null ? totalBalance : 0.0;
    }

// Optional: if you add accountCount to Customer model or use a DTO
// @Override
// public long countAccountsByCustomerId(Long customerId) {
//     return (Long) em.createQuery("SELECT COUNT(a) FROM Account a WHERE a.customer.id = :customerId")
//                      .setParameter("customerId", customerId)
//                      .getSingleResult();
// }
}

