<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%
    // Check if user is already logged in
    User user = (User) session.getAttribute("user");
    if (user != null) {
        // Redirect based on user role
        if ("admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Car Rental System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .login-option-container {
            min-height: 100vh;
            background: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)), url('https://images.unsplash.com/photo-1486496572940-2bb2341fdbdf');
            background-size: cover;
            background-position: center;
            padding: 50px 0;
        }
        .option-card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            border: none;
            height: 100%;
            border-radius: 15px;
        }
        .option-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 15px 35px rgba(0,0,0,0.2);
        }
        .admin-card {
            border-top: 4px solid #dc3545;
        }
        .user-card {
            border-top: 4px solid #0d6efd;
        }
        .btn-back {
            position: absolute;
            top: 20px;
            left: 20px;
            z-index: 1000;
        }
    </style>
</head>
<body>
    <a href="../../index.jsp" class="btn btn-light btn-back">
        <i class="fas fa-arrow-left me-2"></i>Back to Home
    </a>
    
    <div class="login-option-container d-flex align-items-center">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 text-center text-white mb-5">
                    <h1 class="display-4 mb-3">Welcome to DriveEasy Rentals</h1>
                    <p class="lead">Choose your login type to continue your journey</p>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-md-5 mb-4">
                    <div class="card option-card user-card">
                        <div class="card-body text-center p-5">
                            <div class="mb-4">
                                <i class="fas fa-user fa-4x text-primary mb-3"></i>
                                <h3 class="card-title">Customer Login</h3>
                                <p class="card-text text-muted">Access your personal account to book and manage rentals</p>
                            </div>
                            <ul class="list-unstyled mb-4 text-start">
                                <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Book cars instantly</li>
                                <li class="mb-2"><i class="fas fa-check text-success me-2"></i>View booking history</li>
                                <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Manage your profile & preferences</li>
                                <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Track current rentals</li>
                            </ul>
                            <a href="UserLogin.jsp" class="btn btn-primary btn-lg w-100 py-3">
                                <i class="fas fa-sign-in-alt me-2"></i>Customer Login
                            </a>
                            <div class="mt-3">
                                <small class="text-muted">Don't have an account? 
                                    <a href="../register/UserRegistration.jsp" class="text-decoration-none">Register here</a>
                                </small>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-5 mb-4">
                    <div class="card option-card admin-card">
                        <div class="card-body text-center p-5">
                            <div class="mb-4">
                                <i class="fas fa-user-shield fa-4x text-danger mb-3"></i>
                                <h3 class="card-title">Admin Login</h3>
                                <p class="card-text text-muted">Access administrative controls and system management</p>
                            </div>
                            <ul class="list-unstyled mb-4 text-start">
                                <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Manage car inventory & pricing</li>
                                <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Monitor all bookings & payments</li>
                                <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Manage customer accounts</li>
                                <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Generate reports & analytics</li>
                            </ul>
                            <a href="AdminLogin.jsp" class="btn btn-danger btn-lg w-100 py-3">
                                <i class="fas fa-sign-in-alt me-2"></i>Admin Login
                            </a>
                            <div class="mt-3">
                                <small class="text-muted">Restricted access for authorized personnel only</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Security Notice -->
            <div class="row justify-content-center mt-4">
                <div class="col-md-8">
                    <div class="alert alert-info text-center">
                        <i class="fas fa-shield-alt me-2"></i>
                        <strong>Security Notice:</strong> Please ensure you're logging into the correct portal. 
                        Customer and admin accounts have separate access levels.
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>