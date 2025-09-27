////package com.example.ee.core.model;
////
////
////import jakarta.persistence.*;
////
////@Entity
////@NamedQueries({
////        @NamedQuery(name = "Account.findByAccountNo",
////                query = "SELECT a FROM Account a WHERE a.accountNo=:accountNo"),
////        @NamedQuery(name = "Account.findByCustomerNic",
////                query = "SELECT a FROM Account a WHERE a.customer.nic = :nic")
////
////})
////@Cacheable(false)
////public class Account {
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private Integer id;
////
////    @Column(unique = true, nullable = false)
////    private String accountNo;
////
////    private double balance;
////
////    private String accountType;
////
////    @ManyToOne(cascade = CascadeType.ALL)
////    @JoinColumn(name = "customer_id") // Optional but better for clarity
////    private Customer customer;
////
////    public Account() {
////    }
////
////    public Account(Integer id, String accountNo, double balance, String accountType, Customer customer) {
////        this.id = id;
////        this.accountNo = accountNo;
////        this.balance = balance;
////        this.accountType = accountType;
////        this.customer = customer;
////    }
////
////    public Account(Integer id, String accountNo, double balance, Customer customer) {
////        this.id = id;
////        this.accountNo = accountNo;
////        this.balance = balance;
////        this.customer = customer;
////    }
////
////    public Integer getId() {
////        return id;
////    }
////
////    public void setId(Integer id) {
////        this.id = id;
////    }
////
////    public String getAccountNo() {
////        return accountNo;
////    }
////
////    public void setAccountNo(String accountNo) {
////        this.accountNo = accountNo;
////    }
////
////    public double getBalance() {
////        return balance;
////    }
////
////    public void setBalance(double balance) {
////        this.balance = balance;
////    }
////
////    public Customer getCustomer() {
////        return customer;
////    }
////
////    public void setCustomer(Customer customer) {
////        this.customer = customer;
////    }
////
////    public void setAccountType(String accountType) {
////        this.accountType = accountType;
////    }
////
////    public String getAccountType() {
////        return accountType;
////    }
////
////}
//package com.example.ee.core.model;
//
//import jakarta.persistence.*;
//
//@Entity
//@NamedQueries({
//        @NamedQuery(name = "Account.findByAccountNo",
//                query = "SELECT a FROM Account a WHERE a.accountNo=:accountNo"),
//        @NamedQuery(name = "Account.findByCustomerNic",
//                query = "SELECT a FROM Account a WHERE a.customer.nic = :nic")
//
//})
//@Cacheable(false)
//public class Account {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(unique = true, nullable = false)
//    private String accountNo;
//
//    private double balance;
//
//    private String accountType;
//
//    // --- Start of Changes for Foreign Key ---
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Consider LAZY fetch for performance
//    @JoinColumn(name = "customer_nic",      // This will be the name of the foreign key column in the 'account' table
//            referencedColumnName = "nic", // This specifies that 'customer_nic' refers to the 'nic' column in the 'customer' table
//            nullable = false)            // Make sure this column is not null
//    private Customer customer;
//    // --- End of Changes for Foreign Key ---
//
//
//    public Account() {
//    }
//
//    public Account(Integer id, String accountNo, double balance, String accountType, Customer customer) {
//        this.id = id;
//        this.accountNo = accountNo;
//        this.balance = balance;
//        this.accountType = accountType;
//        this.customer = customer;
//    }
//
//    public Account(Integer id, String accountNo, double balance, Customer customer) {
//        this.id = id;
//        this.accountNo = accountNo;
//        this.balance = balance;
//        this.customer = customer;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getAccountNo() {
//        return accountNo;
//    }
//
//    public void setAccountNo(String accountNo) {
//        this.accountNo = accountNo;
//    }
//
//    public double getBalance() {
//        return balance;
//    }
//
//    public void setBalance(double balance) {
//        this.balance = balance;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//    public void setAccountType(String accountType) {
//        this.accountType = accountType;
//    }
//
//    public String getAccountType() {
//        return accountType;
//    }
//
//}

package com.example.ee.core.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@NamedQueries({
        @NamedQuery(name = "Account.findByAccountNo", query = "SELECT a FROM Account a WHERE a.accountNo = :accountNo"),
        @NamedQuery(name = "Account.findByCustomerNic", query = "SELECT a FROM Account a WHERE a.customer.nic = :nic")
})
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( unique = true, nullable = false, length = 8)
    private String accountNo;

    @Column(nullable = false)
    private double balance;

    @Column(name = "account_type", nullable = false, length = 50)
    private String accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false, length = 20)
    private AccountStatus accountStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_nic", referencedColumnName = "nic", nullable = false)
    private Customer customer;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Constructors
    public Account() {
        this.createdAt = LocalDateTime.now();
        this.accountStatus = AccountStatus.ACTIVE;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}