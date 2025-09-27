

///////////register.jsp////////////////


// Password strength checker
function checkPasswordStrength(password) {
    const strength = {
        level: 0,
        text: 'Weak'
    };

    // Length check
    if (password.length >= 8) strength.level++;
    if (password.length >= 12) strength.level++;

    // Complexity checks
    if (/[A-Z]/.test(password)) strength.level++;
    if (/[0-9]/.test(password)) strength.level++;
    if (/[^A-Za-z0-9]/.test(password)) strength.level++;

    // Determine strength
    if (strength.level <= 2) {
        strength.text = 'Weak';
    } else if (strength.level <= 4) {
        strength.text = 'Medium';
    } else {
        strength.text = 'Strong';
    }

    return strength;
}

// Update password strength indicator
function updatePasswordStrength() {
    const password = document.getElementById('password').value;
    const strength = checkPasswordStrength(password);
    const meter = document.querySelector('.strength-meter');
    const text = document.getElementById('strengthText');

    // Reset classes
    meter.parentElement.classList.remove('password-weak', 'password-medium', 'password-strong');

    // Update based on strength
    if (strength.text === 'Weak') {
        meter.parentElement.classList.add('password-weak');
    } else if (strength.text === 'Medium') {
        meter.parentElement.classList.add('password-medium');
    } else {
        meter.parentElement.classList.add('password-strong');
    }

    text.textContent = strength.text;
}

// Enhanced toggle password visibility
function togglePasswordVisibility(fieldId) {
    const field = document.getElementById(fieldId);
    const toggleIcon = field.nextElementSibling;

    if (field.type === 'password') {
        field.type = 'text';
        toggleIcon.classList.remove('fa-eye');
        toggleIcon.classList.add('fa-eye-slash');
    } else {
        field.type = 'password';
        toggleIcon.classList.remove('fa-eye-slash');
        toggleIcon.classList.add('fa-eye');
    }
}

// Form validation
function validateRegistrationForm() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const termsChecked = document.getElementById('terms').checked;

    // Password match validation
    if (password !== confirmPassword) {
        showAlert('Passwords do not match', 'error');
        return false;
    }

    // Terms agreement validation
    if (!termsChecked) {
        showAlert('You must agree to the terms and conditions', 'error');
        return false;
    }

    return true;
}

// Initialize registration form events
document.addEventListener('DOMContentLoaded', function() {
    // Password strength real-time feedback
    const passwordField = document.getElementById('password');
    if (passwordField) {
        passwordField.addEventListener('input', updatePasswordStrength);
    }

    // Form submission
    const registrationForm = document.getElementById('registrationForm');
    if (registrationForm) {
        registrationForm.addEventListener('submit', function(e) {
            if (!validateRegistrationForm()) {
                e.preventDefault();
                return false;
            }

            // Disable button during submission
            const registerBtn = document.getElementById('registerBtn');
            registerBtn.disabled = true;
            registerBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Creating Account...';


            registrationForm.addEventListener('submit', function(e) {
                if (!validateRegistrationForm()) {
                    e.preventDefault();
                    return false;
                }

                const registerBtn = document.getElementById('registerBtn');
                registerBtn.disabled = true;
                registerBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Creating Account...';
                // Do NOT prevent default submission here
            });

            });

    }
});
///////////signIn.jsp////////////////
document.addEventListener('DOMContentLoaded', function() {
    // Toggle password visibility
    window.togglePasswordVisibility = function() {
        const passwordInput = document.getElementById('password');
        const toggleIcon = document.querySelector('.toggle-password');

        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            toggleIcon.classList.remove('fa-eye');
            toggleIcon.classList.add('fa-eye-slash');
        } else {
            passwordInput.type = 'password';
            toggleIcon.classList.remove('fa-eye-slash');
            toggleIcon.classList.add('fa-eye');
        }
    };

    // Form submission handling
    const loginForm = document.getElementById('loginForm');
    const loginBtn = document.getElementById('loginBtn');

    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            // e.preventDefault();

            // Get form values
            const username = document.getElementById('username').value.trim();
            const password = document.getElementById('password').value;
            const rememberMe = document.getElementById('remember').checked;

            // Simple validation
            if (!username || !password) {
                showAlert('Please fill in all fields', 'error');
                return;
            }

            // Disable button during submission
            loginBtn.disabled = true;
            loginBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Authenticating...';

            // Simulate API call (replace with actual AJAX call)
            setTimeout(() => {
                // This is just for demo - replace with actual authentication logic
                console.log('Login attempt with:', { username, password, rememberMe });

                // For demo purposes, simulate a successful login after 2 seconds
                // In a real application, you would make an AJAX call to your backend
                simulateLoginResponse();
            }, 2000);
        });
    }

    function simulateLoginResponse() {
        // Randomly simulate success or failure for demo purposes
        const isSuccess = Math.random() > 0.2;

        if (isSuccess) {
            showAlert('Login successful! Redirecting...', 'success');
            // Redirect to dashboard after a delay
            setTimeout(() => {
                window.location.href = 'dashboard.jsp';
            }, 1500);
        } else {
            showAlert('Invalid credentials. Please try again.', 'error');
            loginBtn.disabled = false;
            loginBtn.innerHTML = '<span class="btn-text">Sign In</span><i class="fas fa-arrow-right"></i>';
        }
    }

    function showAlert(message, type) {
        // Remove any existing alerts
        const existingAlert = document.querySelector('.custom-alert');
        if (existingAlert) {
            existingAlert.remove();
        }

        // Create alert element
        const alert = document.createElement('div');
        alert.className = `custom-alert ${type}`;
        alert.textContent = message;

        // Add to DOM
        document.body.appendChild(alert);

        // Position alert
        const loginForm = document.querySelector('.login-form');
        if (loginForm) {
            alert.style.top = `${loginForm.offsetTop - 60}px`;
            alert.style.left = `${loginForm.offsetLeft}px`;
            alert.style.width = `${loginForm.offsetWidth}px`;
        }

        // Remove after 3 seconds
        setTimeout(() => {
            alert.style.opacity = '0';
            setTimeout(() => {
                alert.remove();
            }, 300);
        }, 3000);
    }

    // Add styles for custom alerts
    const style = document.createElement('style');
    style.textContent = `
        .custom-alert {
            position: fixed;
            top: 20px;
            left: 50%;
            transform: translateX(-50%);
            padding: 12px 20px;
            border-radius: 4px;
            color: white;
            font-size: 14px;
            font-weight: 500;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            z-index: 1000;
            opacity: 1;
            transition: opacity 0.3s ease;
            max-width: 90%;
            text-align: center;
        }
        
        .custom-alert.success {
            background-color: var(--success-color);
        }
        
        .custom-alert.error {
            background-color: var(--danger-color);
        }
        
        .custom-alert.warning {
            background-color: var(--warning-color);
            color: #333;
        }
    `;
    document.head.appendChild(style);
});

