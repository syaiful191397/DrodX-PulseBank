<%--
  Created by IntelliJ IDEA.
  User: DisanduRodrigo
  Date: 06-Jul-25
  Time: 2:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DrodX-PulseBank | Dashboard</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="dashboard-container">
    <!-- Header Section -->
    <header class="dashboard-header">
        <div class="logo">
            <i class="fas fa-university"></i>
            <h1>DrodX<span>-PulseBank</span></h1>
        </div>
        <div class="customer-profile">
            <div class="customer-info">
                <span class="welcome-message">Welcome back,</span>
                <span class="customer-name">${customer.firstName} ${customer.lastName}</span>
<%--                <span class="account-number">Account: ${customer.accountNumber}</span>--%>
            </div>
            <div class="profile-icon">
                <i class="fas fa-customer-circle"></i>
            </div>
        </div>
    </header>

    <!-- Main Content -->
    <main class="dashboard-content">
        <!-- Account Summary Cards -->
        <section class="summary-cards">
            <div class="card balance-card">
                <div class="card-header">
                    <h3>Account Balance</h3>
                    <i class="fas fa-wallet"></i>
                </div>
                <div class="card-body">

                    <div class="amount">$${account.balance}</div>
                    <div class="account-type">${account.accountType} Account</div>
                </div>
                <div class="card-footer">
                    <a href="#" class="card-link">View Details <i class="fas fa-chevron-right"></i></a>
                </div>
            </div>

            <div class="card transfer-card">
                <div class="card-header">
                    <h3>Quick Transfer</h3>
                    <i class="fas fa-exchange-alt"></i>
                </div>
                <div class="card-body">
                    <form class="transfer-form" action="transfer" method="POST">
                        <input type="hidden" name="fromAccount" value="${account.accountNo}">

                        <div class="form-group">
                            <label for="toAccount">To Account</label>
                            <input type="text" id="toAccount" name="toAccount" placeholder="Enter recipient account number" required>
                        </div>
                        <div class="form-group">
                            <label for="amount">Amount</label>
                            <input type="number" id="amount" name="amount" placeholder="0.00" step="0.01" min="0.01" required>
                        </div>
                        <button type="submit" class="transfer-btn">Transfer Now</button>
                    </form>
                </div>
            </div>

            <div class="card recent-transactions">
                <div class="card-header">
                    <h3>Recent Transactions</h3>
                    <i class="fas fa-history"></i>
                </div>
                <div class="card-body">
                    <div class="transaction-list">
                        <%-- Iterate over the recentTransactions list --%>
                        <c:choose>
                            <c:when test="${not empty sessionScope.recentTransactions}">
                                <c:forEach var="transaction" items="${sessionScope.recentTransactions}">
                                    <div class="transaction-item">
                                        <div class="transaction-icon">
                                                <%-- Determine icon based on transaction type or if it's an incoming/outgoing transaction --%>
                                            <c:set var="isOutgoing" value="${transaction.fromAccount.accountNo == account.accountNo}"/>
                                            <i class="fas <c:if test="${isOutgoing}">fa-arrow-up sent</c:if><c:if test="${!isOutgoing}">fa-arrow-down received</c:if>"></i>
                                        </div>
                                        <div class="transaction-details">
                                            <div class="transaction-title">
                                                    <%-- Display description or a generic message if no description --%>
                                                <c:choose>
                                                    <c:when test="${not empty transaction.description}">
                                                        ${transaction.description}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${isOutgoing}">Transfer to ${transaction.toAccount.accountNo}</c:if>
                                                        <c:if test="${!isOutgoing}">Transfer from ${transaction.fromAccount.accountNo}</c:if>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
