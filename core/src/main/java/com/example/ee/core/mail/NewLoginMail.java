package com.example.ee.core.mail;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;

public class NewLoginMail extends Mailable {

    private String to;
    private String firstName;
    private String lastName;
    private String username;
    private String accountNo;


    public NewLoginMail(String to, String firstName, String lastName, String username, String accountNo) {
        this.to = to;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.accountNo = accountNo;
    }

    @Override
    public void build(Message message) throws Exception {
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Welcome to DrodX-PulseBank");


        message.setText("Hi " + firstName + " " + lastName + " ,\n\n\n"
                + "Your DrodX-PulseBank account has been successfully created.\n\n"
                + "Username: " + username + "\n"
                + "Account No: " + accountNo + "\n\n"
                + "Please keep your account details safe and secure.\n\n"
                + "Regards,\nDrodX-PulseBank Team");
//        message.setContent(password, "text/html; charset=utf-8");



    }
}
