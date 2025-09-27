package com.example.ee.web.servlet;

import com.example.ee.core.model.AccountStatus;
import com.example.ee.core.model.Customer;
import com.example.ee.core.service.CustomerService;
import com.google.gson.Gson;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

//@WebServlet("/admin/customer-action")
@WebServlet("/customer-action")
public class CustomerActionServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CustomerActionServlet.class.getName());
    private final Gson gson = new Gson();

    @EJB
    private CustomerService customerService;

    // Helper for JSON response
    private void sendJsonResponse(HttpServletResponse response, int status, String message, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new ApiResponse(message, data)));
        out.flush();
    }


    private static class ApiResponse {
        String message;
        Object data;

        public ApiResponse(String message, Object data) {
            this.message = message;
            this.data = data;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("admin") == null) {
//            sendJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access.", null);
//            return;
//        }

        String action = request.getParameter("action");
        String customerIdStr = request.getParameter("customerId");
        Long customerId = null;

        if (customerIdStr != null && !customerIdStr.isEmpty()) {
            try {
                customerId = Long.parseLong(customerIdStr);
            } catch (NumberFormatException e) {
                sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid customer ID format.", null);
                return;
            }
        }

        if ("view".equals(action) || "edit-get".equals(action)) {
            if (customerId == null) {
                sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Customer ID is required for view/edit.", null);
                return;
            }
            Customer customer = customerService.getCustomerById(customerId);
            if (customer != null) {
                sendJsonResponse(response, HttpServletResponse.SC_OK, "Customer fetched successfully.", customer);
            } else {
                sendJsonResponse(response, HttpServletResponse.SC_NOT_FOUND, "Customer not found.", null);
            }
        } else {
            sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action or missing parameters.", null);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("admin") == null) {
//            sendJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access.", null);
//            return;
//        }

        String action = request.getParameter("action");
        String customerIdStr = request.getParameter("customerId");
        Long customerId = null;

        if (customerIdStr != null && !customerIdStr.isEmpty()) {
            try {
                customerId = Long.parseLong(customerIdStr);
            } catch (NumberFormatException e) {
                sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid customer ID format.", null);
                return;
            }
        }

        if (customerId == null) {
            sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Customer ID is required for this action.", null);
            return;
        }

        try {
            switch (action) {
                case "activate":
                    customerService.updateCustomerStatus(customerId, AccountStatus.ACTIVE);
                    sendJsonResponse(response, HttpServletResponse.SC_OK, "Customer activated successfully.", null);
                    break;
                case "deactivate":
                    customerService.updateCustomerStatus(customerId, AccountStatus.INACTIVE);
                    sendJsonResponse(response, HttpServletResponse.SC_OK, "Customer deactivated successfully.", null);
                    break;
                case "edit-save":

                    Customer customerToUpdate = customerService.getCustomerById(customerId);
                    if (customerToUpdate == null) {
                        sendJsonResponse(response, HttpServletResponse.SC_NOT_FOUND, "Customer not found for update.", null);
                        return;
                    }


                    customerToUpdate.setFirstName(request.getParameter("firstName"));
                    customerToUpdate.setLastName(request.getParameter("lastName"));
                    customerToUpdate.setEmail(request.getParameter("email"));
                    customerToUpdate.setContact(request.getParameter("contact"));
                    customerToUpdate.setGender(request.getParameter("gender"));
                    customerToUpdate.setNic(request.getParameter("nic"));
                    customerToUpdate.setAddress(request.getParameter("address"));
                    customerToUpdate.setPostalCode(request.getParameter("postalCode"));


                    String dobStr = request.getParameter("dob");
                    if (dobStr != null && !dobStr.isEmpty()) {
                        try {

                            customerToUpdate.setDob(dobStr);
                        } catch (DateTimeParseException e) {
                            sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid date of birth format.", null);
                            return;
                        }
                    }


                    String statusStr = request.getParameter("status");
                    if (statusStr != null && !statusStr.isEmpty()) {
                        customerToUpdate.setAccountStatus(AccountStatus.valueOf(statusStr.toUpperCase()));
                    }

                    customerService.updateCustomer(customerToUpdate);
                    sendJsonResponse(response, HttpServletResponse.SC_OK, "Customer updated successfully.", customerToUpdate);
                    break;
                default:
                    sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action.", null);
                    break;
            }
        } catch (Exception e) {
            logger.severe("Error performing customer action " + action + ": " + e.getMessage());
            e.printStackTrace();
            sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage(), null);
        }
    }
}