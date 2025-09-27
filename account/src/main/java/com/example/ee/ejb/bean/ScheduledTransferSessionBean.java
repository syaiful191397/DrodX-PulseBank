package com.example.ee.ejb.bean;

import com.example.ee.core.model.Account;
import com.example.ee.core.model.ScheduledTransfers;
import com.example.ee.core.model.Transaction;
import com.example.ee.core.service.AccountService;
import com.example.ee.core.service.ScheduledTransferService;
import com.example.ee.core.service.TransactionService;
import jakarta.annotation.Resource;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager; // Import EntityManager
import jakarta.persistence.PersistenceContext; // Import PersistenceContext

import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;


@Stateless
@Local(ScheduledTransferService.class)
public class ScheduledTransferSessionBean implements ScheduledTransferService {

    private static final Logger logger = Logger.getLogger(ScheduledTransferSessionBean.class.getName());

    @Resource
    private TimerService timerService;

    @EJB
    private AccountService accountService;

    @EJB
    private TransactionService transactionService;

    @PersistenceContext
    private EntityManager em;


    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void scheduleTransfer(ScheduledTransfers details) {
        em.persist(details);
        em.flush();
        logger.info("Persisted ScheduledTransfers entity with ID: " + details.getId());


        Date initialExpiration = Date.from(details.getTransferDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

        TimerConfig timerConfig = new TimerConfig();

        timerConfig.setInfo(details);
        timerConfig.setPersistent(true);

        switch (details.getRecurrence()) {
            case "once":
                timerService.createSingleActionTimer(initialExpiration, timerConfig);
                logger.info("One-time transfer scheduled for " + details.getFromAccount().getAccountNo() + " to " + details.getToAccount().getAccountNo() +
                        " on " + details.getTransferDate() + " (Timer ID for info: " + details.getId() + ")");
                break;
            case "daily":
                timerService.createIntervalTimer(initialExpiration, 24 * 60 * 60 * 1000, timerConfig);
                logger.info("Daily transfer scheduled starting " + details.getFromAccount().getAccountNo() + " to " + details.getToAccount().getAccountNo() +
                        " from " + details.getTransferDate() + " (Timer ID for info: " + details.getId() + ")");
                break;
            case "weekly":
                timerService.createIntervalTimer(initialExpiration, 7 * 24 * 60 * 60 * 1000, timerConfig);
                logger.info("Weekly transfer scheduled starting " + details.getFromAccount().getAccountNo() + " to " + details.getToAccount().getAccountNo() +
                        " from " + details.getTransferDate() + " (Timer ID for info: " + details.getId() + ")");
                break;
            case "monthly":
                timerService.createIntervalTimer(initialExpiration, 30L * 24 * 60 * 60 * 1000, timerConfig);
                logger.warning("Monthly transfer scheduled using approximate 30-day interval. For precise monthly, consider Calendar-Based Timers.");
                logger.info("Monthly transfer scheduled starting " + details.getFromAccount().getAccountNo() + " to " + details.getToAccount().getAccountNo() +
                        " from " + details.getTransferDate() + " (Timer ID for info: " + details.getId() + ")");
                break;
            default:
                logger.warning("Unknown recurrence type: " + details.getRecurrence());
                break;
        }
    }

    @Override
    @Timeout
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void performScheduledTransfer(Timer timer) {

        ScheduledTransfers timerInfoDetails = (ScheduledTransfers) timer.getInfo();
        if (timerInfoDetails == null || timerInfoDetails.getId() == null) {
            logger.severe("Timer fired with null info payload or no ID. Cannot perform transfer.");
            return;
        }

        ScheduledTransfers details = em.find(ScheduledTransfers.class, timerInfoDetails.getId());

        if (details == null) {
            logger.severe("ScheduledTransfers entity not found in database for ID: " + timerInfoDetails.getId() + ". Cancelling timer if one-time.");
            if ("once".equals(timerInfoDetails.getRecurrence())) {
                timer.cancel();
            }
            return;
        }


        Account fromAccount = accountService.getAccountByAccountNo(details.getFromAccount().getAccountNo());
        Account toAccount = accountService.getAccountByAccountNo(details.getToAccount().getAccountNo());

        if (fromAccount == null || toAccount == null) {
            logger.severe("One or both accounts not found for scheduled transfer (from: " + details.getFromAccount().getAccountNo() + ", to: " + details.getToAccount().getAccountNo() + "). Cancelling timer if one-time.");
            if ("once".equals(details.getRecurrence())) {
                timer.cancel();
            }
            return;
        }

        logger.info("Executing scheduled transfer: From " + fromAccount.getAccountNo() + " to " + toAccount.getAccountNo() + ", Amount: " + details.getAmount());

        try {
            boolean success = accountService.transferMoneyScheduled(
                    fromAccount.getAccountNo(),
                    toAccount.getAccountNo(),
                    details.getAmount()
            );

            if (success) {
                logger.info("Scheduled transfer successful for: " + fromAccount.getAccountNo() +
                        " to " + toAccount.getAccountNo() + " amount: " + details.getAmount());


                Transaction transactionRecord = new Transaction(
                        fromAccount,
                        toAccount,
                        details.getAmount(),
                        "Scheduled Transfer",
                        details.getDescription()
                );
                transactionService.recordTransaction(transactionRecord);
                logger.info("Transaction record created for scheduled transfer: " + transactionRecord);

            } else {
                logger.warning("Scheduled transfer FAILED for: " + fromAccount.getAccountNo() +
                        " to " + toAccount.getAccountNo() + " amount: " + details.getAmount() +
                        ". Likely insufficient funds or other issue at time of execution.");

            }

            if ("once".equals(details.getRecurrence())) {
                timer.cancel();

                logger.info("One-time timer for " + fromAccount.getAccountNo() + " cancelled and entity removed if applicable.");
            }

        } catch (Exception e) {
            logger.severe("Error executing scheduled transfer for " + fromAccount.getAccountNo() +
                    " to " + toAccount.getAccountNo() + ": " + e.getMessage());
            e.printStackTrace();

        }
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveScheduledTransfer(ScheduledTransfers scheduledTransfer) {
        em.persist(scheduledTransfer);
        logger.info("Saved ScheduledTransfer entity to database: " + scheduledTransfer.getId());
    }
}