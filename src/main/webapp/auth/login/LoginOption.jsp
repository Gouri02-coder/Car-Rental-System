<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Car Rental System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .login-option-container {
            height: 100vh;
            background: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)), url('https://images.unsplash.com/photo-1486496572940-2bb2341fdbdf');
            background-size: cover;
            background-position: center;
        }
        .option-card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            border: none;
            height: 100%;
        }
        .option-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
        }
        .admin-card {
            border-top: 4px solid #dc3545;
        }
        .user-card {
            border-top: 4px solid #0d6efd;
        }
    </style>
</head>
<body>
    <div class="login-option-container d-flex align-items-center">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 text-center text-white mb-5">
                    <h1 class="display-4 mb-3">Welcome to Car Rental System</h1>
                    <p class="lead">Choose your login type to continue</p>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-md-5 mb-4">
                    <div class="card option-card user-card">
                        <div class="card-body text-center p-5">
                            <div class="mb-4">
                                <i class="fas fa-user fa-4x text-primary mb-3"></i>
                                <h3 class="card-title">User Login</h3>
                                <p class="card-text text-muted">Login to book cars and manage your rentals</p>
                            </div>
                            <ul class="list-unstyled mb-4 text-start">
                                <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Book cars online</li>
                                <li class="mb-2"><i class="fas fa-check text-success me-2"></i>View booking history</li>
                                <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Manage your profile</li>
                            </ul>
                            <a href="UserLogin.jsp" class="btn btn-primary btn-lg w-100">
                                <i class="fas fa-sign-in-alt me-2"></i>User Login
                            </a>
                        </div>
                    </div>
                </div>
                <div class="col-md-5 mb-4">
                    <div class="card option-card admin-card">
                        <div class="card-body text-center p-5">
                            <div class="mb-4">
                                <i class="fas fa-user-shield fa-4x text-danger mb-3"></i>
                                <h3 class="card-title">Admin Login</h3>
                                <p class="card-text text-muted">Login to manage system and oversee operations</p>
                            </div>
                            <ul class="list-unstyled mb-4 text-start">
                                <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Manage car inventory</li>
                                <li class="mb-2"><i class="fas fa-check text-success me-2"></i>View all bookings</li>
                                <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Manage users</li>
                            </ul>
                            <a href="AdminLogin.jsp" class="btn btn-danger btn-lg w-100">
                                <i class="fas fa-sign-in-alt me-2"></i>Admin Login
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-md-10 text-center">
                    <a href="../../index.jsp" class="btn btn-outline-light btn-lg">
                        <i class="fas fa-arrow-left me-2"></i>Back to Home
                    </a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>