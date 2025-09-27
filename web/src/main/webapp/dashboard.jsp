<%--
  Created by IntelliJ IDEA.
  User: DisanduRodrigo
  Date: 07-Jul-25
  Time: 11:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>DrodX-PulseBank | Admin Dashboard</title>
  <link rel="stylesheet" href="style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
</head>
<body>
<div class="admin-container">
  <!-- Header Section -->
  <header class="admin-header">
    <div class="logo">
      <i class="fas fa-university"></i>
      <h1>DrodX<span>-PulseBank</span> <span class="admin-badge">Admin</span></h1>
    </div>
    <div class="admin-profile">
      <div class="admin-info">
        <span class="admin-name">${admin.username}</span>
        <span class="admin-role">${admin.role}</span>
      </div>
      <div class="profile-icon">
        <i class="fas fa-user-shield"></i>
      </div>
    </div>
  </header>

  <!-- Main Content -->
  <main class="admin-content">
    <!-- Admin Summary Cards -->
    <section class="admin-summary">
      <div class="admin-card total-customers">
        <div class="card-icon">
          <i class="fas fa-users"></i>
        </div>
        <div class="card-info">
          <h3>Total Customers</h3>
          <p>${totalCustomers}</p>
        </div>
      </div>

      <div class="admin-card total-accounts">
        <div class="card-icon">
          <i class="fas fa-wallet"></i>
        </div>
        <div class="card-info">
          <h3>Total Accounts</h3>
          <p>${totalAccounts}</p>
        </div>
      </div>

      <div class="admin-card total-transactions">
        <div class="card-icon">
          <i class="fas fa-exchange-alt"></i>
        </div>
        <div class="card-info">
          <h3>Today's Transactions</h3>
          <p>${todaysTransactions}</p>
        </div>
      </div>

      <div class="admin-card total-balance">
        <div class="card-icon">
          <i class="fas fa-money-bill-wave"></i>
        </div>
        <div class="card-info">
          <h3>Total Bank Balance</h3>
          <p>$${totalBankBalance}</p>
        </div>
      </div>
    </section>

    <!-- Admin Actions Section -->
    <section class="admin-actions">
      <button class="action-btn" onclick="openModal('addCustomerModal')">
        <i class="fas fa-user-plus"></i>
        <span>Add Customer</span>
      </button>
      <button class="action-btn" onclick="openModal('transferFundsModal')">
        <i class="fas fa-random"></i>
        <span>Transfer Funds</span>
      </button>
      <button class="action-btn" onclick="openModal('manageAccountsModal')">
        <i class="fas fa-credit-card"></i>
        <span>Manage Accounts</span>
      </button>
      <button class="action-btn" onclick="openModal('generateReportsModal')">
        <i class="fas fa-chart-pie"></i>
        <span>Generate Reports</span>
      </button>
    </section>

    <!-- Customers Table -->
    <section class="admin-table-section">
      <div class="section-header">
        <h2>Customer Management</h2>
        <div class="table-actions">
          <input type="text" id="customerSearch" placeholder="Search customers...">
          <button class="refresh-btn" onclick="refreshCustomers()">
            <i class="fas fa-sync-alt"></i>
          </button>
        </div>
      </div>
      <div class="table-container">
        <table id="customersTable" class="display">
          <thead>
          <tr>
            <th>ID</th>
            <th>NIC</th>
            <th>Name</th>
            <th>Email</th>
            <th>Contact</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="customer" items="${customers}">
            <tr id="customer-row-${customer.id}"> <%-- Added ID to row for easy JS update --%>
              <td>${customer.id}</td>
              <td>${customer.nic}</td> <%-- Display NIC --%>
              <td>${customer.firstName} ${customer.lastName}</td>
              <td>${customer.email}</td>
              <td>${customer.contact}</td>

              <td>
                                        <span id="status-badge-${customer.id}" class="status-badge ${customer.accountStatus == 'ACTIVE' ? 'active' : 'inactive'}">
                                           ${customer.accountStatus} <%-- Display actual enum name for consistency --%>
                                       </span>
              </td>
              <td>
                <button class="action-icon edit-btn" onclick="editCustomer('${customer.id}')">
                  <i class="fas fa-edit"></i>
                </button>
                <button class="action-icon view-btn" onclick="viewCustomer('${customer.id}')">
                  <i class="fas fa-eye"></i>
                </button>
                <button id="toggle-status-btn-${customer.id}"
                        class="action-icon ${customer.accountStatus == 'ACTIVE' ? 'deactivate-btn' : 'activate-btn'}"
                        onclick="${customer.accountStatus == 'ACTIVE' ? 'deactivateCustomer' : 'activateCustomer'}('${customer.id}')">
                  <i class="fas ${customer.accountStatus == 'ACTIVE' ? 'fa-user-slash' : 'fa-user-check'}"></i>
                </button>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </section>
  </main>

  <!-- Navigation Sidebar -->
  <nav class="admin-sidebar">
    <ul class="nav-menu">
      <li class="nav-item active">
        <a href="#">
          <i class="fas fa-tachometer-alt"></i>
          <span>Dashboard</span>
        </a>
      </li>
      <li class="nav-item">
        <a href="#" onclick="openModal('addCustomerModal')">
          <i class="fas fa-user-plus"></i>
          <span>Add Customer</span>
        </a>
      </li>
      <li class="nav-item">
        <a href="#">
          <i class="fas fa-users-cog"></i>
          <span>Customer Management</span>
        </a>
      </li>
      <li class="nav-item">
        <a href="#" onclick="openModal('transferFundsModal')">
          <i class="fas fa-random"></i>
          <span>Fund Transfers</span>
        </a>
      </li>
      <li class="nav-item">
        <a href="#">
          <i class="fas fa-credit-card"></i>
          <span>Account Management</span>
        </a>
      </li>
      <li class="nav-item">
        <a href="#">
          <i class="fas fa-chart-line"></i>
          <span>Bank Analytics</span>
        </a>
      </li>
      <li class="nav-item">
        <a href="#">
          <i class="fas fa-cog"></i>
          <span>System Settings</span>
        </a>
      </li>
      <li class="nav-item logout">
        <a href="adminLogout">
          <i class="fas fa-sign-out-alt"></i>
          <span>Log Out</span>
        </a>
      </li>
    </ul>
  </nav>