/////////////////verify.jsp////////////////

// Verification Page Specific JavaScript
document.addEventListener('DOMContentLoaded', function() {
    // Auto-focus and move between verification code inputs
    const verificationInputs = document.querySelectorAll('.verification-inputs input');

    verificationInputs.forEach((input, index) => {
        // Move to next input on digit entry
        input.addEventListener('input', function() {
            if (this.value.length === this.maxLength) {
                if (index < verificationInputs.length - 1) {
                    verificationInputs[index + 1].focus();
                }
            }
        });

        // Handle backspace to move to previous input
        input.addEventListener('keydown', function(e) {
            if (e.key === 'Backspace' && this.value.length === 0) {
                if (index > 0) {
                    verificationInputs[index - 1].focus();
                }
            }
        });
    });

    // Countdown timer for verification code
    let timeLeft = 300; // 5 minutes in seconds
    const countdownElement = document.getElementById('countdown');
    const resendButton = document.getElementById('resendCode');

    function updateCountdown() {
        const minutes = Math.floor(timeLeft / 60);
        const seconds = timeLeft % 60;
        countdownElement.textContent = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;

        if (timeLeft <= 0) {
            clearInterval(countdownInterval);
            countdownElement.textContent = "Expired";
            countdownElement.style.color = --danger-color;
            resendButton.classList.remove('disabled');
        } else {
            timeLeft--;
        }
    }

    let countdownInterval = setInterval(updateCountdown, 1000);

    // Resend code functionality
    resendButton.addEventListener('click', function(e) {
        e.preventDefault();

        if (this.classList.contains('disabled')) return;

        // Show loading state
        const originalText = this.textContent;
        this.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Sending...';
        this.classList.add('disabled');

        // Simulate API call to resend code
        setTimeout(() => {
            // Reset countdown
            timeLeft = 300;
            countdownInterval = setInterval(updateCountdown, 1000);
            countdownElement.style.color = (--danger-color);

            // Reset button after 30 seconds
            setTimeout(() => {
                this.textContent = originalText;
            }, 30000);

            showAlert('New verification code sent to your email', 'success');
        }, 1500);
    });

    // Form submission handling
    const verificationForm = document.getElementById('verificationForm');
    if (verificationForm) {
        verificationForm.addEventListener('submit', function(e) {
            const verifyBtn = document.getElementById('verifyBtn');

            // Disable button during submission
            verifyBtn.disabled = true;
            verifyBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Verifying...';

            // Collect all digits into a single code
            const digits = Array.from(document.querySelectorAll('.verification-inputs input'))
                .map(input => input.value)
                .join('');

            // Add hidden field with complete code
            const hiddenInput = document.createElement('input');
            hiddenInput.type = 'hidden';
            hiddenInput.name = 'verificationCode';
            hiddenInput.value = digits;
            this.appendChild(hiddenInput);

            // In a real application, the form would submit to the server
            // For demo, simulate verification after 1.5 seconds
            setTimeout(() => {
                // Randomly simulate success or failure for demo
                const isSuccess = Math.random() > 0.2;

                if (isSuccess) {
                    showAlert('Email verified successfully! Redirecting...', 'success');
                    // Redirect to sign in page after a delay
                    setTimeout(() => {
                        window.location.href = 'signIn.jsp?email=' + encodeURIComponent(document.querySelector('input[name="email"]').value);
                    }, 1500);
                } else {
                    showAlert('Invalid verification code. Please try again.', 'error');
                    verifyBtn.disabled = false;
                    verifyBtn.innerHTML = '<span class="btn-text">Verify Email</span><i class="fas fa-check-circle"></i>';
                }
            }, 1500);

            // Prevent actual form submission for this demo
            e.preventDefault();
        });
    }

    // Try different email functionality
    document.getElementById('tryDifferentEmail').addEventListener('click', function(e) {
        e.preventDefault();
        window.location.href = 'register.jsp';
    });
});

////////////////////index.jsp(dashboard)////////////////////////////////

// Dashboard Specific JavaScript
// document.addEventListener('DOMContentLoaded', function() {
//
//     // Transfer form submission (REMOVE OLD SIMULATION)
//     const transferForm = document.querySelector('.transfer-form');
//     if (transferForm) {
//         transferForm.addEventListener('submit', function(e) {
//             // Client-side validation before submission
//             const toAccount = document.getElementById('toAccount').value;
//             const amount = document.getElementById('amount').value;
//             const transferBtn = this.querySelector('button[type="submit"]');
//
//             if (!toAccount || !amount) {
//                 showAlert('Please fill in all fields', 'error');
//                 e.preventDefault(); // Stop form submission
//                 return;
//             }
//
//             const parsedAmount = parseFloat(amount);
//             if (isNaN(parsedAmount) || parsedAmount <= 0) {
//                 showAlert('Amount must be a positive number.', 'error');
//                 e.preventDefault(); // Stop form submission
//                 return;
//             }
//
//             // Disable button during actual submission
//             transferBtn.disabled = true;
//             transferBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';
//
//             // Allow the form to submit to the servlet
//             // No e.preventDefault() here if you want it to submit
//         });
//     }

    // Logout confirmation
//     const logoutLink = document.querySelector('.nav-item.logout a');
//     if (logoutLink) {
//         logoutLink.addEventListener('click', function(e) {
//             e.preventDefault();
//             if (confirm('Are you sure you want to log out?')) {
//                 // In a real app, you would call your logout endpoint first
//                 window.location.href = this.getAttribute('href');
//             }
//         });
//     }
// });


////////////////////////////transfer sucess model///////////////////
function showAlert(message, type) {
    const statusMessageDiv = document.getElementById('statusMessage');
    if (statusMessageDiv) {
        statusMessageDiv.textContent = message;
        statusMessageDiv.className = 'status-message ' + type; // Add 'success' or 'error' class
        statusMessageDiv.style.display = 'block';
        // No need for a timeout here, as the modal will cover it or it will clear on refresh.
    }
}

function showTransferSuccessModal() {
    const modal = document.getElementById('transferSuccessModal');
    if (modal) {
        modal.style.display = 'flex'; // Use flex to center content
        document.body.style.overflow = 'hidden'; // Prevent scrolling
    }
}

function hideTransferSuccessModal() {
    const modal = document.getElementById('transferSuccessModal');
    if (modal) {
        modal.style.display = 'none';
        document.body.style.overflow = 'auto'; // Re-enable scrolling
    }
    // Note: session attributes are cleared on the server, not directly here.
}

