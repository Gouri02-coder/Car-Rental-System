<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration - Car Rental System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .registration-container {
            min-height: 100vh;
            background: linear-gradient(rgba(13, 110, 253, 0.8), rgba(13, 110, 253, 0.9)), url('https://images.unsplash.com/photo-1549317661-bd32c8ce0db2');
            background-size: cover;
            background-position: center;
            padding: 50px 0;
        }
        .registration-card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.1);
        }
        .form-icon {
            color: #0d6efd;
        }
        .password-strength {
            height: 5px;
            margin-top: 5px;
            border-radius: 5px;
        }
        .btn-back {
            position: absolute;
            top: 20px;
            left: 20px;
        }
    </style>
</head>
<body>
    <div class="registration-container d-flex align-items-center">
        <a href="../login/LoginOption.jsp" class="btn btn-light btn-back">
            <i class="fas fa-arrow-left me-2"></i>Back to Login
        </a>
        
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">
                    <div class="card registration-card">
                        <div class="card-body p-5">
                            <div class="text-center mb-4">
                                <i class="fas fa-user-plus fa-3x form-icon mb-3"></i>
                                <h3>Create Your Account</h3>
                                <p class="text-muted">Join our car rental community today</p>
                            </div>

                            <!-- Display error/success messages -->
                            <% 
                                String errorMessage = (String) request.getAttribute("errorMessage");
                                String successMessage = (String) request.getAttribute("successMessage");
                            %>
                            
                            <% if (errorMessage != null) { %>
                                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                    <i class="fas fa-exclamation-triangle me-2"></i>
                                    <%= errorMessage %>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                </div>
                            <% } %>
                            
                            <% if (successMessage != null) { %>
                                <div class="alert alert-success alert-dismissible fade show" role="alert">
                                    <i class="fas fa-check-circle me-2"></i>
                                    <%= successMessage %>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                </div>
                            <% } %>

                            <!-- FIXED FORM ACTION -->
                            <form action="${pageContext.request.contextPath}/UserCtrl" method="POST">
                                <input type="hidden" name="action" value="register">
                                
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="firstName" class="form-label">First Name</label>
                                        <div class="input-group">
                                            <span class="input-group-text bg-light border-end-0">
                                                <i class="fas fa-user form-icon"></i>
                                            </span>
                                            <input type="text" class="form-control border-start-0" id="firstName" name="firstName" 
                                                   placeholder="Enter first name" value="${param.firstName}" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="lastName" class="form-label">Last Name</label>
                                        <input type="text" class="form-control" id="lastName" name="lastName" 
                                               placeholder="Enter last name" value="${param.lastName}" required>
                                    </div>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email Address</label>
                                    <div class="input-group">
                                        <span class="input-group-text bg-light border-end-0">
                                            <i class="fas fa-envelope form-icon"></i>
                                        </span>
                                        <input type="email" class="form-control border-start-0" id="email" name="email" 
                                               placeholder="Enter your email" value="${param.email}" required>
                                    </div>
                                </div>
                                
                               <div class="mb-3">
								    <label for="phone" class="form-label">Phone Number</label>
								    <div class="input-group">
								        <span class="input-group-text bg-light border-end-0">
								            <i class="fas fa-phone form-icon"></i>
								        </span>
								        <input type="tel" class="form-control border-start-0" id="phone" name="phone" 
								               placeholder="Enter phone number" value="${param.phone}" required 
								               pattern="[0-9]{10}" title="Please enter a 10-digit phone number">
								    </div>
								    <small class="form-text text-muted">Enter 10-digit phone number</small>
								</div>	
                                <div class="mb-3">
                                    <label for="address" class="form-label">Address</label>
                                    <div class="input-group">
                                        <span class="input-group-text bg-light border-end-0">
                                            <i class="fas fa-home form-icon"></i>
                                        </span>
                                        <textarea class="form-control border-start-0" id="address" name="address" 
                                                  placeholder="Enter your address" rows="2" required>${param.address}</textarea>
                                    </div>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="licenseNumber" class="form-label">Driving License Number</label>
                                    <div class="input-group">
                                        <span class="input-group-text bg-light border-end-0">
                                            <i class="fas fa-id-card form-icon"></i>
                                        </span>
                                        <input type="text" class="form-control border-start-0" id="licenseNumber" name="licenseNumber" 
                                               placeholder="Enter driving license number" value="${param.licenseNumber}" required>
                                    </div>
                                </div>
                                
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="password" class="form-label">Password</label>
                                        <div class="input-group">
                                            <span class="input-group-text bg-light border-end-0">
                                                <i class="fas fa-lock form-icon"></i>
                                            </span>
                                            <input type="password" class="form-control border-start-0" id="password" name="password" 
                                                   placeholder="Create password" required>
                                            <button class="btn btn-outline-secondary" type="button" id="togglePassword">
                                                <i class="fas fa-eye"></i>
                                            </button>
                                        </div>
                                        <div class="password-strength bg-secondary" id="passwordStrength"></div>
                                        <div class="form-text">Minimum 8 characters with letters and numbers</div>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="confirmPassword" class="form-label">Confirm Password</label>
                                        <div class="input-group">
                                            <span class="input-group-text bg-light border-end-0">
                                                <i class="fas fa-lock form-icon"></i>
                                            </span>
                                            <input type="password" class="form-control border-start-0" id="confirmPassword" name="confirmPassword" 
                                                   placeholder="Confirm password" required>
                                            <button class="btn btn-outline-secondary" type="button" id="toggleConfirmPassword">
                                                <i class="fas fa-eye"></i>
                                            </button>
                                        </div>
                                        <div class="form-text" id="passwordMatch"></div>
                                    </div>
                                </div>
                                
                                <div class="mb-3 form-check">
                                    <input type="checkbox" class="form-check-input" id="terms" required>
                                    <label class="form-check-label" for="terms">
                                        I agree to the <a href="#" class="text-decoration-none">Terms and Conditions</a>
                                    </label>
                                </div>
                                
                                <div class="d-grid mb-3">
                                    <button type="submit" class="btn btn-primary btn-lg">
                                        <i class="fas fa-user-plus me-2"></i>Create Account
                                    </button>
                                </div>
                                
                                <div class="text-center">
                                    <p class="mb-0">Already have an account? 
                                        <a href="../login/UserLogin.jsp" class="text-decoration-none fw-bold">Login here</a>
                                    </p>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Toggle password visibility
        document.getElementById('togglePassword').addEventListener('click', function() {
            const passwordInput = document.getElementById('password');
            const icon = this.querySelector('i');
            togglePasswordVisibility(passwordInput, icon);
        });

        document.getElementById('toggleConfirmPassword').addEventListener('click', function() {
            const confirmPasswordInput = document.getElementById('confirmPassword');
            const icon = this.querySelector('i');
            togglePasswordVisibility(confirmPasswordInput, icon);
        });

        function togglePasswordVisibility(input, icon) {
            if (input.type === 'password') {
                input.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else {
                input.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        }

        // Password strength indicator
        document.getElementById('password').addEventListener('input', function() {
            const password = this.value;
            const strengthBar = document.getElementById('passwordStrength');
            let strength = 0;
            
            if (password.length >= 8) strength++;
            if (password.match(/[a-z]/)) strength++;
            if (password.match(/[A-Z]/)) strength++;
            if (password.match(/[0-9]/)) strength++;
            if (password.match(/[^a-zA-Z0-9]/)) strength++;
            
            const colors = ['bg-danger', 'bg-danger', 'bg-warning', 'bg-info', 'bg-success', 'bg-success'];
            const widths = ['20%', '40%', '60%', '80%', '100%', '100%'];
            
            strengthBar.className = 'password-strength ' + colors[strength];
            strengthBar.style.width = widths[strength];
        });

        // Password match validation
        document.getElementById('confirmPassword').addEventListener('input', function() {
            const password = document.getElementById('password').value;
            const confirmPassword = this.value;
            const matchText = document.getElementById('passwordMatch');
            
            if (confirmPassword === '') {
                matchText.textContent = '';
                matchText.className = 'form-text';
            } else if (password === confirmPassword) {
                matchText.textContent = '✓ Passwords match';
                matchText.className = 'form-text text-success fw-bold';
            } else {
                matchText.textContent = '✗ Passwords do not match';
                matchText.className = 'form-text text-danger fw-bold';
            }
        });

        // Form validation
        document.querySelector('form').addEventListener('submit', function(e) {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            const terms = document.getElementById('terms').checked;
            
            if (password !== confirmPassword) {
                e.preventDefault();
                alert('Passwords do not match!');
                return false;
            }
            
            if (!terms) {
                e.preventDefault();
                alert('Please agree to the Terms and Conditions');
                return false;
            }
            
            if (password.length < 8) {
                e.preventDefault();
                alert('Password must be at least 8 characters long');
                return false;
            }
        });
    </script>
</body>
</html>