</div>

<!-- Add Customer Modal -->
<div id="addCustomerModal" class="modal">
  <div class="modal-content">
    <div class="modal-header">
      <h3>Add New Customer</h3>
      <span class="close-btn" onclick="closeModal('addCustomerModal')">&times;</span>
    </div>
    <div class="modal-body">
      <form id="addCustomerForm"  action="register" method="POST">
        <div class="form-row">
          <div class="form-group">
            <label for="firstName">First Name</label>
            <input type="text" id="firstName" name="firstName" required>
          </div>
          <div class="form-group">
            <label for="lastName">Last Name</label>
            <input type="text" id="lastName" name="lastName" required>
          </div>
        </div>

        <!-- Add Gender Field -->
        <div class="form-group">
          <label for="gender">Gender</label>
          <select id="gender" name="gender" required>
            <option value="">Select Gender</option>
            <option value="male">Male</option>
            <option value="female">Female</option>
          </select>
        </div>

        <!-- Add NIC Number Field -->
        <div class="form-group">
          <label for="nic">NIC Number</label>
          <input type="text" id="nic" name="nic" required
                 pattern="[0-9]{9}[VvXx]|[0-9]{12}"
                 title="Enter valid NIC number (old format: 123456789V or new format: 123456789012)">
        </div>

        <div class="form-group">
          <label for="email">Email Address</label>
          <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
          <label for="phone">Contact Number</label>
          <input type="tel" id="contact" name="contact" required>
        </div>
        <div class="form-group">
          <label for="dob">Date of Birth</label>
          <input type="date" id="dob" name="dob" required>
        </div>
        <div class="form-group">
          <label for="address">Address</label>
          <textarea id="address" name="address" rows="3" required></textarea>
        </div>

        <!-- Add Postal Code Field -->
        <div class="form-group">
          <label for="postalCode">Postal Code</label>
          <input type="text" id="postalCode" name="postalCode"
                 pattern="[0-9]{5}"
                 title="5-digit postal code" required>
        </div>

        <div class="form-group">
          <label for="initialDeposit">Initial Deposit ($)</label>
          <input type="number" id="initialDeposit" name="initialDeposit" min="0" step="0.01" required>
        </div>
        <div class="form-actions">
          <button type="button" class="cancel-btn" onclick="closeModal('addCustomerModal')">Cancel</button>
          <button type="submit" value="Register"  class="submit-btn">Create Customer</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Transfer Funds Modal -->
