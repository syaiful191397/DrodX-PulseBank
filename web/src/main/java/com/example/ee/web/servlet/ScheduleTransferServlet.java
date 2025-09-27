package com.example.ee.web.servlet;

import com.example.ee.core.model.Account;
import com.example.ee.core.model.ScheduledTransfers;
import com.example.ee.core.service.AccountService;
import com.example.ee.core.service.ScheduledTransferService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

@WebServlet("/ScheduleTransferServlet")
public class ScheduleTransferServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ScheduleTransferServlet.class.getName());

    @EJB
    private ScheduledTransferService scheduledTransferService;

    @EJB
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String fromAccountNo = request.getParameter("fromAccount");
        String toAccountNo = request.getParameter("toAccount");
        String amountStr = request.getParameter("amount");
        String description = request.getParameter("description");
        String transferDateStr = request.getParameter("transferDate");
        String recurrence = request.getParameter("recurrence");


        if (fromAccountNo == null || fromAccountNo.isEmpty() ||
                toAccountNo == null || toAccountNo.isEmpty() ||
                amountStr == null || amountStr.isEmpty() ||
                transferDateStr == null || transferDateStr.isEmpty() ||
                recurrence == null || recurrence.isEmpty()) {
            session.setAttribute("transferMessage", "All fields are required for scheduled transfer.");
            session.setAttribute("transferStatus", "error");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                session.setAttribute("transferMessage", "Amount must be positive.");
                session.setAttribute("transferStatus", "error");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
        } catch (NumberFormatException e) {
            session.setAttribute("transferMessage", "Invalid amount format.");
            session.setAttribute("transferStatus", "error");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        LocalDate transferDate;
        try {
            transferDate = LocalDate.parse(transferDateStr);
            if (transferDate.isBefore(LocalDate.now(ZoneId.of("Asia/Colombo")))) { // Use current date for Sri Lanka
                session.setAttribute("transferMessage", "Scheduled transfer date cannot be in the past.");
                session.setAttribute("transferStatus", "error");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
        } catch (DateTimeParseException e) {
            session.setAttribute("transferMessage", "Invalid date format.");
            session.setAttribute("transferStatus", "error");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        if (fromAccountNo.equals(toAccountNo)) {
            session.setAttribute("transferMessage", "Cannot schedule transfer to the same account.");
            session.setAttribute("transferStatus", "error");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        try {

            Account fromAccount = accountService.getAccountByAccountNo(fromAccountNo);
            Account toAccount = accountService.getAccountByAccountNo(toAccountNo);

            if (fromAccount == null) {
                session.setAttribute("transferMessage", "Your 'From' account was not found. Please select a valid account.");
                session.setAttribute("transferStatus", "error");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }

            if (toAccount == null) {
                session.setAttribute("transferMessage", "Recipient account with number " + toAccountNo + " does not exist.");
                session.setAttribute("transferStatus", "error");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }

            ScheduledTransfers details = new ScheduledTransfers(
                    fromAccount, toAccount, amount, description, transferDate, recurrence
            );

            scheduledTransferService.scheduleTransfer(details);

            session.setAttribute("transferMessage", "Transfer of $" + String.format("%.2f", amount) +
                    " scheduled for " + transferDate + (recurrence.equals("once") ? "." : " (" + recurrence + " recurrence)."));
            session.setAttribute("transferStatus", "success");

          
        } catch (Exception e) {
            logger.severe("Error scheduling transfer: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("transferMessage", "Failed to schedule transfer: " + e.getMessage());
            session.setAttribute("transferStatus", "error");
        }

        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}