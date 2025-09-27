<%--
  Created by IntelliJ IDEA.
  User: DisanduRodrigo
  Date: 17-Jul-25
  Time: 12:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%-- adminLogin.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>

        body {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: var(--background-color-dark);
            font-family: 'Poppins', sans-serif;
            color: var(--text-color-light);
        }
        .login-container {
            background-color: var(--card-background-dark);
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
            text-align: center;
            width: 350px;
        }
        .login-container h2 {
            margin-bottom: 30px;
            color: var(--primary-color);
            font-size: 1.8em;
        }
        .login-container .form-group {
            margin-bottom: 20px;
            text-align: left;
        }
        .login-container label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
        }
        .login-container input[type="text"],
        .login-container input[type="password"] {
            width: calc(100% - 20px);
            padding: 12px 10px;
            border: 1px solid var(--border-color);
            border-radius: 5px;
            background-color: var(--input-background);
            color: var(--text-color-light);
            font-size: 1em;
            box-sizing: border-box;
        }
        .login-container button {
            width: 100%;
            padding: 12px;
            background-color: var(--primary-color);
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 1.1em;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .login-container button:hover {
            background-color: var(--primary-color-dark);
        }
        .login-container .error-message {
            color: var(--danger-color);
            margin-top: 15px;
            font-size: 0.9em;
        }
        .toggle-password {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            color: var(--text-color-light);
        }
        .password-input-wrapper {
            position: relative;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Admin Login</h2>
    <% if (request.getAttribute("login_error") != null) { %>
    <p class="error-message"><%= request.getAttribute("login_error") %></p>
    <% } %>
    <form action="j_security_check" method="POST">
        <div class="form-group">
            <label for="j_username">Username:</label>
            <input type="text" id="j_username" name="j_username" required>
        </div>
        <div class="form-group password-input-wrapper">
            <label for="j_password">Password:</label>
            <input type="password" id="j_password" name="j_password" required>
            <span class="toggle-password" onclick="togglePasswordVisibility('j_password')">
                    <i class="fas fa-eye"></i>
                </span>
        </div>
        <button type="submit">Login</button>
    </form>
</div>
<script src="<%= request.getContextPath() %>/script.js"></script>
<script>

</script>
</body>
</html>
