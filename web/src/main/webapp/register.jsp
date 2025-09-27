<%--
  Created by IntelliJ IDEA.
  User: DisanduRodrigo
  Date: 06-Jul-25
  Time: 4:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DrodX-PulseBank | Create Account</title>
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
            <img src="images/secure-banking1.jpg" alt="Secure Banking Registration">
        </div>
        <div class="features">
            <div class="feature-item">
                <i class="fas fa-shield-alt"></i>
                <p>Military-Grade Encryption</p>
            </div>
            <div class="feature-item">
                <i class="fas fa-mobile-alt"></i>
                <p>Mobile Banking Ready</p>
            </div>
            <div class="feature-item">
                <i class="fas fa-percentage"></i>
                <p>Competitive Interest Rates</p>
            </div>
        </div>
    </div>
    <div class="right-panel">
        <div class="registration-form">
            <h2>Create Your Account</h2>
            <p class="subtitle">Join DrodX-PulseBank in just a few steps</p>

            <form id="registrationForm" action="register" method="POST">
                <div class="form-row">
                    <div class="form-group half-width">
                        <label for="firstName">First Name</label>
                        <div class="input-with-icon">
                            <i class="fas fa-user"></i>
                            <input type="text" id="firstName" name="firstName" placeholder="John" required>
                        </div>
                    </div>
                    <div class="form-group half-width">
                        <label for="lastName">Last Name</label>
                        <div class="input-with-icon">
                            <i class="fas fa-user"></i>
                            <input type="text" id="lastName" name="lastName" placeholder="Doe" required>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="email">Email Address</label>
                    <div class="input-with-icon">

                        <i class="fas fa-envelope"></i>
                        <input type="email" id="email" name="email" placeholder="john.doe@example.com" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="contact">Contact Number</label>
                    <div class="input-with-icon">
                        <i class="fas fa-phone"></i>
                        <input type="tel" id="contact" name="contact" placeholder="+94 (77) 123-4567" required>
                    </div>
                </div>

<%--                <div class="form-group">--%>
<%--                    <label for="dob">Date of Birth</label>--%>
<%--                    <div class="input-with-icon">--%>
<%--                        <i class="fas fa-calendar-alt"></i>--%>
<%--                        <input type="date" id="dob" name="dob" required>--%>
<%--                    </div>--%>
<%--                </div>--%>

<%--                <div class="form-group">--%>
<%--                    <label for="ssn">Social Security Number</label>--%>
<%--                    <div class="input-with-icon">--%>
<%--                        <i class="fas fa-lock"></i>--%>
<%--                        <input type="password" id="ssn" name="ssn" placeholder="XXX-XX-XXXX" required>--%>
<%--                        <i class="fas fa-eye toggle-password" onclick="togglePasswordVisibility('ssn')"></i>--%>
<%--                    </div>--%>
<%--                </div>--%>

                <div class="form-group">
                    <label for="password">Create Password</label>
                    <div class="input-with-icon">
                        <input type="password" id="password" name="password" placeholder="At least 8 characters" required>
                        <i class="fas fa-eye toggle-password" onclick="togglePasswordVisibility('password')"></i>
                    </div>
                    <div class="password-strength">
                        <div class="strength-meter"></div>
                        <span class="strength-text">Password Strength: <span id="strengthText">Weak</span></span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Confirm Password</label>

                    <div class="input-with-icon">
                        <i class="fas fa-lock"></i>
                        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Re-enter your password" required>
<%--                        <i class="fas fa-eye toggle-password" onclick="togglePasswordVisibility('password')"></i>--%>
                    </div>

                </div>

                <div class="form-options">
                    <div class="terms-agreement">
                        <input type="checkbox" id="terms" name="terms" required>
                        <label for="terms">I agree to the <a href="#" class="terms-link">Terms of Service</a> and <a href="#" class="terms-link">Privacy Policy</a></label>
                    </div>
                </div>

                <button type="submit" value="Register" class="register-btn" id="registerBtn">
                    <span class="btn-text">Create Account</span>
                    <i class="fas fa-user-plus"></i>
                </button>

                <div class="login-redirect">
                    <p>Already have an account? <a href="signIn.jsp" class="login-link">Sign In</a></p>
                </div>
            </form>
        </div>

        <div class="footer">
            <p>&copy; 2025 DrodX-PulseBank. All rights reserved.</p>
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