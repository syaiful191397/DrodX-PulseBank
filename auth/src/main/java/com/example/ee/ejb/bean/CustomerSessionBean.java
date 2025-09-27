package com.example.ee.ejb.bean;


import com.example.ee.core.model.AccountStatus;
import com.example.ee.core.model.Customer;
import com.example.ee.core.service.CustomerService;
import com.example.ee.core.util.Encryption;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
public class CustomerSessionBean implements CustomerService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Customer getCustomerByUsername(String nic) {
        return em.find(Customer.class, nic);
    }

    @Override
    public Customer getCustomerByNic(String nic) {
        try {
            return em.createNamedQuery("Customer.findByNic", Customer.class)
                    .setParameter("nic", nic)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }


    @Override
    public Customer getCustomerByEmail(String email) {
        return null;
    }

    @Override
    public void addCustomer(Customer customer) {
        em.persist(customer);
    }

    //@RolesAllowed({"CUSTOMER","ADMIN"})
    @Override
    public void updateCustomer(Customer customer) {
        em.merge(customer);
    }

    @RolesAllowed({"CUSTOMER", "ADMIN"})
    @Override
    public void deleteCustomer(Customer customer) {
        em.remove(customer);
    }

    @RolesAllowed({"CUSTOMER", "ADMIN"})
    @Override
    public Customer login(String nic, String password) {

        try {
            Customer customer = em.createNamedQuery("Customer.findByUsernameAndPassword", Customer.class)
                    .setParameter("nic", nic)
                    .setParameter("password", password)
                    .getSingleResult();
            if (customer != null && customer.getPassword().equals(password)) {
                return customer;
            }
            return null;
        } catch (NoResultException e) {
            return null;
        }
    }


    @Override
    public boolean validate(String nic, String password) {
        Customer customer = em.createNamedQuery("Customer.findByNic", Customer.class)
                .setParameter("nic", nic).getSingleResult();

        return customer != null && customer.getPassword().equals(password);
    }

    @Override
//    @RolesAllowed("ADMIN")
    public List<Customer> getAllCustomers() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }
    @Override
//    @RolesAllowed("ADMIN")
    public Customer getCustomerById(Long id) {
        return em.find(Customer.class, id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
//    @RolesAllowed("ADMIN")
    public void updateCustomerStatus(Long customerId, AccountStatus newStatus) {
        Customer customer = em.find(Customer.class, customerId);
        if (customer != null) {
            customer.setAccountStatus(newStatus);
            em.merge(customer); // Persist the change
           }
    }
}

