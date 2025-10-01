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
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">🚗 Car Rental</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="login.jsp">Login</a>
                <a class="nav-link" href="register.jsp">Register</a>
            </div>
        </div>
    </nav>

    <div class="hero-section">
        <div class="container">
            <h1 class="display-4 fw-bold">Welcome to Car Rental System</h1>
            <p class="lead">Find the perfect car for your journey</p>
            <a href="login.jsp" class="btn btn-primary btn-lg">Get Started</a>
        </div>
    </div>

    <div class="container mt-5">
        <div class="row text-center">
            <div class="col-md-4">
                <h3>Wide Selection</h3>
                <p>Choose from various car models</p>
            </div>
            <div class="col-md-4">
                <h3>Best Prices</h3>
                <p>Competitive daily rental rates</p>
            </div>
            <div class="col-md-4">
                <h3>Easy Booking</h3>
                <p>Simple and fast reservation process</p>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>