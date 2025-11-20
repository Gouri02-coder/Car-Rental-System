<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%
    // Check if user is logged in
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login/UserLogin.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Car Rental System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        :root {
            --primary: #0d6efd;
            --secondary: #6c757d;
            --success: #198754;
            --info: #0dcaf0;
            --warning: #ffc107;
            --danger: #dc3545;
            --light: #f8f9fa;
            --dark: #212529;
        }
        
        .sidebar {
            background: linear-gradient(135deg, var(--primary), #0a58ca);
            color: white;
            min-height: 100vh;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }
        
        .sidebar .nav-link {
            color: rgba(255,255,255,0.8);
            padding: 12px 20px;
            margin: 5px 0;
            border-radius: 8px;
            transition: all 0.3s;
        }
        
        .sidebar .nav-link:hover, .sidebar .nav-link.active {
            color: white;
            background: rgba(255,255,255,0.1);
            transform: translateX(5px);
        }
        
        .sidebar .nav-link i {
            width: 20px;
            margin-right: 10px;
        }
        
        .main-content {
            background-color: #f8f9fa;
            min-height: 100vh;
        }
        
        .stat-card {
            border: none;
            border-radius: 15px;
            transition: transform 0.3s, box-shadow 0.3s;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        
        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.15);
        }
        
        .stat-card.primary { background: linear-gradient(135deg, var(--primary), #0a58ca); color: white; }
        .stat-card.success { background: linear-gradient(135deg, var(--success), #146c43); color: white; }
        .stat-card.warning { background: linear-gradient(135deg, var(--warning), #ffcd39); color: black; }
        .stat-card.info { background: linear-gradient(135deg, var(--info), #31d2f2); color: white; }
        
        .welcome-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px;
            padding: 30px;
            margin-bottom: 30px;
        }
        
        .user-avatar {
            width: 80px;
            height: 80px;
            background: rgba(255,255,255,0.2);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2rem;
            margin-right: 20px;
        }
        
        .recent-activity {
            background: white;
            border-radius: 15px;
            padding: 25px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        
        .activity-item {
            padding: 15px 0;
            border-bottom: 1px solid #eee;
            display: flex;
            align-items: center;
        }
        
        .activity-item:last-child {
            border-bottom: none;
        }
        
        .activity-icon {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: var(--light);
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 15px;
            color: var(--primary);
        }
        
        .navbar-custom {
            background: white;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            padding: 15px 0;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3 col-lg-2 sidebar">
                <div class="d-flex flex-column p-3">
                    <div class="text-center mb-4">
                        <h4 class="fw-bold">CarRental</h4>
                        <small class="text-white-50">Drive Your Dreams</small>
                    </div>
                    
                    <ul class="nav nav-pills flex-column mb-auto">
                        <li class="nav-item">
                            <a href="dashboard.jsp" class="nav-link active">
                                <i class="fas fa-home"></i>Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="profile.jsp" class="nav-link">
                                <i class="fas fa-user"></i>My Profile
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="booking-history.jsp" class="nav-link">
                                <i class="fas fa-history"></i>Booking History
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="car-list.jsp" class="nav-link">
                                <i class="fas fa-car"></i>Rent a Car
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="#" class="nav-link">
                                <i class="fas fa-credit-card"></i>Payments
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="#" class="nav-link">
                                <i class="fas fa-cog"></i>Settings
                            </a>
                        </li>
                    </ul>
                    
                    <div class="mt-auto">
                        <div class="dropdown">
                            <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" data-bs-toggle="dropdown">
                                <div class="user-avatar">
                                    <i class="fas fa-user"></i>
                                </div>
                                <div>
                                    <strong><%= user.getName() %></strong>
                                    <small class="text-white-50 d-block"><%= user.getEmail() %></small>
                                </div>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
                                <li><a class="dropdown-item" href="profile.jsp">Profile</a></li>
                                <li><a class="dropdown-item" href="#">Settings</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/UserCtrl?action=logout">Sign out</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Main Content -->
            <div class="col-md-9 col-lg-10 main-content">
                <!-- Top Navigation -->
                <nav class="navbar navbar-custom">
                    <div class="container-fluid">
                        <div class="navbar-nav ms-auto">
                            <div class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                                    <i class="fas fa-bell"></i>
                                    <span class="badge bg-danger">3</span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="#">New booking confirmed</a></li>
                                    <li><a class="dropdown-item" href="#">Payment received</a></li>
                                    <li><a class="dropdown-item" href="#">Car return reminder</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </nav>
                
                <div class="container-fluid mt-4">
                    <!-- Welcome Section -->
                    <div class="welcome-section">
                        <div class="row align-items-center">
                            <div class="col-md-8">
                                <h2 class="fw-bold">Welcome back, <%= user.getName() %>! 👋</h2>
                                <p class="mb-0">Ready to find your next adventure? Explore our latest car collection.</p>
                            </div>
                            <div class="col-md-4 text-end">
                                <button class="btn btn-light btn-lg">
                                    <i class="fas fa-car me-2"></i>Book a Car Now
                                </button>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Statistics Cards -->
                    <div class="row mb-4">
                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="stat-card primary text-white p-4">
                                <div class="d-flex justify-content-between">
                                    <div>
                                        <h5>Total Bookings</h5>
                                        <h2 class="fw-bold">12</h2>
                                    </div>
                                    <div class="align-self-center">
                                        <i class="fas fa-calendar-check fa-2x"></i>
                                    </div>
                                </div>
                                <small>+2 from last month</small>
                            </div>
                        </div>
                        
                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="stat-card success text-white p-4">
                                <div class="d-flex justify-content-between">
                                    <div>
                                        <h5>Active Rentals</h5>
                                        <h2 class="fw-bold">2</h2>
                                    </div>
                                    <div class="align-self-center">
                                        <i class="fas fa-car fa-2x"></i>
                                    </div>
                                </div>
                                <small>Currently rented cars</small>
                            </div>
                        </div>
                        
                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="stat-card warning p-4">
                                <div class="d-flex justify-content-between">
                                    <div>
                                        <h5>Loyalty Points</h5>
                                        <h2 class="fw-bold">450</h2>
                                    </div>
                                    <div class="align-self-center">
                                        <i class="fas fa-star fa-2x"></i>
                                    </div>
                                </div>
                                <small>Earn 50 more for gold status</small>
                            </div>
                        </div>
                        
                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="stat-card info text-white p-4">
                                <div class="d-flex justify-content-between">
                                    <div>
                                        <h5>Total Spent</h5>
                                        <h2 class="fw-bold">$1,240</h2>
                                    </div>
                                    <div class="align-self-center">
                                        <i class="fas fa-dollar-sign fa-2x"></i>
                                    </div>
                                </div>
                                <small>All-time rental expenses</small>
                            </div>
                        </div>
                    </div>
                    
                    <div class="row">
                        <!-- Recent Activity -->
                        <div class="col-lg-8 mb-4">
                            <div class="recent-activity">
                                <h5 class="fw-bold mb-4">Recent Activity</h5>
                                
                                <div class="activity-item">
                                    <div class="activity-icon">
                                        <i class="fas fa-car"></i>
                                    </div>
                                    <div class="flex-grow-1">
                                        <h6 class="mb-1">Car Booking Confirmed</h6>
                                        <p class="mb-0 text-muted">Toyota Camry - Pickup: Tomorrow 10:00 AM</p>
                                        <small class="text-muted">2 hours ago</small>
                                    </div>
                                    <span class="badge bg-success">Confirmed</span>
                                </div>
                                
                                <div class="activity-item">
                                    <div class="activity-icon">
                                        <i class="fas fa-credit-card"></i>
                                    </div>
                                    <div class="flex-grow-1">
                                        <h6 class="mb-1">Payment Processed</h6>
                                        <p class="mb-0 text-muted">Payment of $120 for Honda Civic rental</p>
                                        <small class="text-muted">1 day ago</small>
                                    </div>
                                    <span class="badge bg-success">Completed</span>
                                </div>
                                
                                <div class="activity-item">
                                    <div class="activity-icon">
                                        <i class="fas fa-star"></i>
                                    </div>
                                    <div class="flex-grow-1">
                                        <h6 class="mb-1">Loyalty Points Added</h6>
                                        <p class="mb-0 text-muted">Earned 50 points for your recent booking</p>
                                        <small class="text-muted">2 days ago</small>
                                    </div>
                                    <span class="badge bg-info">+50 Points</span>
                                </div>
                                
                                <div class="activity-item">
                                    <div class="activity-icon">
                                        <i class="fas fa-car"></i>
                                    </div>
                                    <div class="flex-grow-1">
                                        <h6 class="mb-1">Car Returned</h6>
                                        <p class="mb-0 text-muted">Successfully returned BMW X3</p>
                                        <small class="text-muted">3 days ago</small>
                                    </div>
                                    <span class="badge bg-secondary">Completed</span>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Quick Actions -->
                        <div class="col-lg-4">
                            <div class="recent-activity">
                                <h5 class="fw-bold mb-4">Quick Actions</h5>
                                
                                <div class="d-grid gap-3">
                                    <button class="btn btn-primary btn-lg">
                                        <i class="fas fa-car me-2"></i>Book New Car
                                    </button>
                                    <button class="btn btn-outline-primary btn-lg">
                                        <i class="fas fa-history me-2"></i>View Booking History
                                    </button>
                                    <button class="btn btn-outline-primary btn-lg">
                                        <i class="fas fa-user me-2"></i>Update Profile
                                    </button>
                                    <button class="btn btn-outline-primary btn-lg">
                                        <i class="fas fa-credit-card me-2"></i>Payment Methods
                                    </button>
                                </div>
                                
                                <hr class="my-4">
                                
                                <h6 class="fw-bold">Upcoming Bookings</h6>
                                <div class="mt-3">
                                    <div class="card border-0 bg-light">
                                        <div class="card-body">
                                            <h6>Toyota Camry</h6>
                                            <small class="text-muted">Pickup: Tomorrow, 10:00 AM</small><br>
                                            <small class="text-muted">Return: 3 days later</small>
                                            <div class="mt-2">
                                                <span class="badge bg-warning">Upcoming</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Simple dashboard interactions
        document.addEventListener('DOMContentLoaded', function() {
            // Update current time
            function updateTime() {
                const now = new Date();
                document.getElementById('currentTime').textContent = now.toLocaleString();
            }
            setInterval(updateTime, 1000);
            updateTime();
        });
    </script>
</body>
</html>