function printTransferReceipt() {
    // Use the globally defined CONTEXT_PATH
    // Ensure CONTEXT_PATH is defined in your JSP's <script> block before script.js is loaded.
    if (typeof CONTEXT_PATH !== 'undefined') {
        window.open(CONTEXT_PATH + '/printReceipt', '_blank');
    } else {
        console.error("CONTEXT_PATH is not defined. Cannot print receipt.");
        alert("Error: Cannot determine application path for printing. Please refresh.");
    }
}
/////////////////changepassword/////////////////////
// Password Change Validation
function validatePasswordChange() {
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const submitBtn = document.getElementById('changePasswordBtn');

    // Password requirements
    const hasLength = newPassword.length >= 8;
    const hasUppercase = /[A-Z]/.test(newPassword);
    const hasNumber = /[0-9]/.test(newPassword);
    const hasSpecial = /[^A-Za-z0-9]/.test(newPassword);

    // Update requirement indicators
    document.getElementById('req-length').className = hasLength ? 'valid' : 'invalid';
    document.getElementById('req-uppercase').className = hasUppercase ? 'valid' : 'invalid';
    document.getElementById('req-number').className = hasNumber ? 'valid' : 'invalid';
    document.getElementById('req-special').className = hasSpecial ? 'valid' : 'invalid';

    // Check if passwords match
    const passwordMatch = document.getElementById('passwordMatch');
    if (newPassword && confirmPassword) {
        if (newPassword === confirmPassword) {
            passwordMatch.textContent = 'Passwords match';
            passwordMatch.className = 'validation-message valid';
        } else {
            passwordMatch.textContent = 'Passwords do not match';
            passwordMatch.className = 'validation-message invalid';
        }
    } else {
        passwordMatch.textContent = '';
        passwordMatch.className = 'validation-message';
    }

    // Enable/disable submit button based on validation
    const isValid = hasLength && hasUppercase && hasNumber && hasSpecial &&
        (newPassword === confirmPassword) && newPassword;
    submitBtn.disabled = !isValid;
}



// Toggle password visibility
function togglePasswordVisibility(fieldId) {
    const field = document.getElementById(fieldId);
    const toggleIcon = field.parentElement.querySelector('.toggle-password');

    if (field.type === 'password') {
        field.type = 'text';
        toggleIcon.classList.remove('fa-eye');
        toggleIcon.classList.add('fa-eye-slash');
    } else {
        field.type = 'password';
        toggleIcon.classList.remove('fa-eye-slash');
        toggleIcon.classList.add('fa-eye');
    }
}

//
// // Initialize password change validation and form submission
// document.addEventListener('DOMContentLoaded', function() {
//     const newPassword = document.getElementById('newPassword');
//     const confirmPassword = document.getElementById('confirmPassword');
//
//     if (newPassword && confirmPassword) {
//         newPassword.addEventListener('input', validatePasswordChange);
//         confirmPassword.addEventListener('input', validatePasswordChange);
//         validatePasswordChange(); // Call on load to set initial state
//     }
//
//     const passwordChangeForm = document.getElementById('passwordChangeForm');
//     if (passwordChangeForm) {
//         passwordChangeForm.addEventListener('submit', function(e) {
//             // e.preventDefault(); // Keep commented if you want a full form submission
//
//             const submitBtn = document.getElementById('changePasswordBtn');
//             submitBtn.disabled = true;
//             submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Updating...';
//
//             // The form will submit normally after this.
//             // Server-side will handle the redirect/message.
//         });
//     }
//
//     /////////////////////////////fund transfer immediate and sheduled////////////////////
//     // Transfer Navigation Functions - Ensure these are outside the passwordChangeForm listener
//     // ... (your existing showTransferForm and hideTransferForms functions and event listener for transferModal click) ...
//
// }); // This closes the main DOMContentLoaded listener

/////////////////////////////fund transfer immediate and sheduled////////////////////


// Transfer Navigation Functions
function showTransferForm(type) {
    // Hide all individual transfer forms within the modal content
    document.querySelectorAll('#immediateTransferForm, #scheduledTransferForm').forEach(form => {
        form.style.display = 'none';
        form.classList.remove('active');
    });

    const transferModal = document.getElementById('transferModal');
    const transferFormTitle = document.getElementById('transferFormTitle');

    transferModal.style.display = 'flex'; // Use flex to center the modal
    document.body.style.overflow = 'hidden'; // Prevent scrolling background
    if (type === 'immediate') {
        const immediateForm = document.getElementById('immediateTransferForm');
        if (immediateForm) {
            immediateForm.style.display = 'flex';
            immediateForm.classList.add('active');
            if (transferFormTitle) transferFormTitle.textContent = 'Immediate Transfer';
        }
    } else if (type === 'scheduled') {
        const scheduledForm = document.getElementById('scheduledTransferForm');
        if (scheduledForm) {
            scheduledForm.style.display = 'flex';
            scheduledForm.classList.add('active');
            if (transferFormTitle) transferFormTitle.textContent = 'Scheduled Transfer';

            const today = new Date();
            const tomorrow = new Date(today);
            tomorrow.setDate(tomorrow.getDate() + 1);

            const minDate = tomorrow.toISOString().split('T')[0];
            const transferDateInput = document.getElementById('transferDate');
            if (transferDateInput) {
                transferDateInput.min = minDate;
            }
        }
    }
}
//     if (type === 'immediate') {
//         document.getElementById('immediateTransferForm').style.display = 'flex'; // Use flex for form layout
//         document.getElementById('immediateTransferForm').classList.add('active');
//         transferFormTitle.textContent = 'Immediate Transfer';
//     } else if (type === 'scheduled') {
//         document.getElementById('scheduledTransferForm').style.display = 'flex'; // Use flex for form layout
//         document.getElementById('scheduledTransferForm').classList.add('active');
//         transferFormTitle.textContent = 'Scheduled Transfer';
//
//         // Set minimum date for scheduled transfer (tomorrow)
//         const today = new Date();
//         const tomorrow = new Date(today);
//         tomorrow.setDate(tomorrow.getDate() + 1);
//
//         const minDate = tomorrow.toISOString().split('T')[0];
//         const transferDateInput = document.getElementById('transferDate');
//         if (transferDateInput) {
//             transferDateInput.min = minDate;
//         }
//     }
//
//     // No need to scroll if it's a centered modal
// }
// // Transfer Navigation Functions - Keep these global for onclick in JSP
// function showTransferForm(type) {
//     // Hide all transfer forms first
//     document.querySelectorAll('#immediateTransferForm, #scheduledTransferForm').forEach(form => {
//         form.style.display = 'none';
//         form.classList.remove('active');
//     });
//
//     // Show the selected form section
//     const formSection = document.getElementById('transferForms');
//     const formTitle = document.getElementById('transferFormTitle');
//
//     formSection.style.display = 'block'; // Make the main section visible
//
//     if (type === 'immediate') {
//         document.getElementById('immediateTransferForm').style.display = 'block';
//         document.getElementById('immediateTransferForm').classList.add('active');
//         formTitle.textContent = 'Immediate Transfer';
//     } else if (type === 'scheduled') {
//         document.getElementById('scheduledTransferForm').style.display = 'block';
//         document.getElementById('scheduledTransferForm').classList.add('active');
//         formTitle.textContent = 'Scheduled Transfer';
//
//         // Set minimum date for scheduled transfer (tomorrow)
//         const today = new Date();
//         const tomorrow = new Date(today);
//         tomorrow.setDate(tomorrow.getDate() + 1);
//
//         const minDate = tomorrow.toISOString().split('T')[0];
//         document.getElementById('transferDate').min = minDate;
//     }
//
//     // Scroll to the form section
//     formSection.scrollIntoView({ behavior: 'smooth' });
// }

