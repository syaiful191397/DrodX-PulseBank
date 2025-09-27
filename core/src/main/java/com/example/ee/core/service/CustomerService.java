package com.example.ee.core.service;

import com.example.ee.core.model.AccountStatus;
import com.example.ee.core.model.Customer;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface CustomerService {

    Customer getCustomerByUsername(String nic);
    Customer getCustomerByNic(String nic);
    Customer getCustomerByEmail(String email);
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(Customer customer);
    boolean validate(String nic, String password);
    Customer login (String nic, String password);
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    void updateCustomerStatus(Long customerId, AccountStatus newStatus);
}