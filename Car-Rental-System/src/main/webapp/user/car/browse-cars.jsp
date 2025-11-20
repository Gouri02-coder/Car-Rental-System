<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Browse Cars | DriveEasy Rentals</title>
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
            background-color: #f8f9fa;
        }
        
        .search-section {
            background: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)), url('https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80');
            background-size: cover;
            background-position: center;
            color: white;
            padding: 80px 0;
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
        
        .results-info {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }
        
        .no-results {
            text-align: center;
            padding: 4rem 0;
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .no-results i {
            font-size: 4rem;
            color: var(--gray);
            margin-bottom: 1rem;
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
    </style>
</head>
<body>
    <%
        String searchQuery = request.getParameter("query");
        if (searchQuery == null) searchQuery = "";
        
        User user = (User) session.getAttribute("user");
        
        // All 6 cars from your browse-cars.jsp
        String[][] cars = {
            {"1", "Buggati Chiron", "HyperCar", "10 lakhs", "2 Seats | Automatic", "Perfect for hyper driving and fuel efficiency.", "https://images.unsplash.com/photo-1544636331-e26879cd4d9b?ixlib=rb-4.0.3&auto=format&fit=crop&w=1074&q=80"},
            {"2", "Honda CR-V", "SUV", "2000", "5 Seats | Automatic", "Spacious SUV perfect for family trips.", "https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?ixlib=rb-4.0.3&auto=format&fit=crop&w=1170&q=80"},
            {"3", "Porsche Taycan", "Luxury", "3 lakhs", "5 Seats | Automatic", "Premium luxury sedan for business or pleasure.", "https://images.unsplash.com/photo-1503376780353-7e6692767b70?ixlib=rb-4.0.3&auto=format&fit=crop&w=1170&q=80"},
            {"4", "Ferrari SF 90", "SuperCar", "1.5 lakhs", "2 Seats | Automatic", "Perfect for hyper driving and fuel efficiency.", "https://images.unsplash.com/photo-1583121274602-3e2820c69888?ixlib=rb-4.0.3&auto=format&fit=crop&w=1074&q=80"},
            {"5", "BMW 5 Series", "Luxury", "50k", "5 Seats | Automatic", "Luxury sedan with premium features.", "https://images.unsplash.com/photo-1555215695-3004980ad54e?ixlib=rb-4.0.3&auto=format&fit=crop&w=1074&q=80"},
            {"6", "Ford Mustang", "Sports", "45k", "4 Seats | Manual", "Classic sports car for enthusiasts.", "https://images.unsplash.com/photo-1584345604376-a69e7d06163f?ixlib=rb-4.0.3&auto=format&fit=crop&w=1074&q=80"}
        };
        
        // Filter cars based on search query
        java.util.List<String[]> filteredCars = new java.util.ArrayList<>();
        String query = searchQuery.toLowerCase().trim();
        
        for (String[] car : cars) {
            if (query.isEmpty() || 
                car[1].toLowerCase().contains(query) ||
                car[2].toLowerCase().contains(query) ||
                car[4].toLowerCase().contains(query) ||
                car[5].toLowerCase().contains(query)) {
                filteredCars.add(car);
            }
        }
    %>
    
    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
        <div class="container">
            <a class="navbar-brand" href="../../index.jsp">
                <i class="fas fa-car me-2"></i><strong>Drive<span>Easy</span> Rentals</strong>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="../../index.jsp">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="browse-cars.jsp">Browse Cars</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../../index.jsp#features">Features</a>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto">
                    <%
                        if (user != null) {
                    %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                            <i class="fas fa-user me-1"></i><%= user.getName() %>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="../dashboard.jsp">
                                <i class="fas fa-tachometer-alt me-2"></i>Dashboard
                            </a></li>
                            <li><a class="dropdown-item" href="../profile.jsp">
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
                        <a class="nav-link" href="../../auth/login/LoginOption.jsp">
                            <i class="fas fa-sign-in-alt me-1"></i>Login
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link btn btn-primary text-white ms-2" href="../../auth/register/UserRegistration.jsp">
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

    <!-- Search Section -->
    <div class="search-section">
        <div class="container">
            <h1 class="display-5 fw-bold mb-4">Find Your Perfect Ride</h1>
            
            <!-- Search Bar -->
            <div class="search-container">
                <form action="browse-cars.jsp" method="get" class="d-flex search-box">
                    <input type="text" 
                           name="query" 
                           class="search-input" 
                           placeholder="Search by car type, brand, or features..."
                           value="<%= searchQuery %>"
                           required>
                    <button type="submit" class="search-btn ms-2">
                        <i class="fas fa-search me-2"></i>Search Cars
                    </button>
                </form>
            </div>
        </div>
    </div>

    <!-- Results Section -->
    <div class="container mt-5 py-5">
        <!-- Results Info -->
        <div class="results-info">
            <div class="row align-items-center">
                <div class="col-md-6">
                    <h4 class="mb-0">
                        <% if (searchQuery.isEmpty()) { %>
                            All Available Cars
                        <% } else { %>
                            Search Results
                        <% } %>
                    </h4>
                    <p class="text-muted mb-0">
                        Found <strong><%= filteredCars.size() %></strong> cars
                        <% if (!searchQuery.isEmpty()) { %>
                            for "<%= searchQuery %>"
                        <% } %>
                    </p>
                </div>
                <div class="col-md-6 text-md-end">
                    <a href="browse-cars.jsp" class="btn btn-outline-secondary">
                        <i class="fas fa-refresh me-2"></i>Clear Search
                    </a>
                </div>
            </div>
        </div>

        <% if (filteredCars.isEmpty()) { %>
            <!-- No Results -->
            <div class="no-results">
                <i class="fas fa-search"></i>
                <h3>No cars found</h3>
                <p class="text-muted">We couldn't find any cars matching "<%= searchQuery %>"</p>
                <a href="browse-cars.jsp" class="btn btn-primary btn-lg mt-3">
                    <i class="fas fa-car me-2"></i>Browse All Cars
                </a>
            </div>
        <% } else { %>
            <!-- Car Grid - Now showing all 6 cars -->
            <div class="row g-4">
                <% for (String[] car : filteredCars) { %>
                <div class="col-md-4">
                    <div class="car-card">
                        <div class="car-img" style="background-image: url('<%= car[6] %>');"></div>
                        <div class="car-content">
                            <h4><%= car[1] %></h4>
                            <p class="text-muted"><%= car[2] %> | <%= car[4] %></p>
                            <div class="car-price"><%= car[3] %><span>/day</span></div>
                            <p><%= car[5] %></p>
                            <%
                                if (user != null) {
                            %>
                            <a href="../booking/BookCar.jsp?carId=<%= car[0] %>" class="rent-btn">Rent This Car</a>
                            <%
                                } else {
                            %>
                            <a href="../../auth/login/UserLogin.jsp?redirect=booking&carId=<%= car[0] %>" class="rent-btn">Rent This Car</a>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
        <% } %>
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