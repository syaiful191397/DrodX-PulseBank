package com.example.ee.web.servlet;

import com.example.ee.core.model.AccountStatus;
import com.example.ee.core.model.Customer;
import com.example.ee.core.model.UserType;
import com.example.ee.core.service.CustomerService;
import com.example.ee.core.service.AccountService;
import com.example.ee.core.service.TransactionService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

//@WebServlet("/admin/dashboard")
@WebServlet("/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AdminDashboardServlet.class.getName());

    @EJB
    private CustomerService customerService;

    @EJB
    private AccountService accountService;

    @EJB
    private TransactionService transactionService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
//
//        // Basic Authorization Check (you might have a more robust filter)
//        // If not logged in as admin or session is invalid, redirect to entry page
//        if (session == null || session.getAttribute("admin") == null) {
//            response.sendRedirect(request.getContextPath() + "/entry.jsp");
//            return;
//        }

        try {

            List<Customer> customers = customerService.getAllCustomers();

            request.setAttribute("customers", customers);


            long totalCustomers = customers.size();
            long totalAccounts = accountService.getTotalAccountCount();
            long todaysTransactions = transactionService.getTodaysTransactionCount();
            double totalBankBalance = accountService.getTotalBankBalance();

            request.setAttribute("totalCustomers", totalCustomers);
            request.setAttribute("totalAccounts", totalAccounts);
            request.setAttribute("todaysTransactions", todaysTransactions);
            request.setAttribute("totalBankBalance", totalBankBalance);


            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            logger.severe("Error loading admin dashboard: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("errorMessage", "Failed to load dashboard data: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/entry.jsp");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}