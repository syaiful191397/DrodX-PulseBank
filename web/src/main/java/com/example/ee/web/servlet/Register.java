package com.example.ee.web.servlet;

import com.example.ee.core.mail.NewCreateAccount;
import com.example.ee.core.model.AccountStatus;
import com.example.ee.core.model.Customer;
import com.example.ee.core.model.Status;
import com.example.ee.core.model.UserType;
import com.example.ee.core.provider.MailServiceProvider;
import com.example.ee.core.service.CustomerService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/register")
public class Register extends HttpServlet {

    @EJB
    private CustomerService customerService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String contact = request.getParameter("contact");
            String dob = request.getParameter("dob");
            String gender = request.getParameter("gender");
            String nic = request.getParameter("nic");
            String address = request.getParameter("address");
            String postalCode = request.getParameter("postalCode");
            String password = request.getParameter("password");
//        String depositStr = request.getParameter("initialDeposit");



            // Encrypt password
//        String encryptedPassword = Encryption.encrypt(password);


            Customer customer = new Customer(firstName, lastName, email, contact, dob, gender, nic, address, postalCode);
//        Customer customer = new Customer(firstname,lastname, email, contact, encryptedPassword);
            customer.setAccountStatus(AccountStatus.INACTIVE);
            customer.setUserType(UserType.CUSTOMER);
            customer.setStatus(Status.NOT_VERIFIED);
            customer.setFirstLogin(true);
//        customer.setVerificationCode(UUID.randomUUID().toString());
         customer.setPassword(UUID.randomUUID().toString().substring(0, 10));


            customerService.addCustomer(customer);

//        // Send verification email
//        VerificationMail mail = new VerificationMail(email, customer.getVerificationCode());
//        MailServiceProvider.getInstance().sendMail(mail);
//
//
            NewCreateAccount mail = new NewCreateAccount(email,firstName,lastName,nic, customer.getPassword());
            MailServiceProvider.getInstance().sendMail(mail);



            request.getSession().setAttribute("customerAdded", "success");
            request.getSession().setAttribute("customerName", firstName + " " + lastName);


            response.sendRedirect(request.getContextPath() + "/dashboard");


        } catch (Exception e) {

            e.printStackTrace();

            request.getSession().setAttribute("customerAdded", "error");
            request.getSession().setAttribute("errorMessage", "Failed to add customer: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
        }


    }

}



//    private static final Random random = new SecureRandom();
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//
//        double initialDeposit = Double.parseDouble(depositStr);
//
//        // 2. Generate credentials
//        String accountNumber = generateAccountNumber();
//        String password = generatePassword();
//
//
//
//         4. Send email with login credentials
//        String subject = "Welcome to DrodX-PulseBank";
//        String message = "Hi " + firstName + " " + lastName + ",\n\n"
//                + "Your DrodX-PulseBank account has been successfully created.\n"
//                + "Account Number: " + accountNumber + "\n"
//                + "Temporary Password: " + password + "\n"
//                + "Please login and change your password.\n\n"
//                + "Regards,\nDrodX-PulseBank Team";
//
//        MailUtility.sendMail(email, subject, message);
//
//        // 5. Redirect back to dashboard
//        response.sendRedirect("dashboard123.jsp?status=success");
//    }
//
//    private String generateAccountNumber() {
//        int number = 10000000 + random.nextInt(90000000); // 8-digit number
//        return String.valueOf(number);
//    }
//
//    private String generatePassword() {
//        return UUID.randomUUID().toString().substring(0, 10); // 10-char password
//    }
//}

//
//            // For initial deposit, you might need to handle this in your CustomerService
//            // and create an initial account/transaction.
//            // For now, let's just pass the customer object.
//            // You'll need to modify customerService.addCustomer to handle the initial deposit.
//            double initialDeposit = 0.0;
//            if (initialDepositStr != null && !initialDepositStr.isEmpty()) {
//                try {
//                    initialDeposit = Double.parseDouble(initialDepositStr);
//                } catch (NumberFormatException e) {
//                    // Log error or handle invalid deposit value
//                    System.err.println("Invalid initial deposit value: " + initialDepositStr);
//                }
//            }
