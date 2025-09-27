package com.example.ee.core.mail;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;

public class NewCreateAccount extends Mailable{
    private String to;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public NewCreateAccount(String to, String firstName, String lastName, String username, String password) {
        this.to = to;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    @Override
    public void build(Message message) throws Exception {
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Welcome to DrodX-PulseBank");

//        String encode = Base64.getEncoder().encodeToString(to.getBytes());

//        String link = "http://localhost:8080/DrodX-PulseBank/verify?id="+encode+"&vc="+verificationCode;
//        message.setContent(link, "text/html; charset=utf-8");
//


        //
        message.setText("Hi " + firstName + " " + lastName + " ,\n\n\n"
                + "Your DrodX-PulseBank account has been successfully Registered.\n\n"
                + "Username: " + username + "\n"
                + "Temporary Password: " + password + "\n\n"
                + "Please login and change your password.\n\n"
                + "Regards,\nDrodX-PulseBank Team");
//        message.setContent(password, "text/html; charset=utf-8");



//        String message = "Hi " + firstName + " " + lastName + ",\n\n"
//                + "Your DrodX-PulseBank account has been successfully created.\n"
//                + "Account Number: " + accountNumber + "\n"
//                + "Temporary Password: " + password + "\n"
//                + "Please login and change your password.\n\n"
//                + "Regards,\nDrodX-PulseBank Team";
    }
}