<div id="transferFundsModal" class="modal">
  <div class="modal-content">
    <div class="modal-header">
      <h3>Transfer Funds</h3>
      <span class="close-btn" onclick="closeModal('transferFundsModal')">&times;</span>
    </div>
    <div class="modal-body">
      <form id="transferFundsForm" action="transferFunds" method="POST">
        <div class="form-group">
          <label for="fromAccount">From Account</label>
          <select id="fromAccount" name="fromAccount" required>
            <option value="">Select Account</option>
            <!-- Populate with actual accounts -->
            <option value="1001001001">1001001001 (John Doe - Savings)</option>
            <option value="1001001002">1001001002 (Jane Smith - Checking)</option>
          </select>
        </div>
        <div class="form-group">
          <label for="toAccount">To Account</label>
          <select id="toAccount" name="toAccount" required>
            <option value="">Select Account</option>
            <!-- Populate with actual accounts -->
            <option value="1001001003">1001001003 (Robert Johnson - Savings)</option>
            <option value="1001001004">1001001004 (Sarah Williams - Checking)</option>
          </select>
        </div>
        <div class="form-group">
          <label for="amount">Amount ($)</label>
          <input type="number" id="amount" name="amount" min="0.01" step="0.01" required>
        </div>
        <div class="form-group">
          <label for="description">Description</label>
          <input type="text" id="description" name="description">
        </div>
        <div class="form-actions">
          <button type="button" class="cancel-btn" onclick="closeModal('transferFundsModal')">Cancel</button>
          <button type="submit" class="submit-btn">Transfer Funds</button>
        </div>
      </form>
    </div>
  </div>
</div>


<div id="editCustomerModal" class="modal">
  <div class="modal-content">
    <div class="modal-header">
      <h3>Edit Customer</h3>
      <span class="close-btn" onclick="closeModal('editCustomerModal')">&times;</span>
    </div>
    <div class="modal-body">

    </div>
  </div>
</div>


<div id="viewCustomerModal" class="modal">
  <div class="modal-content">
    <div class="modal-header">
      <h3>Customer Details</h3>
      <span class="close-btn" onclick="closeModal('viewCustomerModal')">&times;</span>
    </div>
    <div class="modal-body">

    </div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="<%= request.getContextPath() %>/script.js"></script>

<script type="text/javascript">

  document.addEventListener('DOMContentLoaded', function() {

    var customerAddedStatus = "${sessionScope.customerAdded}";
    var customerName = "${sessionScope.customerName}";
    var errorMessage = "${sessionScope.errorMessage}";

    if (customerAddedStatus === "success") {
      showAlert('Customer "' + customerName + '" added and Email Sent Successfully!', 'success');

      <% session.removeAttribute("customerAdded"); %>
      <% session.removeAttribute("customerName"); %>
    } else if (customerAddedStatus === "error") {
      showAlert('Error: ' + errorMessage, 'error');

      <% session.removeAttribute("customerAdded"); %>
      <% session.removeAttribute("errorMessage"); %>
    }
  });
</script>
</body>
</html>

