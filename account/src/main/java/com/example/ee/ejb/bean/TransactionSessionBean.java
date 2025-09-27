package com.example.ee.ejb.bean;

import com.example.ee.core.model.Transaction;
import com.example.ee.core.service.TransactionService;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TransactionSessionBean implements TransactionService {

    @PersistenceContext
    private EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void recordTransaction(Transaction transaction) {
        em.persist(transaction);
        System.out.println("Recorded transaction: " + transaction);
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return em.find(Transaction.class, id);
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(Long accountId) {

        String jpql = "SELECT t FROM Transaction t WHERE t.fromAccount.id = :accountId OR t.toAccount.id = :accountId ORDER BY t.transactionDate DESC";
        TypedQuery<Transaction> query = em.createQuery(jpql, Transaction.class);
        query.setParameter("accountId", accountId);
        return query.getResultList();
    }
    @Override
    public long getTodaysTransactionCount() {


        java.time.LocalDateTime startOfDay = java.time.LocalDate.now().atStartOfDay();
        java.time.LocalDateTime endOfDay = java.time.LocalDate.now().atTime(23, 59, 59, 999999999);

        return (Long) em.createQuery("SELECT COUNT(t) FROM Transaction t WHERE t.transactionDate >= :start AND t.transactionDate <= :end")
                .setParameter("start", startOfDay)
                .setParameter("end", endOfDay)
                .getSingleResult();
    }
}