<%--                                            <div class="transaction-date">--%>
<%--                                                    &lt;%&ndash; Format the transaction date &ndash;%&gt;--%>
<%--                                                    &lt;%&ndash; Corrected pattern: Use SSS for milliseconds, which is more robust for default LocalDateTime.toString() &ndash;%&gt;--%>
<%--                                                <fmt:parseDate value="${transaction.transactionDate}" pattern="yyyy-MM-dd'T'HH:mm:ss.SSS" var="parsedDate" type="both"/>--%>
<%--                                                <fmt:formatDate value="${parsedDate}" pattern="MMM dd, yyyy, hh:mm a"/>--%>
<%--                                            </div>--%>
                                        </div>
                                        <div class="transaction-amount <c:if test="${isOutgoing}">sent</c:if><c:if test="${!isOutgoing}">received</c:if>">
                                                <%-- Display amount with + or - sign --%>
                                            <c:if test="${isOutgoing}">-</c:if><c:if test="${!isOutgoing}">+</c:if>$<fmt:formatNumber value="${transaction.amount}" pattern="#,##0.00"/>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="transaction-item no-transactions">
                                    <p>No recent transactions to display.</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="card-footer">
                    <a href="#" class="card-link">View All Transactions <i class="fas fa-chevron-right"></i></a>
                </div>
            </div>

        </section>

        <!-- Additional Cards Section (for future expansion) -->
        <section class="additional-cards">
            <div class="card quick-actions">
                <div class="card-header">
                    <h3>Quick Actions</h3>
                    <i class="fas fa-bolt"></i>
                </div>
                <div class="card-body">
                    <div class="action-buttons">
                        <button class="action-btn">
                            <i class="fas fa-money-bill-wave"></i>
                            <span>Pay Bills</span>
                        </button>
                        <button class="action-btn">
                            <i class="fas fa-mobile-alt"></i>
                            <span>Mobile Top-up</span>
                        </button>
                        <button class="action-btn">
                            <i class="fas fa-credit-card"></i>
                            <span>Card Settings</span>
                        </button>
                        <button class="action-btn">
                            <i class="fas fa-cog"></i>
                            <span>Settings</span>
                        </button>
                    </div>
                </div>
            </div>

            <div class="card savings-goal">
                <div class="card-header">
                    <h3>Savings Goal</h3>
                    <i class="fas fa-piggy-bank"></i>
                </div>
                <div class="card-body">
                    <div class="progress-container">
                        <div class="progress-info">
                            <div class="progress-title">New Car Fund</div>
                            <div class="progress-amount">$2,450/$10,000</div>
                        </div>
                        <div class="progress-bar">
                            <div class="progress" style="width: 24.5%"></div>
                        </div>
                        <div class="progress-percentage">24.5%</div>
                    </div>
                </div>
                <div class="card-footer">
                    <a href="#" class="card-link">Manage Goals <i class="fas fa-chevron-right"></i></a>
                </div>
            </div>
        </section>
    </main>

    <!-- Navigation Sidebar -->
    <nav class="dashboard-sidebar">
        <ul class="nav-menu">
            <li class="nav-item">
                <a href="#">
                    <i class="fas fa-home"></i>
                    <span>Dashboard</span>
                </a>
            </li>

            <!-- Enhanced Transfers Menu -->
            <li class="nav-item has-submenu">
                <a href="#" class="submenu-toggle">
                    <i class="fas fa-exchange-alt"></i>
                    <span>Transfers</span>
                    <i class="fas fa-chevron-right submenu-indicator"></i>
                </a>
                <ul class="submenu">
                    <li class="submenu-item">
                        <a href="#" onclick="showTransferForm('immediate')">
                            <i class="fas fa-bolt"></i>
                            <span>Immediate Transfer</span>
                        </a>
                    </li>
                    <li class="submenu-item">
                        <a href="#" onclick="showTransferForm('scheduled')">
                            <i class="fas fa-clock"></i>
                            <span>Scheduled Transfer</span>
                        </a>
                    </li>
                </ul>
            </li>

            <li class="nav-item">
                <a href="#">
                    <i class="fas fa-credit-card"></i>
                    <span>Cards</span>
                </a>
            </li>
            <li class="nav-item">
                <a href="#">
                    <i class="fas fa-chart-line"></i>
                    <span>Investments</span>
                </a>
            </li>
            <li class="nav-item">
                <a href="#">
                    <i class="fas fa-cog"></i>
                    <span>Settings</span>
                </a>
            </li>
            <li class="nav-item">
                <a href="#">
                    <i class="fas fa-question-circle"></i>
                    <span>Help</span>
                </a>
            </li>
            <li class="nav-item logout">
                <a href="signIn.jsp">
                    <i class="fas fa-sign-out-alt"></i>
                    <span>Log Out</span>
                </a>
            </li>
        </ul>
    </nav>



    </div>
<!-- Transfer Forms Section -->

