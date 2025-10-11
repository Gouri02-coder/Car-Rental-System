<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Car Rental System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .hero-section {
            background: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url('https://images.unsplash.com/photo-1449965408869-eaa3f722e40d');
            background-size: cover;
            background-position: center;
            color: white;
            padding: 100px 0;
            text-align: center;
        }
        .feature-icon {
            font-size: 3rem;
            color: #007bff;
            margin-bottom: 1rem;
        }
        .nav-link.btn {
            padding: 0.375rem 0.75rem;
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">
                <i class="fas fa-car me-2"></i><strong>Car Rental System</strong>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="index.jsp">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#features">Features</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#about">About</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="auth/login/LoginOption.jsp">
                            <i class="fas fa-sign-in-alt me-1"></i>Login
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link btn btn-primary text-white ms-2" href="auth/register/UserRegistration.jsp">
                            <i class="fas fa-user-plus me-1"></i>Register
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <div class="hero-section">
        <div class="container">
            <h1 class="display-4 fw-bold">Find Your Perfect Ride</h1>
            <p class="lead">Discover the best car rental deals for your journey</p>
            <div class="mt-4">
                <a href="auth/register/UserRegistration.jsp" class="btn btn-primary btn-lg me-3">
                    <i class="fas fa-rocket me-2"></i>Get Started
                </a>
                <a href="auth/login/LoginOption.jsp" class="btn btn-outline-light btn-lg">
                    <i class="fas fa-sign-in-alt me-2"></i>Login
                </a>
            </div>
        </div>
    </div>

    <!-- Features Section -->
    <div id="features" class="container mt-5 py-5">
        <div class="row text-center mb-5">
            <div class="col-12">
                <h2 class="display-5 mb-3">Why Choose Our Car Rental Service?</h2>
                <p class="lead text-muted">Experience the best in car rental with our premium features</p>
            </div>
        </div>
        <div class="row text-center">
            <div class="col-md-4 mb-4">
                <div class="feature-icon">
                    <i class="fas fa-car-side"></i>
                </div>
                <h3>Wide Selection</h3>
                <p class="text-muted">Choose from various car models including economy, luxury, and SUVs.</p>
            </div>
            <div class="col-md-4 mb-4">
                <div class="feature-icon">
                    <i class="fas fa-tag"></i>
                </div>
                <h3>Best Prices</h3>
                <p class="text-muted">Competitive daily rental rates with no hidden charges.</p>
            </div>
            <div class="col-md-4 mb-4">
                <div class="feature-icon">
                    <i class="fas fa-calendar-check"></i>
                </div>
                <h3>Easy Booking</h3>
                <p class="text-muted">Simple and fast reservation process with instant confirmation.</p>
            </div>
        </div>
    </div>

    <!-- Call to Action -->
    <div class="bg-light py-5">
        <div class="container text-center">
            <h2>Ready to Start Your Journey?</h2>
            <p class="lead mb-4">Join thousands of satisfied customers who trust our car rental service</p>
            <div class="d-flex justify-content-center gap-3 flex-wrap">
                <a href="auth/register/UserRegistration.jsp" class="btn btn-primary btn-lg">
                    <i class="fas fa-user-plus me-2"></i>Create Your Account
                </a>
                <a href="auth/login/LoginOption.jsp" class="btn btn-outline-primary btn-lg">
                    <i class="fas fa-sign-in-alt me-2"></i>Login to Your Account
                </a>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="bg-dark text-white py-4 mt-5">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <h5><i class="fas fa-car me-2"></i>Car Rental System</h5>
                    <p class="mb-0">&copy; 2024 Car Rental System. All rights reserved.</p>
                </div>
                <div class="col-md-6 text-md-end">
                    <h5>Quick Links</h5>
                    <div class="d-flex flex-column">
                        <a href="auth/login/LoginOption.jsp" class="text-white text-decoration-none mb-2">
                            <i class="fas fa-sign-in-alt me-1"></i>Login
                        </a>
                        <a href="auth/register/UserRegistration.jsp" class="text-white text-decoration-none mb-2">
                            <i class="fas fa-user-plus me-1"></i>Register
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>