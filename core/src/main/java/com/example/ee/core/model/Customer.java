package com.example.ee.core.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
@NamedQueries({
        //@NamedQuery(name = "Customer.findByEmail", query = "select u from Customer u where u.email = ?1"),
        @NamedQuery(name = "Customer.findByNic", query = "select u from Customer u where u.nic =:nic"),
        @NamedQuery(name = "Customer.findByUsernameAndPassword",
                query = "select u from Customer u where u.nic =:nic and u.password=:password"),
})
@Cacheable(false)
public class Customer implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String dob;
    private String gender;
    @Column(unique = true)
    private String nic;
    private String address;
    private String postalCode;
    private String password;
    private String verificationCode;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.INACTIVE;
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.CUSTOMER;
    @Enumerated(EnumType.STRING)
    private Status status = Status.NOT_VERIFIED;
    private boolean firstLogin;



    public Customer() {
    }

    public Customer(boolean firstLogin) {
        this.firstLogin = true;
    }

    public Customer(String firstName, String lastName, String email, String contact, String dob, String gender, String nic, String address, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contact = contact;
        this.dob = dob;
        this.gender = gender;
        this.nic = nic;
        this.address = address;
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
}