<div id="transferModal" class="modal" style="display: none;">
    <div class="modal-content">
        <section id="transferForms" class="transfer-forms-section-content">
            <div class="section-header">
        <h2 id="transferFormTitle">Transfer Funds</h2>
        <button class="close-transfer-btn" onclick="hideTransferForms()">
            <i class="fas fa-times"></i>
        </button>
    </div>


    <!-- Immediate Transfer Form -->
    <div id="immediateTransferForm" class="transfer-form">
        <form id="immediateTransfer" action="transfer" method="POST">
            <input type="hidden" name="transferType" value="immediate">
            <input type="hidden" name="fromAccount" value="${account.accountNo}">
            <div class="form-row">

                <div class="form-group">
                    <label for="toAccountImmediate">To Account</label>
                    <input type="text" id="toAccountImmediate" name="toAccount"
                           placeholder="Enter recipient account number" required>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="amountImmediate">Amount</label>
                    <div class="input-with-currency">
                        <span class="currency-symbol">$</span>
                        <input type="number" id="amountImmediate" name="amount"
                               min="0.01" step="0.01" placeholder="0.00" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="descriptionImmediate">Description</label>
                    <input type="text" id="descriptionImmediate" name="description"
                           placeholder="Optional reference">
                </div>
            </div>

            <div class="form-actions">
                <button type="button" class="cancel-btn" onclick="hideTransferForms()">Cancel</button>
                <button type="submit" class="submit-btn">
                    <i class="fas fa-paper-plane"></i> Transfer Now
                </button>
            </div>
        </form>
    </div>

    <!-- Scheduled Transfer Form -->
    <div id="scheduledTransferForm" class="transfer-form" style="display: none;">
        <form id="scheduledTransfer" action="ScheduleTransferServlet" method="POST">
            <input type="hidden" name="transferType" value="scheduled">
            <input type="hidden" name="fromAccount" value="${account.accountNo}">
            <div class="form-row">

                <div class="form-group">
                    <label for="toAccountScheduled">To Account</label>
                    <input type="text" id="toAccountScheduled" name="toAccount"
                           placeholder="Enter recipient account number" required>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="amountScheduled">Amount</label>
                    <div class="input-with-currency">
                        <span class="currency-symbol">$</span>
                        <input type="number" id="amountScheduled" name="amount"
                               min="0.01" step="0.01" placeholder="0.00" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="descriptionScheduled">Description</label>
                    <input type="text" id="descriptionScheduled" name="description"
                           placeholder="Optional reference" required>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="transferDate">Transfer Date</label>
                    <input type="date" id="transferDate" name="transferDate"
                           min="${minDate}" required>
                </div>
                <div class="form-group">
                    <label for="recurrence">Recurrence</label>
                    <select id="recurrence" name="recurrence">
                        <option value="once">One-time</option>
                        <option value="daily">Daily</option>
                        <option value="weekly">Weekly</option>
                        <option value="monthly">Monthly</option>
                    </select>
                </div>
            </div>

            <div class="form-actions">
                <button type="button" class="cancel-btn" onclick="hideTransferForms()">Cancel</button>
                <button type="submit" class="submit-btn">
                    <i class="fas fa-calendar-check"></i> Schedule Transfer
                </button>
            </div>
        </form>
    </div>
</section>

