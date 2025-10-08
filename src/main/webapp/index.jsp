<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Car Rental System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
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
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">
                <strong>Car Rental System</strong>
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
                        <a class="nav-link" href="auth/login.jsp">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link btn btn-primary text-white ms-2" href="auth/register.jsp">Register</a>
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
                <a href="auth/register.jsp" class="btn btn-primary btn-lg me-3">Get Started</a>
                <a href="auth/login.jsp" class="btn btn-outline-light btn-lg">Login</a>
            </div>
        </div>
    </div>

    <!-- Features Section -->
    <div id="features" class="container mt-5 py-5">
        <div class="row text-center">
            <div class="col-md-4 mb-4">
                <div class="feature-icon">Wide Selection</div>
                <h3>Wide Selection</h3>
                <p class="text-muted">Choose from various car models including economy, luxury, and SUVs</p>
            </div>
            <div class="col-md-4 mb-4">
                <div class="feature-icon">Best Prices</div>
                <h3>Best Prices</h3>
                <p class="text-muted">Competitive daily rental rates with no hidden charges</p>
            </div>
            <div class="col-md-4 mb-4">
                <div class="feature-icon">Easy Booking</div>
                <h3>Easy Booking</h3>
                <p class="text-muted">Simple and fast reservation process with instant confirmation</p>
            </div>
        </div>
    </div>

    <!-- Call to Action -->
    <div class="bg-light py-5">
        <div class="container text-center">
            <h2>Ready to Start Your Journey?</h2>
            <p class="lead mb-4">Join thousands of satisfied customers who trust our car rental service</p>
            <a href="auth/register.jsp" class="btn btn-primary btn-lg">Create Your Account Now</a>
        </div>
    </div>

    <!-- Footer -->
    <footer class="bg-dark text-white py-4 mt-5">
        <div class="container text-center">
            <p>&copy; 2024 Car Rental System. All rights reserved.</p>
            <div>
                <a href="auth/login.jsp" class="text-white me-3">Login</a>
                <a href="auth/register.jsp" class="text-white me-3">Register</a>
                <a href="#privacy" class="text-white">Privacy Policy</a>
            </div>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>