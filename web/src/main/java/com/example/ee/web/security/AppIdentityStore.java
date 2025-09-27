//package com.example.ee.web.security;
//
//
//import com.example.ee.core.model.Customer;
//import com.example.ee.core.service.CustomerService;
//import jakarta.ejb.EJB;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.security.enterprise.credential.Credential;
//import jakarta.security.enterprise.credential.UsernamePasswordCredential;
//import jakarta.security.enterprise.identitystore.CredentialValidationResult;
//import jakarta.security.enterprise.identitystore.IdentityStore;
//
//import java.util.Set;
//
//@ApplicationScoped
//public class AppIdentityStore implements IdentityStore {
//
//
//    @EJB
//    private CustomerService customerService;
//
//    @Override
//    public CredentialValidationResult validate(Credential credential) {
//        if (credential instanceof UsernamePasswordCredential) {
//            UsernamePasswordCredential upc = (UsernamePasswordCredential) credential;
//
//            if (customerService.validate(upc.getCaller(), upc.getPasswordAsString())) {
//                Customer customer = customerService.getCustomerByNic(upc.getCaller());
//
//                return new CredentialValidationResult(customer.getNic(), Set.of(customer.getUserType().name()));
//
//            }
//
//        }
//
//        return CredentialValidationResult.INVALID_RESULT;
//    }
//}
///////////////////////////////////////////////////////////////////////


package com.example.ee.web.security;

import com.example.ee.core.model.Customer;
import com.example.ee.core.service.CustomerService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

import java.util.Set;

@ApplicationScoped
public class AppIdentityStore implements IdentityStore {

    @EJB
    private CustomerService customerService;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential upc = (UsernamePasswordCredential) credential;


            if (customerService.validate(upc.getCaller(), upc.getPasswordAsString())) {
                Customer customer = customerService.getCustomerByNic(upc.getCaller());

                if (customer != null) {
                    return new CredentialValidationResult(customer.getNic(), Set.of(customer.getUserType().name()));
                }
            }
        }
        return CredentialValidationResult.INVALID_RESULT;
    }
}