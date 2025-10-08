<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - Car Rental System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
        }
        .register-card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <!-- Back to Home -->
                <div class="text-center mb-4">
                    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-outline-light">
                        ← Back to Home
                    </a>
                </div>

                <div class="card register-card">
                    <div class="card-header bg-white border-0 text-center pt-4">
                        <h3 class="fw-bold text-primary">Create Account</h3>
                        <p class="text-muted">Join our car rental community</p>
                    </div>
                    <div class="card-body p-4">
                        <!-- Display Messages -->
                        <% 
                            String error = (String) request.getAttribute("errorMessage");
                            if (error != null) {
                        %>
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <%= error %>
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        <%
                            }
                        %>
                        
                        <!-- Registration Form -->
                        <form action="${pageContext.request.contextPath}/UserServlet" method="post">
                            <input type="hidden" name="action" value="register">
                            
                            <div class="row">
                                <div class="col-md-12 mb-3">
                                    <label for="name" class="form-label">Full Name</label>
                                    <input type="text" class="form-control form-control-lg" 
                                           id="name" name="name" placeholder="Enter your full name" required>
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="email" class="form-label">Email Address</label>
                                <input type="email" class="form-control form-control-lg" 
                                       id="email" name="email" placeholder="Enter your email" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" class="form-control form-control-lg" 
                                       id="password" name="password" placeholder="Create a password" required>
                            </div>
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="phone" class="form-label">Phone Number</label>
                                    <input type="tel" class="form-control form-control-lg" 
                                           id="phone" name="phone" placeholder="Phone number">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="address" class="form-label">Address</label>
                                    <input type="text" class="form-control form-control-lg" 
                                           id="address" name="address" placeholder="Your address">
                                </div>
                            </div>
                            
                            <div class="d-grid mb-3">
                                <button type="submit" class="btn btn-primary btn-lg">Create Account</button>
                            </div>
                        </form>
                        
                        <!-- Divider -->
                        <div class="text-center mb-3">
                            <span class="text-muted">Already have an account?</span>
                        </div>
                        
                        <!-- Login Link -->
                        <div class="d-grid">
                            <a href="${pageContext.request.contextPath}/auth/login.jsp" class="btn btn-outline-primary btn-lg">
                                Sign In Instead
                            </a>
                        </div>
                    </div>
                </div>
                
                <!-- Additional Links -->
                <div class="text-center mt-3">
                    <a href="${pageContext.request.contextPath}/index.jsp" class="text-white">← Return to Homepage</a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Simple Form Validation -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.querySelector('form');
            const password = document.getElementById('password');
            
            form.addEventListener('submit', function(e) {
                if (password.value.length < 6) {
                    e.preventDefault();
                    alert('Password must be at least 6 characters long');
                    password.focus();
                }
            });
        });
    </script>
</body>
</html>