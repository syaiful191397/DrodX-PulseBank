<%--
  Created by IntelliJ IDEA.
  User: DisanduRodrigo
  Date: 06-Jul-25
  Time: 5:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DrodX-PulseBank | Verify Email</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="left-panel">
        <div class="logo">
            <i class="fas fa-university"></i>
            <h1>DrodX<span>-PulseBank</span></h1>
        </div>
        <div class="bank-image">
            <img src="images/secure-banking2.jpg" alt="Email Verification">
        </div>
        <div class="features">
            <div class="feature-item">
                <i class="fas fa-envelope"></i>
                <p>Secure Email Verification</p>
            </div>
            <div class="feature-item">
                <i class="fas fa-shield-alt"></i>
                <p>Account Protection</p>
            </div>
            <div class="feature-item">
                <i class="fas fa-customer-check"></i>
                <p>Identity Confirmation</p>
            </div>
        </div>
    </div>
    <div class="right-panel">
        <div class="verification-form">
            <div class="verification-header">
                <i class="fas fa-envelope-open-text verification-icon"></i>
                <h2>Verify Your Email</h2>
                <p class="subtitle">We've sent a 6-digit code to <span class="customer-email"><%= request.getParameter("email") != null ? request.getParameter("email") : "your email" %></span></p>
            </div>

            <form id="verificationForm" action="VerifyServlet" method="POST">
                <input type="hidden" name="email" value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>">

                <div class="verification-code-container">
                    <div class="verification-inputs">
                        <input type="text" name="digit1" maxlength="1" pattern="[0-9]" required autofocus>
                        <input type="text" name="digit2" maxlength="1" pattern="[0-9]" required>
                        <input type="text" name="digit3" maxlength="1" pattern="[0-9]" required>
                        <input type="text" name="digit4" maxlength="1" pattern="[0-9]" required>
                        <input type="text" name="digit5" maxlength="1" pattern="[0-9]" required>
                        <input type="text" name="digit6" maxlength="1" pattern="[0-9]" required>
                    </div>
                </div>

                <div class="verification-options">
                    <p class="timer">Code expires in: <span id="countdown">05:00</span></p>
                    <a href="#" class="resend-code" id="resendCode">Resend Code</a>
                </div>

                <button type="submit" class="verify-btn" id="verifyBtn">
                    <span class="btn-text">Verify Email</span>
                    <i class="fas fa-check-circle"></i>
                </button>

                <div class="verification-footer">
                    <p>Didn't receive the code? Check your spam folder or <a href="#" id="tryDifferentEmail">try a different email address</a></p>
                </div>
            </form>
        </div>

        <div class="footer">
            <p>&copy; 2023 DrodX-PulseBank. All rights reserved.</p>
            <div class="footer-links">
                <a href="#">Security</a>
                <a href="#">Privacy Policy</a>
                <a href="#">Contact Us</a>
            </div>
        </div>
    </div>
</div>

<script src="script.js"></script>
</body>
</html>