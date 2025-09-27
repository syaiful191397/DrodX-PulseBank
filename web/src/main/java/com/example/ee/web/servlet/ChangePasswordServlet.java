package com.example.ee.web.servlet;

import com.example.ee.core.model.AccountStatus;
import com.example.ee.core.model.Customer;
import com.example.ee.core.service.CustomerService;
import com.example.ee.core.util.Encryption;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {

    @EJB
    private CustomerService customerService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nic = request.getParameter("username");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        Customer customer = customerService.getCustomerByNic(nic);



        String encryptedPassword = Encryption.encrypt(confirmPassword);

        if (customer != null && customer.getPassword().equals(currentPassword)) {
            if (newPassword != null && newPassword.equals(confirmPassword)) {

                customer.setPassword(encryptedPassword);
                customer.setFirstLogin(false);
                customer.setAccountStatus(AccountStatus.ACTIVE);
                customerService.updateCustomer(customer);


                HttpSession session = request.getSession();
                session.setAttribute("customer", customer);
                session.setAttribute("firstLogin", false);


                request.getSession().setAttribute("PasswordChanged", "success");
                response.sendRedirect("index.jsp");
            } else {

                response.getWriter().write("Password mismatch");
            }
        } else {
            response.getWriter().write("Invalid temporary password");
        }
    }
}
