package com.example.ee.web.servlet;


import com.example.ee.core.model.Account;
import com.example.ee.core.model.Customer;
import com.example.ee.core.model.TransferReceiptDetails;
import com.example.ee.core.service.AccountService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {

    @EJB
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String fromAccountNo = request.getParameter("fromAccount");
        String toAccountNo = request.getParameter("toAccount");
        String amountStr = request.getParameter("amount");
        String description = request.getParameter("description");

        Customer senderCustomer = (Customer) session.getAttribute("customer");
        Account senderAccount = (Account) session.getAttribute("account");


        if (fromAccountNo == null || fromAccountNo.isEmpty() ||
                toAccountNo == null || toAccountNo.isEmpty() ||
                amountStr == null || amountStr.isEmpty()) {
            session.setAttribute("transferMessage", "Please fill in all transfer details.");
            session.setAttribute("transferStatus", "error");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                session.setAttribute("transferMessage", "Transfer amount must be positive.");
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


        if (fromAccountNo.equals(toAccountNo)) {
            session.setAttribute("transferMessage", "Cannot transfer money to the same account.");
            session.setAttribute("transferStatus", "error");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        try {

            Account recipientAccount = accountService.getAccountByAccountNo(toAccountNo);
            if (recipientAccount == null) {
                session.setAttribute("transferMessage", "Recipient account does not exist.");
                session.setAttribute("transferStatus", "error");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }


            boolean success = accountService.transferMoney(fromAccountNo, toAccountNo, amount);

            if (success) {
                LocalDateTime transactionDate = LocalDateTime.now();
                TransferReceiptDetails receiptDetails = new TransferReceiptDetails(
                        senderCustomer.getFirstName() + " " + senderCustomer.getLastName(),
                        senderAccount.getAccountNo(),
                        senderAccount.getAccountType(),
                        toAccountNo,
                        "Immediate Transfer",
                        amount,
                        transactionDate,
                        description
                );
                session.setAttribute("transferMessage", "Successfully transferred $" + String.format("%.2f", amount) + " to account " + toAccountNo + ".");
                session.setAttribute("transferStatus", "success");
                session.setAttribute("showTransferSuccessPopup", true);
                session.setAttribute("receiptDetails", receiptDetails);


                Account updatedSenderAccount = accountService.getAccountByAccountNo(fromAccountNo);
                if (updatedSenderAccount != null) {
                    session.setAttribute("account", updatedSenderAccount);
                }

            } else {
                session.setAttribute("transferMessage", "Transfer failed. Insufficient balance or other issue.");
                session.setAttribute("transferStatus", "error");
                session.removeAttribute("showTransferSuccessPopup");
                session.removeAttribute("receiptDetails");
            }
        } catch (Exception e) {
            System.err.println("Error during money transfer: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("transferMessage", "An unexpected error occurred during transfer. Please try again.");
            session.setAttribute("transferStatus", "error");
            session.removeAttribute("showTransferSuccessPopup");
            session.removeAttribute("receiptDetails");
        }

        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}