<!-- Mandatory Password Change Modal -->
<div id="passwordChangeModal" class="modal mandatory-modal">
    <div class="modal-contentp">
        <div class="modal-header">
            <h3><i class="fas fa-shield-alt"></i> Change Your Password</h3>
            <div class="modal-subtitle">For security reasons, you must change your temporary password</div>
        </div>
        <div class="modal-body">
            <form id="passwordChangeForm" action="ChangePasswordServlet" method="POST">
                <input type="hidden" name="username" value="${customer.nic}">

                <div class="form-group">
                    <label for="currentPassword">Temporary Password</label>
                    <div class="input-with-icon">
                        <i class="fas fa-key"></i>
                        <input type="password" id="currentPassword" name="currentPassword" placeholder="Enter your temporary password" required>
                        <i class="fas fa-eye toggle-password" onclick="togglePasswordVisibility('currentPassword')"></i>
                    </div>
                </div>

                <div class="form-group">
                    <label for="newPassword">New Password</label>
                    <div class="input-with-icon">
                        <i class="fas fa-lock"></i>
                        <input type="password" id="newPassword" name="newPassword" placeholder="Create a new password" required>
                        <i class="fas fa-eye toggle-password" onclick="togglePasswordVisibility('newPassword')"></i>
                    </div>
                    <div class="password-requirements">
                        <p>Your password must contain:</p>
                        <ul>
                            <li id="req-length">At least 8 characters</li>
                            <li id="req-uppercase">One uppercase letter</li>
                            <li id="req-number">One number</li>
                            <li id="req-special">One special character</li>
                        </ul>
                    </div>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Confirm New Password</label>
                    <div class="input-with-icon">
                        <i class="fas fa-lock"></i>
                        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Re-enter your new password" required>
                        <i class="fas fa-eye toggle-password" onclick="togglePasswordVisibility('confirmPassword')"></i>
                    </div>
                    <div id="passwordMatch" class="validation-message"></div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="submit-btn" id="changePasswordBtn">
                        <span class="btn-text">Change Password</span>
                        <i class="fas fa-arrow-right"></i>
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

        <%-- Transfer Success Details Modal --%>
        <div id="transferSuccessModal" class="modal-success-receipt">
            <div class="modal-success-content">
                <div class="modal-success-header">
                    <h3><i class="fas fa-check-circle success-icon"></i> Transfer Successful!</h3>
                    <button class="close-receipt-btn" onclick="hideTransferSuccessModal()">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                <div class="modal-success-body">
                    <p class="modal-success-message">Your immediate transfer has been completed successfully.</p>
                    <div class="receipt-details">
                        <div class="detail-item">
                            <span class="detail-label">Transfer Type:</span>
                            <span class="detail-value">${sessionScope.receiptDetails.transactionType}</span>
                        </div>
                        <div class="detail-item">
                            <span class="detail-label">Transaction Date:</span>
                            <span class="detail-value">${sessionScope.receiptDetails.formattedTransactionDate}</span>
                        </div>
                        <div class="detail-item">
                            <span class="detail-label">Amount:</span>
                            <span class="detail-value">$${sessionScope.receiptDetails.formattedAmount}</span>
                        </div>
                        <div class="detail-item">
                            <span class="detail-label">Description:</span>
                            <span class="detail-value">${not empty sessionScope.receiptDetails.description ? sessionScope.receiptDetails.description : "N/A"}</span>
                        </div>
                        <hr>
                        <h4>Sender Details</h4>
                        <div class="detail-item">
                            <span class="detail-label">Sender Name:</span>
                            <span class="detail-value">${sessionScope.receiptDetails.senderName}</span>
                        </div>
                        <div class="detail-item">
                            <span class="detail-label">Sender Account No:</span>
                            <span class="detail-value">${sessionScope.receiptDetails.senderAccountNo}</span>
                        </div>
                        <div class="detail-item">
                            <span class="detail-label">Sender Account Type:</span>
                            <span class="detail-value">${sessionScope.receiptDetails.senderAccountType}</span>
                        </div>
                        <hr>
                        <h4>Recipient Details</h4>
                        <div class="detail-item">
                            <span class="detail-label">Recipient Account No:</span>
                            <span class="detail-value">${sessionScope.receiptDetails.recipientAccountNo}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-success-footer">
                    <button class="btn btn-primary" onclick="printTransferReceipt()"><i class="fas fa-print"></i> Print Receipt</button>
                    <button class="btn btn-secondary" onclick="hideTransferSuccessModal()">Close</button>
                </div>
            </div>
        </div>



    <div id="statusMessage" class="status-message"></div>
</div>

</div>
<script type="text/javascript">


    document.addEventListener('DOMContentLoaded', function() {

        <c:if test="${sessionScope.firstLogin == true}">
        document.getElementById('passwordChangeModal').style.display = 'block';
        document.body.style.overflow = 'hidden';
        </c:if>

        var PasswordChangedStatus = "${sessionScope.PasswordChanged}";
        if (PasswordChangedStatus === "success") {
            showAlert("Password Changed Successfully!", 'success');
            <% session.removeAttribute("PasswordChanged"); %>
        }

        const transferMessage = "${sessionScope.transferMessage}";
        const transferStatus = "${sessionScope.transferStatus}";


        const showTransferSuccessPopup = <%=(Boolean) session.getAttribute("showTransferSuccessPopup") != null && (Boolean) session.getAttribute("showTransferSuccessPopup") ? "true" : "false"%>;

        if (transferMessage && transferStatus) {
            showAlert(transferMessage, transferStatus);

            <% session.removeAttribute("transferMessage"); %>
            <% session.removeAttribute("transferStatus"); %>


            if (transferStatus === "success" && showTransferSuccessPopup === true) { // Changed to strict boolean comparison
                showTransferSuccessModal();
                <% session.removeAttribute("showTransferSuccessPopup"); %> // Clear the flag

            }
        }
    });
    const CONTEXT_PATH = '${pageContext.request.contextPath}';
</script>
<script src="script.js"></script>
</body>
</html>
