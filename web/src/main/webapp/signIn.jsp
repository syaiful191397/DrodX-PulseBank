<%--
  Created by IntelliJ IDEA.
  User: DisanduRodrigo
  Date: 06-Jul-25
  Time: 3:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>DrodX-PulseBank | Secure Sign In</title>
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
      <img src="images/secure-banking.jpg" alt="Secure Banking">
    </div>
<%--    <div class="bank-image">--%>
<%--      <i class="fas fa-university"></i>--%>
<%--    </div>--%>
    <div class="features">
      <div class="feature-item">
        <i class="fas fa-shield-alt"></i>
        <p>256-bit SSL Encryption</p>
      </div>
      <div class="feature-item">
        <i class="fas fa-clock"></i>
        <p>24/7 Banking Services</p>
      </div>
      <div class="feature-item">
        <i class="fas fa-hand-holding-usd"></i>
        <p>Automated Transactions</p>
      </div>
    </div>
  </div>
  <div class="right-panel">
    <div class="login-form">
      <h2>Sign In to Your Account</h2>
      <p class="subtitle">Secure access to your banking dashboard</p>

      <form id="loginForm" action="${pageContext.request.contextPath}/login" method="POST">
        <div class="form-group">
          <label for="email">Username</label>
          <div class="input-with-icon">
            <i class="fas fa-user"></i>
            <input type="text" id="email" name="username" placeholder="Enter your Username" required>
          </div>
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <div class="input-with-icon">
<%--            <i class="fas fa-lock"></i>--%>
            <input type="password" id="password" name="password" placeholder="Enter your password" required>
            <i class="fas fa-eye toggle-password" onclick="togglePasswordVisibility()"></i>
          </div>
        </div>

        <div class="form-options">
          <div class="remember-me">
            <input type="checkbox" id="remember" name="remember">
            <label for="remember">Remember me</label>
          </div>
          <a href="#" class="forgot-password">Forgot password?</a>
        </div>

        <button type="submit" value="Login" class="login-btn" id="loginBtn">
          <span class="btn-text">Sign In</span>
          <i class="fas fa-arrow-right"></i>
        </button>

        <div class="security-notice">
          <i class="fas fa-info-circle"></i>
          <p>For your security, we recommend you log out after each session.</p>
        </div>
      </form>

      <div class="register-link">
        <p>Don't have an account? <a href="register.jsp">Enroll now</a></p>
      </div>
    </div>

    <div class="footer">
      <p>&copy; 2025 DrodX-PulseBank. All rights reserved.</p>
      <div class="footer-links">
        <a href="#">Privacy Policy</a>
        <a href="#">Terms of Service</a>
        <a href="#">Contact Support</a>
      </div>
    </div>
  </div>
</div>

<script src="script.js"></script>
</body>
</html>