function hideTransferForms() {
    const transferModal = document.getElementById('transferModal');
    if (transferModal) {
        transferModal.style.display = 'none';
        document.body.style.overflow = ''; // Restore scrolling
    }
}


// Main DOMContentLoaded Listener (Consolidate all DOM-related logic here)
document.addEventListener('DOMContentLoaded', function() {
    // Dashboard Specific JavaScript
    const transferForm = document.querySelector('.transfer-form');
    if (transferForm) {
        transferForm.addEventListener('submit', function(e) {
            const toAccount = document.getElementById('toAccount').value;
            const amount = document.getElementById('amount').value;
            const transferBtn = this.querySelector('button[type="submit"]');

            if (!toAccount || !amount) {
                showAlert('Please fill in all fields', 'error');
                e.preventDefault();
                return;
            }

            const parsedAmount = parseFloat(amount);
            if (isNaN(parsedAmount) || parsedAmount <= 0) {
                showAlert('Amount must be a positive number.', 'error');
                e.preventDefault();
                return;
            }

            transferBtn.disabled = true;
            transferBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';
        });
    }

    // Logout confirmation
    const logoutLink = document.querySelector('.nav-item.logout a');
    if (logoutLink) {
        logoutLink.addEventListener('click', function(e) {
            e.preventDefault();
            if (confirm('Are you sure you want to log out?')) {
                window.location.href = this.getAttribute('href');
            }
        });
    }

    // Mandatory Password Change Modal Logic
    const newPassword = document.getElementById('newPassword');
    const confirmPassword = document.getElementById('confirmPassword');
    if (newPassword && confirmPassword) {
        newPassword.addEventListener('input', validatePasswordChange);
        confirmPassword.addEventListener('input', validatePasswordChange);
        validatePasswordChange(); // Call on load to set initial state
    }

    const passwordChangeForm = document.getElementById('passwordChangeForm');
    if (passwordChangeForm) {
        passwordChangeForm.addEventListener('submit', function(e) {
            const submitBtn = document.getElementById('changePasswordBtn');
            submitBtn.disabled = true;
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Updating...';
        });
    }

    // Transfer Modal backdrop click handler
    const transferModal = document.getElementById('transferModal');
    if (transferModal) {
        transferModal.addEventListener('click', function(event) {
            if (event.target === transferModal) {
                hideTransferForms();
            }
        });
    }

    // Add event listener for the new transfer success modal close button
    const closeReceiptBtn = document.querySelector('.close-receipt-btn');
    if (closeReceiptBtn) {
        closeReceiptBtn.addEventListener('click', hideTransferSuccessModal);
    }

    // Also add event listener for the "Close" button in the footer of the receipt modal
    const closeReceiptFooterBtn = document.querySelector('#transferSuccessModal .modal-success-footer .btn-secondary');
    if (closeReceiptFooterBtn) {
        closeReceiptFooterBtn.addEventListener('click', hideTransferSuccessModal);
    }

    // And for the Print button
    const printReceiptBtn = document.querySelector('#transferSuccessModal .modal-success-footer .btn-primary');
    if (printReceiptBtn) {
        printReceiptBtn.addEventListener('click', printTransferReceipt);
    }

    // Initialize the password change form validation on load
    if (document.getElementById('newPassword')) {
        validatePasswordChange();
    }
});
// Main DOMContentLoaded logic
// document.addEventListener('DOMContentLoaded', function() {
//     // Submenu Toggle Functionality
//     const submenuToggles = document.querySelectorAll('.submenu-toggle');
//
//     submenuToggles.forEach(toggle => {
//         toggle.addEventListener('click', function(e) {
//             e.preventDefault(); // Prevent default link behavior
//             const parentItem = this.closest('.nav-item');
//             const submenu = this.nextElementSibling; // This is the <ul>.submenu
//
//             // Toggle active class on the parent nav-item
//             parentItem.classList.toggle('active');
//
//             // Toggle submenu visibility based on active class
//             if (parentItem.classList.contains('active')) {
//                 // Use max-height for smooth transition with CSS
//                 submenu.style.maxHeight = submenu.scrollHeight + "px";
//                 submenu.style.opacity = "1";
//             } else {
//                 submenu.style.maxHeight = "0";
//                 submenu.style.opacity = "0";
//             }
//
//             // Close other open submenus
//             document.querySelectorAll('.nav-item.has-submenu').forEach(item => {
//                 if (item !== parentItem) {
//                     item.classList.remove('active');
//                     const otherSubmenu = item.querySelector('.submenu');
//                     if (otherSubmenu) {
//                         otherSubmenu.style.maxHeight = "0";
//                         otherSubmenu.style.opacity = "0";
//                     }
//                 }
//             });
//         });
//     });
//
//     // Handle form submissions - remove the simulation!
//     const immediateForm = document.getElementById('immediateTransfer');
//     if (immediateForm) {
//         immediateForm.addEventListener('submit', function(e) {
//             // No e.preventDefault() here unless you have client-side validation
//             // that should prevent submission.
//             // If the form passes client-side validation, let it submit naturally.
//
//             const submitBtn = this.querySelector('button[type="submit"]');
//             submitBtn.disabled = true;
//             submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';
//             // The form will now submit to TransferServlet
//         });
//     }
//
//     const scheduledForm = document.getElementById('scheduledTransfer');
//     if (scheduledForm) {
//         scheduledForm.addEventListener('submit', function(e) {
//             // No e.preventDefault() here unless you have client-side validation
//             // that should prevent submission.
//             const submitBtn = this.querySelector('button[type="submit"]');
//             submitBtn.disabled = true;
//             submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';
//             // The form will now submit to TransferServlet
//         });
//     }
// });

