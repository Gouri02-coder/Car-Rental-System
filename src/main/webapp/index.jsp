<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>DriveEasy Rentals | Your Journey Starts Here</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        :root {
            --primary: #2c3e50;
            --secondary: #3498db;
            --accent: #e74c3c;
            --light: #ecf0f1;
            --dark: #2c3e50;
            --gray: #95a5a6;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
        }
        
        .hero-section {
            background: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)), url('https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80');
            background-size: cover;
            background-position: center;
            color: white;
            padding: 120px 0;
            text-align: center;
        }
        
        .search-container {
            max-width: 600px;
            margin: 0 auto;
        }
        
        .search-box {
            background: white;
            border-radius: 50px;
            padding: 5px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }
        
        .search-input {
            border: none;
            outline: none;
            padding: 15px 20px;
            border-radius: 50px;
            width: 100%;
            font-size: 16px;
        }
        
        .search-btn {
            background: var(--accent);
            color: white;
            border: none;
            border-radius: 50px;
            padding: 15px 30px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.3s;
            white-space: nowrap;
        }
        
        .search-btn:hover {
            background: #c0392b;
        }
        
        .feature-icon {
            font-size: 3rem;
            color: var(--secondary);
            margin-bottom: 1rem;
        }
        
        .section-title {
            text-align: center;
            margin: 4rem 0 2rem;
            position: relative;
        }
        
        .section-title h2 {
            font-size: 2.5rem;
            color: var(--dark);
            display: inline-block;
            padding-bottom: 0.5rem;
        }
        
        .section-title h2::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 80px;
            height: 4px;
            background: var(--secondary);
        }
        
        .car-card {
            background: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s;
            height: 100%;
        }
        
        .car-card:hover {
            transform: translateY(-5px);
        }
        
        .car-img {
            height: 200px;
            background-size: cover;
            background-position: center;
        }
        
        .car-content {
            padding: 1.5rem;
        }
        
        .car-price {
            font-size: 1.5rem;
            font-weight: 700;
            color: var(--secondary);
        }
        
        .car-price span {
            font-size: 1rem;
            color: var(--gray);
            font-weight: normal;
        }
        
        .rent-btn {
            background: var(--accent);
            color: white;
            border: none;
            padding: 0.75rem 1.5rem;
            border-radius: 4px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.3s;
            width: 100%;
            margin-top: 1rem;
            text-decoration: none;
            display: inline-block;
            text-align: center;
        }
        
        .rent-btn:hover {
            background: #c0392b;
            color: white;
        }
        
        .navbar-brand {
            font-weight: 700;
        }
        
        .navbar-brand span {
            color: var(--secondary);
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">
                <i class="fas fa-car me-2"></i><strong>Drive<span>Easy</span> Rentals</strong>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="index.jsp">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#inventory">Browse Cars</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#features">Features</a>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto">
                    <%
                        User user = (User) session.getAttribute("user");
                        if (user != null) {
                    %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                            <i class="fas fa-user me-1"></i><%= user.getName() %>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="user/dashboard.jsp">
                                <i class="fas fa-tachometer-alt me-2"></i>Dashboard
                            </a></li>
                            <li><a class="dropdown-item" href="user/profile.jsp">
                                <i class="fas fa-user me-2"></i>Profile
                            </a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <a class="dropdown-item text-danger" href="<%= request.getContextPath() %>/UserCtrl?action=logout">
                                    <i class="fas fa-sign-out-alt me-2"></i>Logout
                                </a>
                            </li>
                        </ul>
                    </li>
                    <%
                        } else {
                    %>
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
                    <%
                        }
                    %>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <div class="hero-section">
        <div class="container">
            <h1 class="display-4 fw-bold mb-4">Find Your Perfect Ride</h1>
            <p class="lead mb-5">Discover the best car rental deals for your next adventure. From economy to luxury, we have the perfect vehicle for every journey.</p>
            
            <!-- Search Bar -->
            <div class="search-container">
                <form action="user/car/browse-cars.jsp" method="get" class="d-flex search-box">
                    <input type="text" 
                           name="query" 
                           class="search-input" 
                           placeholder="Search by car type, brand, or features..."
                           required>
                    <button type="submit" class="search-btn ms-2">
                        <i class="fas fa-search me-2"></i>Search Cars
                    </button>
                </form>
            </div>
        </div>
    </div>

    <!-- Inventory Section -->
    <div id="inventory" class="container mt-5 py-5">
        <div class="section-title">
            <h2>Our Car Inventory</h2>
            <p class="lead text-muted">Browse our wide selection of vehicles</p>
        </div>
        
        <div class="row g-4">
            <!-- Car 1 - Buggati Chiron -->
            <div class="col-md-4">
                <div class="car-card">
                    <div class="car-img" style="background-image: url('https://images.unsplash.com/photo-1544636331-e26879cd4d9b?ixlib=rb-4.0.3&auto=format&fit=crop&w=1074&q=80');"></div>
                    <div class="car-content">
                        <h4>Buggati Chiron</h4>
                        <p class="text-muted">HyperCar | 2 Seats | Automatic</p>
                        <div class="car-price">10 lakhs<span>/day</span></div>
                        <p>Perfect for hyper driving and fuel efficiency.</p>
                        <%
                            if (user != null) {
                        %>
                        <a href="booking/BookCar.jsp?carId=1" class="rent-btn">Rent This Car</a>
                        <%
                            } else {
                        %>
                        <a href="auth/login/UserLogin.jsp?redirect=booking&carId=1" class="rent-btn">Rent This Car</a>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
            
            <!-- Car 2 - Honda CR-V -->
            <div class="col-md-4">
                <div class="car-card">
                    <div class="car-img" style="background-image: url('https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?ixlib=rb-4.0.3&auto=format&fit=crop&w=1170&q=80');"></div>
                    <div class="car-content">
                        <h4>Honda CR-V</h4>
                        <p class="text-muted">SUV | 5 Seats | Automatic</p>
                        <div class="car-price">2000<span>/day</span></div>
                        <p>Spacious SUV perfect for family trips.</p>
                        <%
                            if (user != null) {
                        %>
                        <a href="booking/BookCar.jsp?carId=2" class="rent-btn">Rent This Car</a>
                        <%
                            } else {
                        %>
                        <a href="auth/login/UserLogin.jsp?redirect=booking&carId=2" class="rent-btn">Rent This Car</a>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
            
            <!-- Car 3 - Porsche Taycan -->
            <div class="col-md-4">
                <div class="car-card">
                    <div class="car-img" style="background-image: url('https://images.unsplash.com/photo-1503376780353-7e6692767b70?ixlib=rb-4.0.3&auto=format&fit=crop&w=1170&q=80');"></div>
                    <div class="car-content">
                        <h4>Porsche Taycan</h4>
                        <p class="text-muted">Luxury | 5 Seats | Automatic</p>
                        <div class="car-price">3 lakhs<span>/day</span></div>
                        <p>Premium luxury sedan for business or pleasure.</p>
                        <%
                            if (user != null) {
                        %>
                        <a href="booking/BookCar.jsp?carId=3" class="rent-btn">Rent This Car</a>
                        <%
                            } else {
                        %>
                        <a href="auth/login/UserLogin.jsp?redirect=booking&carId=3" class="rent-btn">Rent This Car</a>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>

            <!-- Car 4 - Ferrari SF 90 -->
            <div class="col-md-4">
                <div class="car-card">
                    <div class="car-img" style="background-image: url('https://images.unsplash.com/photo-1583121274602-3e2820c69888?ixlib=rb-4.0.3&auto=format&fit=crop&w=1074&q=80');"></div>
                    <div class="car-content">
                        <h4>Ferrari SF 90</h4>
                        <p class="text-muted">SuperCar | 2 Seats | Automatic</p>
                        <div class="car-price">1.5 lakhs<span>/day</span></div>
                        <p>Perfect for hyper driving and fuel efficiency.</p>
                        <%
                            if (user != null) {
                        %>
                        <a href="booking/BookCar.jsp?carId=4" class="rent-btn">Rent This Car</a>
                        <%
                            } else {
                        %>
                        <a href="auth/login/UserLogin.jsp?redirect=booking&carId=4" class="rent-btn">Rent This Car</a>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>

            <!-- Car 5 - BMW 5 Series -->
            <div class="col-md-4">
                <div class="car-card">
                    <div class="car-img" style="background-image: url('https://images.unsplash.com/photo-1555215695-3004980ad54e?ixlib=rb-4.0.3&auto=format&fit=crop&w=1074&q=80');"></div>
                    <div class="car-content">
                        <h4>BMW 5 Series</h4>
                        <p class="text-muted">Luxury | 5 Seats | Automatic</p>
                        <div class="car-price">50k<span>/day</span></div>
                        <p>Luxury sedan with premium features.</p>
                        <%
                            if (user != null) {
                        %>
                        <a href="booking/BookCar.jsp?carId=5" class="rent-btn">Rent This Car</a>
                        <%
                            } else {
                        %>
                        <a href="auth/login/UserLogin.jsp?redirect=booking&carId=5" class="rent-btn">Rent This Car</a>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>

            <!-- Car 6 - Ford Mustang -->
            <div class="col-md-4">
                <div class="car-card">
                    <div class="car-img" style="background-image: url('https://images.unsplash.com/photo-1584345604376-a69e7d06163f?ixlib=rb-4.0.3&auto=format&fit=crop&w=1074&q=80');"></div>
                    <div class="car-content">
                        <h4>Ford Mustang</h4>
                        <p class="text-muted">Sports | 4 Seats | Manual</p>
                        <div class="car-price">45k<span>/day</span></div>
                        <p>Classic sports car for enthusiasts.</p>
                        <%
                            if (user != null) {
                        %>
                        <a href="booking/BookCar.jsp?carId=6" class="rent-btn">Rent This Car</a>
                        <%
                            } else {
                        %>
                        <a href="auth/login/UserLogin.jsp?redirect=booking&carId=6" class="rent-btn">Rent This Car</a>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Features Section -->
    <div id="features" class="bg-light py-5">
        <div class="container">
            <div class="section-title">
                <h2>Why Choose DriveEasy?</h2>
            </div>
            
            <div class="row g-4">
                <div class="col-md-4">
                    <div class="feature-card text-center">
                        <div class="feature-icon">
                            <i class="fas fa-dollar-sign"></i>
                        </div>
                        <h3>Best Price Guarantee</h3>
                        <p class="text-muted">We offer competitive prices with no hidden fees.</p>
                    </div>
                </div>
                
                <div class="col-md-4">
                    <div class="feature-card text-center">
                        <div class="feature-icon">
                            <i class="fas fa-car"></i>
                        </div>
                        <h3>Wide Selection</h3>
                        <p class="text-muted">Choose from economy cars, luxury sedans, SUVs, and more.</p>
                    </div>
                </div>
                
                <div class="col-md-4">
                    <div class="feature-card text-center">
                        <div class="feature-icon">
                            <i class="fas fa-shield-alt"></i>
                        </div>
                        <h3>Fully Insured</h3>
                        <p class="text-muted">Comprehensive insurance coverage for peace of mind.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="bg-dark text-white py-4">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-6">
                    <h5><i class="fas fa-car me-2"></i>DriveEasy Rentals</h5>
                    <p class="mb-0">&copy; 2024 DriveEasy Rentals. All rights reserved.</p>
                </div>
                <div class="col-md-6 text-md-end">
                    <div class="social-links">
                        <a href="#" class="text-white me-3"><i class="fab fa-facebook fa-lg"></i></a>
                        <a href="#" class="text-white me-3"><i class="fab fa-twitter fa-lg"></i></a>
                        <a href="#" class="text-white me-3"><i class="fab fa-instagram fa-lg"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>