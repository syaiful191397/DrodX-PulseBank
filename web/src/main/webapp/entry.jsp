<%--
  Created by IntelliJ IDEA.
  User: DisanduRodrigo
  Date: 15-Jul-25
  Time: 11:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DrodX-PulseBank | Welcome</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="entry-container">
    <!-- Header Section -->
    <header class="entry-header">
        <div class="logo">
            <i class="fas fa-university"></i>
            <h1>DrodX<span>-PulseBank</span></h1>
        </div>
        <p class="tagline">Secure Banking Solutions for the Digital Age</p>
    </header>

    <!-- Main Content -->
    <main class="entry-content">
        <div class="selection-cards">
            <!-- Admin Card -->
            <div class="selection-card admin-cardd" onclick="window.location.href='<%= request.getContextPath() %>/dashboard'">
                <div class="card-icon">
                    <i class="fas fa-user-shield"></i>
                </div>
                <div class="card-content">
                    <h2>Administrator</h2>
                    <p>Access the bank management dashboard to oversee accounts, transactions, and customer data.</p>
                </div>
                <div class="card-footer">
                    <span class="enter-btn">Enter <i class="fas fa-arrow-right"></i></span>
                </div>
            </div>

            <!-- User Card -->
            <div class="selection-card user-card" onclick="window.location.href='signIn.jsp'">
                <div class="card-icon">
                    <i class="fas fa-user"></i>
                </div>
                <div class="card-content">
                    <h2>Customer</h2>
                    <p>Sign in to your personal banking account to manage your finances and make transactions.</p>
                </div>
                <div class="card-footer">
                    <span class="enter-btn">Enter <i class="fas fa-arrow-right"></i></span>
                </div>
            </div>
        </div>
    </main>

    <!-- Footer Section -->
    <footer class="entry-footer">
        <p>&copy; 2025 DrodX-PulseBank. All rights reserved.</p>
        <div class="footer-links">
            <a href="#">Privacy Policy</a>
            <a href="#">Terms of Service</a>
            <a href="#">Contact Support</a>
        </div>
    </footer>
</div>
</body>
</html>