// function processTransferForm(form) {
//     const submitBtn = form.querySelector('button[type="submit"]');
//     const originalText = submitBtn.innerHTML;
//
//     submitBtn.disabled = true;
//     submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';
//
//     // Simulate form submission
//     setTimeout(() => {
//         const transferType = form.querySelector('input[name="transferType"]').value;
//         const amount = form.querySelector('input[name="amount"]').value;
//
//         showAlert(`${transferType === 'immediate' ? 'Immediate' : 'Scheduled'} transfer of $${amount} initiated successfully!`, 'success');
//
//         submitBtn.disabled = false;
//         submitBtn.innerHTML = originalText;
//         hideTransferForms();
//
//         // In a real application, you would submit the form
//         // form.submit();
//     }, 1500);
// }
/////////////////////////admin-dashboard.jsp////////////////////

// Admin Dashboard JavaScript
document.addEventListener('DOMContentLoaded', function() {
    // // Initialize DataTable
    $('#customersTable').DataTable({
        responsive: true,
        dom: '<"top"f>rt<"bottom"lip><"clear">',
        language: {
            search: "_INPUT_",
            searchPlaceholder: "Search customers..."
        }
    });

    // Remove the client-side form submission handler for addCustomerForm
    // because it's now handled by server-side redirect.
    // The alert will be triggered by dashboard.jsp after the redirect.
    // You might want to keep the submit button's loading state for a moment
    // before the redirect happens, but the alert will come from the server.
    const addCustomerForm = document.getElementById('addCustomerForm');
    if (addCustomerForm) {
        addCustomerForm.addEventListener('submit', function(e) {
            // No e.preventDefault() here, let the form submit normally
            const submitBtn = this.querySelector('button[type="submit"]');
            submitBtn.disabled = true;
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';
            // The actual redirect will happen on the server, so no client-side timeout needed.
            // The button state will reset on page reload.
        });
    }
    // // Add Customer Form Submission
    // const addCustomerForm = document.getElementById('addCustomerForm');
    // if (addCustomerForm) {
    //     addCustomerForm.addEventListener('submit', function(e) {
    //         e.preventDefault();
    //
    //         const submitBtn = this.querySelector('button[type="submit"]');
    //         submitBtn.disabled = true;
    //         submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';
    //
    //         // Simulate API call
    //         setTimeout(() => {
    //             showAlert('Customer added successfully!', 'success');
    //             closeModal('addCustomerModal');
    //             submitBtn.disabled = false;
    //             submitBtn.textContent = 'Create Customer';
    //             this.reset();
    //
    //             // In a real app, you would refresh the customer list
    //             // refreshCustomers();
    //         }, 2000);
    //     });
    // }

    // Transfer Funds Form Submission
    const transferFundsForm = document.getElementById('transferFundsForm');
    if (transferFundsForm) {
        transferFundsForm.addEventListener('submit', function(e) {
            e.preventDefault();

            const fromAccount = document.getElementById('fromAccount').value;
            const toAccount = document.getElementById('toAccount').value;
            const amount = document.getElementById('amount').value;
            const submitBtn = this.querySelector('button[type="submit"]');

            if (fromAccount === toAccount) {
                showAlert('Cannot transfer to the same account!', 'error');
                return;
            }

            submitBtn.disabled = true;
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';

            // Use fetch API for transfer (assuming you have a transferFunds servlet)
            fetch('transfer', { // Update this URL if your transfer servlet has a different path
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    fromAccount: fromAccount,
                    toAccount: toAccount,
                    amount: amount,
                    description: document.getElementById('description').value // Include description
                })
            })
                .then(response => {
                    // If it's a redirect, handle it here. If it returns JSON, parse it.
                    // For a form submission resulting in a redirect, you might not get JSON
                    if (response.redirected) {
                        window.location.href = response.url; // Follow the redirect
                        return; // Stop further processing
                    }
                    return response.json(); // If your servlet returns JSON
                })
                .then(data => {
                    // This block executes if the servlet returns JSON (e.g., success/error message)
                    if (data && data.status === 'success') { // Adjust based on your servlet's JSON structure
                        showAlert(data.message || `Successfully transferred $${amount} from account ${fromAccount} to ${toAccount}`, 'success');
                        closeModal('transferFundsModal');
                        // Optionally refresh account balance here if not using WebSockets
                    } else if (data && data.status === 'error') {
                        showAlert(data.message || 'Transfer failed!', 'error');
                    }
                    submitBtn.disabled = false;
                    submitBtn.textContent = 'Transfer Funds';
                    this.reset();
                })
                .catch(error => {
                    console.error('Error:', error);
                    showAlert('An error occurred during transfer.', 'error');
                    submitBtn.disabled = false;
                    submitBtn.textContent = 'Transfer Funds';
                });
        });
    }
});
// Modal Functions
function openModal(modalId) {
    document.getElementById(modalId).style.display = 'block';
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
}

// Close modal when clicking outside
window.onclick = function(event) {
    if (event.target.classList.contains('modal')) {
        event.target.style.display = 'none';
    }
}
// function editCustomer(customerId) {
//     // In a real app, you would fetch customer data via AJAX
//     console.log('Editing customer:', customerId);
//     openModal('editCustomerModal');
//
//     // Simulate loading customer data
//     const modalBody = document.querySelector('#editCustomerModal .modal-body');
//     modalBody.innerHTML = `
//         <div class="loading-spinner">
//             <i class="fas fa-spinner fa-spin"></i> Loading customer data...
//         </div>
//     `;
//
//     setTimeout(() => {
//         modalBody.innerHTML = `
//             <form id="editCustomerForm">
//                 <input type="hidden" name="customerId" value="${customerId}">
//                 <div class="form-row">
//                     <div class="form-group">
//                         <label for="editFirstName">First Name</label>
//                         <input type="text" id="editFirstName" name="firstName" value="John" required>
//                     </div>
//                     <div class="form-group">
//                         <label for="editLastName">Last Name</label>
//                         <input type="text" id="editLastName" name="lastName" value="Doe" required>
//                     </div>
//                 </div>
//
//                 <!-- Add Gender Field -->
//                 <div class="form-group">
//                     <label for="editGender">Gender</label>
//                     <select id="editGender" name="gender" required>
//                         <option value="male" selected>Male</option>
//                         <option value="female">Female</option>
//                         <option value="other">Other</option>
//                         <option value="prefer-not-to-say">Prefer not to say</option>
//                     </select>
//                 </div>
//
//                 <!-- Add NIC Number Field -->
//                 <div class="form-group">
//                     <label for="editNic">NIC Number</label>
//                     <input type="text" id="editNic" name="nic" value="123456789V" required
//                            pattern="[0-9]{9}[VvXx]|[0-9]{12}"
//                            title="Enter valid NIC number">
//                 </div>
//
//                 <div class="form-group">
//                     <label for="editEmail">Email Address</label>
//                     <input type="email" id="editEmail" name="email" value="john.doe@example.com" required>
//                 </div>
//                 <div class="form-group">
//                     <label for="editPhone">Phone Number</label>
//                     <input type="tel" id="editPhone" name="phone" value="+1 (555) 123-4567" required>
//                 </div>
//
//                 <!-- Add Postal Code Field -->
//                 <div class="form-group">
//                     <label for="editPostalCode">Postal Code</label>
//                     <input type="text" id="editPostalCode" name="postalCode" value="12345"
//                            pattern="[0-9]{5}"
//                            title="5-digit postal code" required>
//                 </div>
//
//                 <div class="form-group">
//                     <label for="editStatus">Account Status</label>
//                     <select id="editStatus" name="status" required>
//                         <option value="active" selected>Active</option>
//                         <option value="inactive">Inactive</option>
//                     </select>
//                 </div>
//                 <div class="form-actions">
//                     <button type="button" class="cancel-btn" onclick="closeModal('editCustomerModal')">Cancel</button>
//                     <button type="submit" class="submit-btn">Save Changes</button>
//                 </div>
//             </form>
//         `;
//
//         // Add form submission handler
//         document.getElementById('editCustomerForm').addEventListener('submit', function(e) {
//             e.preventDefault();
//             const submitBtn = this.querySelector('button[type="submit"]');
//             submitBtn.disabled = true;
//             submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Saving...';
//
//             setTimeout(() => {
//                 showAlert('Customer updated successfully!', 'success');
//                 closeModal('editCustomerModal');
//                 // refreshCustomers();
//             }, 1500);
//         });
//     }, 1000);
// }

