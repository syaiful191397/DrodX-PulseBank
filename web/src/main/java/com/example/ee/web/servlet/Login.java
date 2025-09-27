package com.example.ee.web.servlet;


import com.example.ee.core.mail.NewLoginMail;
import com.example.ee.core.model.Account;
import com.example.ee.core.model.AccountStatus;
import com.example.ee.core.model.Customer;

import com.example.ee.core.model.Transaction;
import com.example.ee.core.provider.MailServiceProvider;
import com.example.ee.core.service.AccountService;
import com.example.ee.core.service.CustomerService;
import com.example.ee.core.service.TransactionService;
import com.example.ee.core.util.Encryption;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@WebServlet("/login")
public class Login extends HttpServlet {


    @Inject
    private SecurityContext securityContext;

    @EJB
    private CustomerService customerService;


    @EJB
    private AccountService accountService;

    @EJB
    private TransactionService transactionService;

    private static final Random random = new SecureRandom();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nic = request.getParameter("username");
        String password = request.getParameter("password");

        AuthenticationParameters parameters = AuthenticationParameters.withParams()
                .credential(new UsernamePasswordCredential(nic, Encryption.encrypt(password)));

        AuthenticationStatus status = securityContext.authenticate(request, response, parameters);

//        System.out.println("Authentication Status : " + status);
        if (status == AuthenticationStatus.SUCCESS) {
            Customer customer = customerService.getCustomerByNic(nic);
            Account customerAccount = null;

            if (customer != null) {
                boolean hasAccount = false;
                try {

                    customerAccount = accountService.getAccountByCustomerNic(nic);
                    if (customerAccount != null) {
                        hasAccount = true;
                    }

                } catch (NoResultException e) {
                    hasAccount = false;
                } catch (Exception e) {
                    System.err.println("Error checking for existing account for NIC " + nic + ": " + e.getMessage());
                    e.printStackTrace();

                    hasAccount = false;
                }

                if (!hasAccount) {

                    String accountNumber = generateAccountNumber();
                    Account newAccount = new Account();
                    newAccount.setAccountNo(accountNumber);
                    newAccount.setBalance(500.0);
                    newAccount.setAccountType("Savings");
                    newAccount.setAccountStatus(AccountStatus.ACTIVE);


                    newAccount.setCustomer(customer);



                    try {
                        accountService.createAccount(newAccount);
                        customerAccount = newAccount;

                        // NewLogin email
                        NewLoginMail mail = new NewLoginMail(customer.getEmail(), customer.getFirstName(), customer.getLastName(),nic, accountNumber);
                        MailServiceProvider.getInstance().sendMail(mail);

                        System.out.println("Authentication Status : " + status);
                    } catch (Exception e) {
                        System.err.println("Error creating account or updating customer status for NIC " + nic + ": " + e.getMessage());
                        e.printStackTrace();

                        request.setAttribute("loginError", "Login successful, but failed to create account due to a system error. Please contact support.");
                        request.getRequestDispatcher("/signIn.jsp").forward(request, response);
                        return;
                    }
                } else {

                    if (customer.isFirstLogin()) {
                        customer.setFirstLogin(false);
                        customerService.updateCustomer(customer);
                    }
                }

                HttpSession session = request.getSession();
                session.setAttribute("customer", customer);
                session.setAttribute("firstLogin", customer.isFirstLogin());
                session.setAttribute("account", customerAccount);


                if (customerAccount != null) {

                    List<Transaction> recentTransactions = transactionService.getTransactionsByAccountId(customerAccount.getId());

                    if (recentTransactions.size() > 3) {
                        recentTransactions = recentTransactions.subList(0, 3);
                    }
                    session.setAttribute("recentTransactions", recentTransactions);
                }


                LocalDate tomorrow = LocalDate.now().plusDays(1);
                request.setAttribute("minDate", tomorrow.format(DateTimeFormatter.ISO_LOCAL_DATE));


                response.sendRedirect(request.getContextPath() + "/index.jsp");

            } else {
                request.setAttribute("loginError", "Authentication successful but customer data not found.");
                request.getRequestDispatcher("/signIn.jsp").forward(request, response);
            }
        } else if (status == AuthenticationStatus.SEND_FAILURE) {
            request.setAttribute("loginError", "Invalid NIC or password.");
            request.getRequestDispatcher("/signIn.jsp").forward(request, response);
        } else {
            request.setAttribute("loginError", "Authentication process could not be completed.");
            request.getRequestDispatcher("/signIn.jsp").forward(request, response);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customer") == null || session.getAttribute("account") == null) {

            response.sendRedirect(request.getContextPath() + "/signIn.jsp");
            return;
        }

        Customer customer = (Customer) session.getAttribute("customer");
        Account customerAccount = (Account) session.getAttribute("account");


        if (customerAccount != null) {
            List<Transaction> recentTransactions = transactionService.getTransactionsByAccountId(customerAccount.getId());
            if (recentTransactions.size() > 3) {
                recentTransactions = recentTransactions.subList(0, 3);
            }
            session.setAttribute("recentTransactions", recentTransactions);
        }

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        request.setAttribute("minDate", tomorrow.format(DateTimeFormatter.ISO_LOCAL_DATE));

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private String generateAccountNumber() {

        int accountNumber = 10000000 + random.nextInt(90000000);
        return String.valueOf(accountNumber);
    }

//        Customer customer = customerService.login(nic, password);
//
//
//        if (customer != null) {
//            HttpSession session = request.getSession();
//            session.setAttribute("customer", customer);
//            session.setAttribute("firstLogin", customer.isFirstLogin());
//
//            response.sendRedirect("index.jsp");
//            System.out.println("Authentication Status normal: " + status);
//        } else {
//            response.sendRedirect(request.getContextPath() + "/signIn.jsp");
////            throw new LoginFailedException("Invalid email or password");
//        }
//
//    }

}



//        } else if (status == AuthenticationStatus.SUCCESS) {

//
//           Account account = new Account();
////            account.setAccountNo(UUID.randomUUID().toString());
////            account.setCustomerNic(nic);
//            account.setAccountNo(accountNumber);
//            account.setBalance(500);
//            account.setAccountType("Saving");
//
//            // Add Account
//            accountService.createAccount(account);

//            response.sendRedirect("signIn.jsp");
//            response.sendError(502,"Logging Error...");
//            throw new ArithmeticException("Authentication Failed");
//            response.sendRedirect("index.jsp");
//            System.out.println("Authentication Status : " + status);
//
//            response.sendRedirect(request.getContextPath() + "/index.jsp");






//        boolean login = loginService.login(nic, password);
//        if (login) {
////            HttpSession session = request.getSession();
////            session.setAttribute("user", email);
//            response.sendRedirect("index.jsp");
//        } else {
//            response.sendRedirect("signIn.jsp");
//        }
//
//    }
//}
//        AuthenticationParameters parameters = AuthenticationParameters.withParams()
//                .credential(new UsernamePasswordCredential(nic, Encryption.encrypt(password)));
//
//        AuthenticationStatus status = securityContext.authenticate(request, response, parameters);
//
//        //System.out.println("Authentication Status : " + status);
//
//
//        if (status == AuthenticationStatus.SUCCESS) {
//        response.sendError(502,"Logging Error...");
//            throw new ArithmeticException("Authentication Failed");
//            response.sendRedirect(request.getContextPath() + "/index.jsp");
//        }else  {
//        response.sendRedirect(request.getContextPath() + "/login.jsp");
//            throw new LoginFailedException("Invalid email or password");
//        }

/////////////////////////////////////////////////////////////////////

//