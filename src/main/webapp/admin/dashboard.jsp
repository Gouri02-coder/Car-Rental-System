<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Admin" %>
<%@ page import="services.DashboardService" %>
<%@ page import="model.Car" %>
<%@ page import="model.Booking" %>
<%@ page import="java.util.*" %>
<%
    // Check admin authentication
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login/AdminLogin.jsp");
        return;
    }
    
    DashboardService dashboardService = new DashboardService();
    Map<String, Object> dashboardData = dashboardService.getDashboardData();
    
    int totalCars = (Integer) dashboardData.get("totalCars");
    int availableCars = (Integer) dashboardData.get("availableCars");
    int rentedCars = (Integer) dashboardData.get("rentedCars");
    int totalBookings = (Integer) dashboardData.get("totalBookings");
    int activeBookings = (Integer) dashboardData.get("activeBookings");
    int pendingBookings = (Integer) dashboardData.get("pendingBookings");
    double totalRevenue = (Double) dashboardData.get("totalRevenue");
    int totalCustomers = (Integer) dashboardData.get("totalCustomers");
    
    List<Car> recentCars = (List<Car>) dashboardData.get("recentCars");
    List<Booking> recentBookings = (List<Booking>) dashboardData.get("recentBookings");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Car Rental System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        /* Add the CSS styles from previous response */
        :root {
            --primary: #dc3545;
            --secondary: #6c757d;
            --success: #198754;
            --info: #0dcaf0;
            --warning: #ffc107;
            --danger: #dc3545;
            --light: #f8f9fa;
            --dark: #212529;
        }
        
        body {
            background-color: #f5f7fb;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        .sidebar {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            height: 100vh;
            position: fixed;
            box-shadow: 3px 0 10px rgba(0,0,0,0.1);
        }
        
        .sidebar-brand {
            padding: 1.5rem 1rem;
            border-bottom: 1px solid rgba(255,255,255,0.1);
        }
        
        .sidebar-nav {
            padding: 1rem 0;
        }
        
        .nav-link {
            color: rgba(255,255,255,0.8);
            padding: 0.8rem 1.5rem;
            margin: 0.2rem 0.5rem;
            border-radius: 0.5rem;
            transition: all 0.3s;
        }
        
        .nav-link:hover, .nav-link.active {
            color: white;
            background: rgba(255,255,255,0.1);
            transform: translateX(5px);
        }
        
        .main-content {
            margin-left: 260px;
            padding: 20px;
        }
        
        .stat-card {
            background: white;
            border-radius: 15px;
            padding: 1.5rem;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
            border: none;
            transition: transform 0.3s;
            height: 100%;
        }
        
        .stat-card:hover {
            transform: translateY(-5px);
        }
        
        .stat-icon {
            width: 60px;
            height: 60px;
            border-radius: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
            margin-bottom: 1rem;
        }
        
        .bg-gradient-primary { background: linear-gradient(45deg, #667eea, #764ba2); }
        .bg-gradient-success { background: linear-gradient(45deg, #4facfe, #00f2fe); }
        .bg-gradient-warning { background: linear-gradient(45deg, #f093fb, #f5576c); }
        .bg-gradient-info { background: linear-gradient(45deg, #43e97b, #38f9d7); }
        
        .status-badge {
            padding: 0.25rem 0.75rem;
            border-radius: 20px;
            font-size: 0.75rem;
            font-weight: 600;
        }
        
        .status-available { background: #d4edda; color: #155724; }
        .status-rented { background: #fff3cd; color: #856404; }
        .status-pending { background: #cce7ff; color: #004085; }
        .status-confirmed { background: #d4edda; color: #155724; }
        .status-active { background: #fff3cd; color: #856404; }
    </style>
</head>
<body>
    <!-- Sidebar -->
    <div class="sidebar" style="width: 260px;">
        <div class="sidebar-brand">
            <h4 class="mb-0">
                <i class="fas fa-car-side me-2"></i>
                <span>CarRental Pro</span>
            </h4>
            <small class="text-white-50">Admin Panel</small>
        </div>
        
        <div class="sidebar-nav">
            <a href="dashboard.jsp" class="nav-link active">
                <i class="fas fa-tachometer-alt"></i>
                <span>Dashboard</span>
            </a>
            <a href="cars/manage-cars.jsp" class="nav-link">
                <i class="fas fa-car"></i>
                <span>Manage Cars</span>
            </a>
            <a href="bookings/manage-bookings.jsp" class="nav-link">
                <i class="fas fa-calendar-check"></i>
                <span>Manage Bookings</span>
            </a>
            <a href="users/manage-users.jsp" class="nav-link">
                <i class="fas fa-users"></i>
                <span>Manage Users</span>
            </a>
        </div>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <!-- Navbar -->
        <nav class="navbar navbar-light bg-white rounded-3 mb-4">
            <div class="container-fluid">
                <h5 class="mb-0">Dashboard Overview</h5>
                <div class="d-flex align-items-center">
                    <span class="me-3">Welcome, <%= admin.getFullName() %></span>
                    <a href="${pageContext.request.contextPath}/auth/login/AdminLogin.jsp" class="btn btn-outline-danger btn-sm">Logout</a>
                </div>
            </div>
        </nav>

        <!-- Statistics Cards -->
        <div class="row g-4 mb-4">
            <div class="col-xl-3 col-md-6">
                <div class="stat-card">
                    <div class="stat-icon bg-gradient-primary text-white">
                        <i class="fas fa-car"></i>
                    </div>
                    <h3 class="text-primary"><%= totalCars %></h3>
                    <h6>Total Cars</h6>
                    <small class="text-success"><%= availableCars %> available</small>
                </div>
            </div>
            <div class="col-xl-3 col-md-6">
                <div class="stat-card">
                    <div class="stat-icon bg-gradient-success text-white">
                        <i class="fas fa-calendar-check"></i>
                    </div>
                    <h3 class="text-success"><%= totalBookings %></h3>
                    <h6>Total Bookings</h6>
                    <small class="text-warning"><%= activeBookings %> active</small>
                </div>
            </div>
            <div class="col-xl-3 col-md-6">
                <div class="stat-card">
                    <div class="stat-icon bg-gradient-warning text-white">
                        <i class="fas fa-users"></i>
                    </div>
                    <h3 class="text-warning"><%= totalCustomers %></h3>
                    <h6>Total Customers</h6>
                    <small class="text-info">Registered users</small>
                </div>
            </div>
            <div class="col-xl-3 col-md-6">
                <div class="stat-card">
                    <div class="stat-icon bg-gradient-info text-white">
                        <i class="fas fa-dollar-sign"></i>
                    </div>
                    <h3 class="text-info">$<%= String.format("%.2f", totalRevenue) %></h3>
                    <h6>Total Revenue</h6>
                    <small class="text-success">All time</small>
                </div>
            </div>
        </div>

        <!-- Recent Activity -->
        <div class="row g-4">
            <!-- Recent Bookings -->
            <div class="col-lg-8">
                <div class="stat-card">
                    <h5 class="mb-4">Recent Bookings</h5>
                    <% if (recentBookings.isEmpty()) { %>
                        <p class="text-muted">No recent bookings</p>
                    <% } else { %>
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Customer</th>
                                        <th>Dates</th>
                                        <th>Amount</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (Booking booking : recentBookings) { %>
                                    <tr>
                                        <td>#<%= booking.getId() %></td>
                                        <td><%= booking.getUserName() != null ? booking.getUserName() : "User#" + booking.getUserId() %></td>
                                        <td><%= booking.getStartDate() %> to <%= booking.getEndDate() %></td>
                                        <td>$<%= String.format("%.2f", booking.getTotalPrice()) %></td>
                                        <td>
                                            <span class="status-badge status-<%= booking.getStatus() %>">
                                                <%= booking.getStatus().toUpperCase() %>
                                            </span>
                                        </td>
                                    </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                    <% } %>
                </div>
            </div>

            <!-- Recent Cars -->
            <div class="col-lg-4">
                <div class="stat-card">
                    <h5 class="mb-4">Recent Cars</h5>
                    <% if (recentCars.isEmpty()) { %>
                        <p class="text-muted">No cars available</p>
                    <% } else { %>
                        <% for (Car car : recentCars) { %>
                        <div class="card mb-3">
                            <div class="card-body">
                                <h6><%= car.getBrand() %> <%= car.getModel() %></h6>
                                <p class="mb-1 text-muted small">$<%= car.getPricePerDay() %>/day</p>
                                <span class="status-badge <%= car.isAvailable() ? "status-available" : "status-rented" %>">
                                    <%= car.isAvailable() ? "AVAILABLE" : "RENTED" %>
                                </span>
                            </div>
                        </div>
                        <% } %>
                    <% } %>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>