async function fetchCustomerData(customerId) {
    const response = await fetch(`customer-action?action=edit-get&customerId=${customerId}`);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const result = await response.json();
    if (result.data) {
        return result.data;
    } else {
        throw new Error(result.message || 'Failed to fetch customer data.');
    }
}

async function editCustomer(customerId) {
    console.log('Editing customer:', customerId);
    openModal('editCustomerModal');

    const modalBody = document.querySelector('#editCustomerModal .modal-body');
    modalBody.innerHTML = `
        <div class="loading-spinner">
            <i class="fas fa-spinner fa-spin"></i> Loading customer data...
        </div>
    `;

    try {
        const customer = await fetchCustomerData(customerId);
        // Populate modal with fetched data
        modalBody.innerHTML = `
            <form id="editCustomerForm">
                <input type="hidden" name="customerId" value="${customer.id}">
                <div class="form-row">
                    <div class="form-group">
                        <label for="editFirstName">First Name</label>
                        <input type="text" id="editFirstName" name="firstName" value="${customer.firstName || ''}" required>
                    </div>
                    <div class="form-group">
                        <label for="editLastName">Last Name</label>
                        <input type="text" id="editLastName" name="lastName" value="${customer.lastName || ''}" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="editGender">Gender</label>
                    <select id="editGender" name="gender" required>
                        <option value="">Select Gender</option>
                        <option value="MALE" ${customer.gender === 'MALE' ? 'selected' : ''}>Male</option>
                        <option value="FEMALE" ${customer.gender === 'FEMALE' ? 'selected' : ''}>Female</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="editNic">NIC Number</label>
                    <input type="text" id="editNic" name="nic" value="${customer.nic || ''}" required
                           pattern="[0-9]{9}[VvXx]|[0-9]{12}"
                           title="Enter valid NIC number">
                </div>

                <div class="form-group">
                    <label for="editEmail">Email Address</label>
                    <input type="email" id="editEmail" name="email" value="${customer.email || ''}" required>
                </div>
                <div class="form-group">
                    <label for="editContact">Contact Number</label>
                    <input type="tel" id="editContact" name="contact" value="${customer.contact || ''}" required>
                </div>
                <div class="form-group">
                    <label for="editDob">Date of Birth</label>
                    <input type="date" id="editDob" name="dob" value="${customer.dob || ''}" required>
                </div>
                <div class="form-group">
                    <label for="editAddress">Address</label>
                    <textarea id="editAddress" name="address" rows="3" required>${customer.address || ''}</textarea>
                </div>
                <div class="form-group">
                    <label for="editPostalCode">Postal Code</label>
                    <input type="text" id="editPostalCode" name="postalCode" value="${customer.postalCode || ''}"
                           pattern="[0-9]{5}"
                           title="5-digit postal code" required>
                </div>

                <div class="form-group">
                    <label for="editStatus">Account Status</label>
                    <select id="editStatus" name="status" required>
                        <option value="ACTIVE" ${customer.accountStatus === 'ACTIVE' ? 'selected' : ''}>Active</option>
                        <option value="INACTIVE" ${customer.accountStatus === 'INACTIVE' ? 'selected' : ''}>Inactive</option>
                        <option value="SUSPENDED" ${customer.accountStatus === 'SUSPENDED' ? 'selected' : ''}>Suspended</option>
                        <option value="CLOSED" ${customer.accountStatus === 'CLOSED' ? 'selected' : ''}>Closed</option>
                    </select>
                </div>
                <div class="form-actions">
                    <button type="button" class="cancel-btn" onclick="closeModal('editCustomerModal')">Cancel</button>
                    <button type="submit" class="submit-btn">Save Changes</button>
                </div>
            </form>
        `;

        // Add form submission handler for the dynamically loaded form
        document.getElementById('editCustomerForm').addEventListener('submit', async function(e) {
            e.preventDefault();
            const submitBtn = this.querySelector('button[type="submit"]');
            submitBtn.disabled = true;
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Saving...';

            const formData = new URLSearchParams(new FormData(this)).toString();

            try {
                const response = await fetch(`customer-action?action=edit-save`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: formData
                });

                const result = await response.json();

                if (response.ok && result.data) {
                    showAlert(result.message, 'success');
                    closeModal('editCustomerModal');
                    // Update the specific row in the DataTable
                    updateCustomerRow(result.data);
                } else {
                    showAlert(result.message || 'Failed to save changes.', 'error');
                }
            } catch (error) {
                console.error('Error saving customer:', error);
                showAlert('An error occurred while saving changes.', 'error');
            } finally {
                submitBtn.disabled = false;
                submitBtn.textContent = 'Save Changes';
            }
        });

    } catch (error) {
        console.error('Error fetching customer data:', error);
        showAlert(error.message, 'error');
        closeModal('editCustomerModal');
    }
}

