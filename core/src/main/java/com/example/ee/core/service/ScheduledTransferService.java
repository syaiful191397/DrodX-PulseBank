package com.example.ee.core.service;

import com.example.ee.core.model.ScheduledTransfers;
import jakarta.ejb.Timer;

public interface ScheduledTransferService {
    void scheduleTransfer(ScheduledTransfers details);
    void performScheduledTransfer(Timer timer);
    void saveScheduledTransfer(ScheduledTransfers scheduledTransfer);
}