// function viewCustomer(customerId) {
//     // In a real app, you would fetch customer data via AJAX
//     console.log('Viewing customer:', customerId);
//     openModal('viewCustomerModal');
//
//     // Simulate loading customer data
//     const modalBody = document.querySelector('#viewCustomerModal .modal-body');
//     modalBody.innerHTML = `
//         <div class="loading-spinner">
//             <i class="fas fa-spinner fa-spin"></i> Loading customer details...
//         </div>
//     `;
//
//     setTimeout(() => {
//         modalBody.innerHTML = `
//             <div class="customer-details">
//                 <div class="detail-row">
//                     <span class="detail-label">Customer ID:</span>
//                     <span class="detail-value">${customerId}</span>
//                 </div>
//                 <div class="detail-row">
//                     <span class="detail-label">Full Name:</span>
//                     <span class="detail-value">John Doe</span>
//                 </div>
//                 <div class="detail-row">
//                     <span class="detail-label">Gender:</span>
//                     <span class="detail-value">Male</span>
//                 </div>
//                 <div class="detail-row">
//                     <span class="detail-label">NIC Number:</span>
//                     <span class="detail-value">123456789V</span>
//                 </div>
//                 <div class="detail-row">
//                     <span class="detail-label">Email:</span>
//                     <span class="detail-value">john.doe@example.com</span>
//                 </div>
//                 <div class="detail-row">
//                     <span class="detail-label">Phone:</span>
//                     <span class="detail-value">+1 (555) 123-4567</span>
//                 </div>
//                 <div class="detail-row">
//                     <span class="detail-label">Date of Birth:</span>
//                     <span class="detail-value">01/15/1985</span>
//                 </div>
//                 <div class="detail-row">
//                     <span class="detail-label">Address:</span>
//                     <span class="detail-value">123 Main St, Anytown, USA</span>
//                 </div>
//                 <div class="detail-row">
//                     <span class="detail-label">Postal Code:</span>
//                     <span class="detail-value">12345</span>
//                 </div>
//                 <div class="detail-row">
//                     <span class="detail-label">Status:</span>
//                     <span class="detail-value status-badge active">Active</span>
//                 </div>
//                 <div class="detail-row">
//                     <span class="detail-label">Accounts:</span>
//                     <div class="accounts-list">
//                         <div class="account-item">
//                             <i class="fas fa-wallet"></i>
//                             <div class="account-info">
//                                 <div class="account-number">1001001001</div>
//                                 <div class="account-type">Savings Account</div>
//                             </div>
//                             <div class="account-balance">$5,250.00</div>
//                         </div>
//                         <div class="account-item">
//                             <i class="fas fa-credit-card"></i>
//                             <div class="account-info">
//                                 <div class="account-number">1001001002</div>
//                                 <div class="account-type">Checking Account</div>
//                             </div>
//                             <div class="account-balance">$1,875.50</div>
//                         </div>
//                     </div>
//                 </div>
//             </div>
//         `;
//     }, 1000);
// }
async function viewCustomer(customerId) {
    console.log('Viewing customer:', customerId);
    openModal('viewCustomerModal');

    const modalBody = document.querySelector('#viewCustomerModal .modal-body');
    modalBody.innerHTML = `
        <div class="loading-spinner">
            <i class="fas fa-spinner fa-spin"></i> Loading customer details...
        </div>
    `;

    try {
        const customer = await fetchCustomerData(customerId);
        // Populate modal with fetched data
        modalBody.innerHTML = `
            <div class="customer-details">
                <div class="detail-row">
                    <span class="detail-label">Customer ID:</span>
                    <span class="detail-value">${customer.id || 'N/A'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Full Name:</span>
                    <span class="detail-value">${customer.firstName || ''} ${customer.lastName || ''}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Gender:</span>
                    <span class="detail-value">${customer.gender || 'N/A'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">NIC Number:</span>
                    <span class="detail-value">${customer.nic || 'N/A'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Email:</span>
                    <span class="detail-value">${customer.email || 'N/A'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Contact:</span>
                    <span class="detail-value">${customer.contact || 'N/A'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Date of Birth:</span>
                    <span class="detail-value">${customer.dob || 'N/A'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Address:</span>
                    <span class="detail-value">${customer.address || 'N/A'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Postal Code:</span>
                    <span class="detail-value">${customer.postalCode || 'N/A'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Status:</span>
                    <span class="detail-value status-badge ${customer.accountStatus === 'ACTIVE' ? 'active' : 'inactive'}">
                        ${customer.accountStatus || 'N/A'}
                    </span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Accounts:</span>
                    <div class="accounts-list">
                        <div class="account-item">
                            <i class="fas fa-wallet"></i>
                            <div class="account-info">
                                <div class="account-number">Fetching...</div>
                                <div class="account-type">Loading...</div>
                            </div>
                            <div class="account-balance">...</div>
                        </div>
                    </div>
                </div>
            </div>
        `;
        // TODO: Implement fetching actual accounts for this customer
    } catch (error) {
        console.error('Error fetching customer data for view:', error);
        showAlert(error.message, 'error');
        closeModal('viewCustomerModal');
    }
}
// function activateCustomer(customerId) {
//     if (confirm('Are you sure you want to activate this customer?')) {
//         // Simulate API call
//         showAlert('Customer activated successfully!', 'success');
//         // refreshCustomers();
//     }
// }
//
// function deactivateCustomer(customerId) {
//     if (confirm('Are you sure you want to deactivate this customer?')) {
//         // Simulate API call
//         showAlert('Customer deactivated successfully!', 'success');
//         // refreshCustomers();
//     }
// }
//
// function refreshCustomers() {
//     // In a real app, this would reload customer data from the server
//     console.log('Refreshing customer list...');
//     showAlert('Customer list refreshed', 'info');
// }
//

async function updateCustomerStatus(customerId, newStatus) {
    if (!confirm(`Are you sure you want to set this customer's status to ${newStatus}?`)) {
        return;
    }

    const btn = document.getElementById(`toggle-status-btn-${customerId}`);
    const originalContent = btn.innerHTML;
    btn.disabled = true;
    btn.innerHTML = '<i class="fas fa-spinner fa-spin"></i>';

    try {
        const response = await fetch(`customer-action?action=${newStatus.toLowerCase()}&customerId=${customerId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
        });

        const result = await response.json();

        if (response.ok) {
            showAlert(result.message, 'success');
            // Update UI directly without full page reload
            const statusBadge = document.getElementById(`status-badge-${customerId}`);
            statusBadge.textContent = newStatus;
            // Update class based on status
            if (newStatus === 'ACTIVE') {
                statusBadge.classList.remove('inactive', 'suspended', 'closed');
                statusBadge.classList.add('active');
                btn.classList.remove('activate-btn');
                btn.classList.add('deactivate-btn');
                btn.setAttribute('onclick', `deactivateCustomer('${customerId}')`);
                btn.querySelector('i').className = 'fas fa-user-slash';
            } else {
                statusBadge.classList.remove('active', 'suspended', 'closed');
                statusBadge.classList.add('inactive'); // Or specific for INACTIVE, SUSPENDED, CLOSED
                btn.classList.remove('deactivate-btn');
                btn.classList.add('activate-btn');
                btn.setAttribute('onclick', `activateCustomer('${customerId}')`);
                btn.querySelector('i').className = 'fas fa-user-check';
            }
        } else {
            showAlert(result.message || `Failed to update customer status to ${newStatus}.`, 'error');
        }
    } catch (error) {
        console.error('Error updating customer status:', error);
        showAlert('An error occurred while updating customer status.', 'error');
    } finally {
        btn.disabled = false;
        btn.innerHTML = originalContent; // Restore original icon/text
        // Refresh DataTable (optional, if you want full table refresh)
        // refreshCustomers();
    }
}

function activateCustomer(customerId) {
    updateCustomerStatus(customerId, 'ACTIVE');
}

function deactivateCustomer(customerId) {
    updateCustomerStatus(customerId, 'INACTIVE'); // You might want a specific status like 'SUSPENDED' for deactivation
}

// Function to refresh DataTable data via AJAX
async function refreshCustomers() {
    console.log('Refreshing customer list...');
    showAlert('Refreshing customer list...', 'info');

    try {
        const response = await fetch('/dashboard?action=getCustomers'); // New action for AdminDashboardServlet to return only customer list
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const customers = await response.json(); // Assuming it returns a JSON array of customers

        // Clear existing data from DataTable
        dataTable.clear();

        // Add new data
        customers.forEach(customer => {
            const statusClass = customer.accountStatus === 'ACTIVE' ? 'active' : 'inactive'; // Assuming 'inactive' for other statuses
            const statusIcon = customer.accountStatus === 'ACTIVE' ? 'fa-user-slash' : 'fa-user-check';
            const statusAction = customer.accountStatus === 'ACTIVE' ? 'deactivateCustomer' : 'activateCustomer';

            // Add row to DataTable. Order of columns: ID, Name, Email, Contact, NIC, Status, Actions
            dataTable.row.add([
                customer.id,
                `${customer.firstName || ''} ${customer.lastName || ''}`,
                customer.email || 'N/A',
                customer.contact || 'N/A',
                customer.nic || 'N/A',
                `<span id="status-badge-${customer.id}" class="status-badge ${statusClass}">${customer.accountStatus || 'N/A'}</span>`,
                `<button class="action-icon edit-btn" onclick="editCustomer('${customer.id}')"><i class="fas fa-edit"></i></button>` +
                `<button class="action-icon view-btn" onclick="viewCustomer('${customer.id}')"><i class="fas fa-eye"></i></button>` +
                `<button id="toggle-status-btn-${customer.id}" class="action-icon ${statusClass === 'active' ? 'deactivate-btn' : 'activate-btn'}" onclick="${statusAction}('${customer.id}')"><i class="fas ${statusIcon}"></i></button>`
            ]);
        });

        // Redraw the table
        dataTable.draw();
        showAlert('Customer list refreshed successfully.', 'success');

    } catch (error) {
        console.error('Error refreshing customer list:', error);
        showAlert('Failed to refresh customer list: ' + error.message, 'error');
    }
}

// Function to update a single customer row after edit/status change
function updateCustomerRow(customer) {
    const rowElement = document.getElementById(`customer-row-${customer.id}`);
    if (!rowElement) {
        console.warn(`Row for customer ID ${customer.id} not found. Reloading table.`);
        refreshCustomers(); // Fallback to full refresh if row not found (e.g., if sorting changed)
        return;
    }

    // Get the DataTable instance for the table
    // Find the row by its node
    const dataTableRow = dataTable.row(rowElement);

    if (dataTableRow.length === 0) {
        console.warn(`DataTable row object for customer ID ${customer.id} not found. Reloading table.`);
        refreshCustomers();
        return;
    }

    const statusClass = customer.accountStatus === 'ACTIVE' ? 'active' : 'inactive';
    const statusIcon = customer.accountStatus === 'ACTIVE' ? 'fa-user-slash' : 'fa-user-check';
    const statusAction = customer.accountStatus === 'ACTIVE' ? 'deactivateCustomer' : 'activateCustomer';

    // Update the data for the row. Ensure the order matches DataTable's column order.
    dataTableRow.data([
        customer.id,
        `${customer.firstName || ''} ${customer.lastName || ''}`,
        customer.email || 'N/A',
        customer.contact || 'N/A',
        customer.nic || 'N/A',
        `<span id="status-badge-${customer.id}" class="status-badge ${statusClass}">${customer.accountStatus || 'N/A'}</span>`,
        `<button class="action-icon edit-btn" onclick="editCustomer('${customer.id}')"><i class="fas fa-edit"></i></button>` +
        `<button class="action-icon view-btn" onclick="viewCustomer('${customer.id}')"><i class="fas fa-eye"></i></button>` +
        `<button id="toggle-status-btn-${customer.id}" class="action-icon ${statusClass === 'active' ? 'deactivate-btn' : 'activate-btn'}" onclick="${statusAction}('${customer.id}')"><i class="fas ${statusIcon}"></i></button>`
    ]).draw(false); // draw(false) redraws the row without re-sorting/filtering the entire table
}



// Alert notification function
function showAlert(message, type) {
    const alert = document.createElement('div');
    alert.className = `admin-alert ${type}`;
    alert.innerHTML = `
        <span>${message}</span>
        <button class="close-alert" onclick="this.parentElement.remove()">&times;</button>
    `;

    document.body.appendChild(alert);

    setTimeout(() => {
        alert.remove();
    }, 5000);
}

// Add alert styles dynamically
const alertStyles = document.createElement('style');
alertStyles.textContent = `
    .admin-alert {
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 1rem 1.5rem;
        border-radius: var(--border-radius);
        box-shadow: var(--box-shadow);
        display: flex;
        align-items: center;
        justify-content: space-between;
        z-index: 1000;
        animation: slideIn 0.3s ease-out;
        max-width: 400px;
    }

    @keyframes slideIn {
        from { transform: translateX(100%); opacity: 0; }
        to { transform: translateX(0); opacity: 1; }
    }

    .admin-alert.success {
        background: #d4edda;
        color: #155724;
        border-left: 4px solid #28a745;
    }

    .admin-alert.error {
        background: #f8d7da;
        color: #721c24;
        border-left: 4px solid #dc3545;
    }

    .admin-alert.info {
        background: #d1ecf1;
        color: #0c5460;
        border-left: 4px solid #17a2b8;
    }

    .close-alert {
        background: transparent;
        border: none;
        color: inherit;
        font-size: 1.2rem;
        margin-left: 1rem;
        cursor: pointer;
        padding: 0 0.3rem;
    }
`;
document.head.appendChild(alertStyles);
