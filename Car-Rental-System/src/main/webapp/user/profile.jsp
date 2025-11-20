<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page import="dao.BookingDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Booking" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login/UserLogin.jsp");
        return;
    }
    
    // Get user's booking statistics
    BookingDAO bookingDAO = new BookingDAO();
    int totalBookings = bookingDAO.getUserTotalBookings(user.getId());
    int activeBookings = bookingDAO.getUserActiveBookings(user.getId());
    double totalSpent = bookingDAO.getUserTotalSpent(user.getId());
    List<Booking> recentBookings = bookingDAO.getUserRecentBookings(user.getId(), 5);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Profile - DriveEasy Rentals</title>
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
            background-color: #f8f9fa;
        }
        
        .profile-header {
            background: linear-gradient(rgba(44, 62, 80, 0.9), rgba(44, 62, 80, 0.9)), url('https://images.unsplash.com/photo-1549317661-bd32c8ce0db2');
            background-size: cover;
            background-position: center;
            color: white;
            padding: 80px 0 40px;
        }
        
        .profile-card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            transition: transform 0.3s ease;
            margin-bottom: 20px;
        }
        
        .profile-card:hover {
            transform: translateY(-5px);
        }
        
        .profile-avatar {
            width: 120px;
            height: 120px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 3rem;
            color: white;
            margin: -60px auto 20px;
            border: 5px solid white;
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
        }
        
        .stats-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 15px;
        }
        
        .booking-card {
            border-left: 4px solid var(--secondary);
        }
        
        .payment-card {
            border-left: 4px solid #27ae60;
        }
        
        .nav-pills .nav-link.active {
            background-color: var(--secondary);
            border: none;
        }
        
        .nav-pills .nav-link {
            color: var(--dark);
            font-weight: 500;
        }
        
        .btn-edit {
            background: var(--secondary);
            color: white;
            border: none;
        }
        
        .btn-edit:hover {
            background: #2980b9;
            color: white;
        }
        
        .booking-status {
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 0.8rem;
            font-weight: 600;
        }
        
        .status-confirmed {
            background-color: #d4edda;
            color: #155724;
        }
        
        .status-active {
            background-color: #cce7ff;
            color: #004085;
        }
        
        .status-completed {
            background-color: #e2e3e5;
            color: #383d41;
        }
        
        .status-pending {
            background-color: #fff3cd;
            color: #856404;
        }
        
        .status-cancelled {
            background-color: #f8d7da;
            color: #721c24;
        }
        
        .license-badge {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 8px 16px;
            border-radius: 25px;
            font-weight: bold;
            letter-spacing: 1px;
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
        <div class="container">
            <a class="navbar-brand" href="../index.jsp">
                <i class="fas fa-car me-2"></i><strong>Drive<span>Easy</span> Rentals</strong>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="../index.jsp">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../index.jsp#inventory">Browse Cars</a>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                            <i class="fas fa-user me-1"></i><%= user.getName() %>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="dashboard.jsp">
                                <i class="fas fa-tachometer-alt me-2"></i>Dashboard
                            </a></li>
                            <li><a class="dropdown-item" href="profile.jsp">
                                <i class="fas fa-user me-2"></i>Profile
                            </a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/UserCtrl?action=logout">
                                    <i class="fas fa-sign-out-alt me-2"></i>Logout
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Profile Header -->
    <div class="profile-header">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-8 text-center text-md-start">
                    <h1 class="display-5 fw-bold">My Profile</h1>
                    <p class="lead mb-0">Manage your personal information and rental history</p>
                </div>
                <div class="col-md-4 text-center text-md-end mt-3 mt-md-0">
                    <a href="dashboard.jsp" class="btn btn-light me-2">
                        <i class="fas fa-tachometer-alt me-1"></i>Dashboard
                    </a>
                    <a href="../index.jsp" class="btn btn-outline-light">
                        <i class="fas fa-home me-1"></i>Back to Home
                    </a>
                </div>
            </div>
        </div>
    </div>

    <!-- Profile Content -->
    <div class="container mt-5">
        <div class="row">
            <!-- Sidebar Navigation -->
            <div class="col-md-3 mb-4">
                <div class="card profile-card">
                    <div class="card-body text-center">
                        <div class="profile-avatar">
                            <i class="fas fa-user"></i>
                        </div>
                        <h4 class="mb-2"><%= user.getName() %></h4>
                        <p class="text-muted mb-3"><%= user.getEmail() %></p>
                        <span class="badge bg-primary"><%= user.getRole().toUpperCase() %></span>
                        
                        <div class="mt-4">
                            <ul class="nav nav-pills flex-column">
                                <li class="nav-item">
                                    <a class="nav-link active" href="#overview" data-bs-toggle="pill">
                                        <i class="fas fa-chart-bar me-2"></i>Overview
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#personal" data-bs-toggle="pill">
                                        <i class="fas fa-user-circle me-2"></i>Personal Info
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#license" data-bs-toggle="pill">
                                        <i class="fas fa-id-card me-2"></i>License Details
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#bookings" data-bs-toggle="pill">
                                        <i class="fas fa-car me-2"></i>My Bookings
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#security" data-bs-toggle="pill">
                                        <i class="fas fa-shield-alt me-2"></i>Security
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                
                <!-- Quick Stats -->
                <div class="card stats-card mt-4">
                    <div class="card-body text-center">
                        <h6 class="card-title">Account Overview</h6>
                        <div class="row mt-3">
                            <div class="col-6">
                                <h4><%= totalBookings %></h4>
                                <small>Total Rentals</small>
                            </div>
                            <div class="col-6">
                                <h4><%= activeBookings %></h4>
                                <small>Active</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Main Content -->
            <div class="col-md-9">
                <div class="tab-content">
                    <!-- Overview Tab -->
                    <div class="tab-pane fade show active" id="overview">
                        <!-- Statistics Cards -->
                        <div class="row">
                            <div class="col-md-4">
                                <div class="card stats-card">
                                    <div class="card-body text-center">
                                        <i class="fas fa-car fa-2x mb-3"></i>
                                        <h3><%= totalBookings %></h3>
                                        <p class="mb-0">Total Rentals</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="card stats-card">
                                    <div class="card-body text-center">
                                        <i class="fas fa-clock fa-2x mb-3"></i>
                                        <h3><%= activeBookings %></h3>
                                        <p class="mb-0">Active Rentals</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="card stats-card">
                                    <div class="card-body text-center">
                                        <i class="fas fa-dollar-sign fa-2x mb-3"></i>
                                        <h3>$<%= String.format("%.2f", totalSpent) %></h3>
                                        <p class="mb-0">Total Spent</p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Recent Activity -->
                        <div class="card profile-card mt-4">
                            <div class="card-header bg-white">
                                <h5 class="card-title mb-0">
                                    <i class="fas fa-history me-2 text-primary"></i>Recent Activity
                                </h5>
                            </div>
                            <div class="card-body">
                                <% if (recentBookings.isEmpty()) { %>
                                    <div class="text-center py-4">
                                        <i class="fas fa-car fa-3x text-muted mb-3"></i>
                                        <h5>No bookings yet</h5>
                                        <p class="text-muted">Start your first rental adventure!</p>
                                        <a href="../index.jsp#inventory" class="btn btn-primary">Browse Cars</a>
                                    </div>
                                <% } else { %>
                                    <div class="table-responsive">
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th>Car</th>
                                                    <th>Booking Date</th>
                                                    <th>Duration</th>
                                                    <th>Amount</th>
                                                    <th>Status</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <% for (Booking booking : recentBookings) { %>
                                                <tr>
                                                    <td>
                                                        <strong>
                                                            <% if (booking.getCarBrand() != null && booking.getCarModel() != null) { %>
                                                                <%= booking.getCarBrand() %> <%= booking.getCarModel() %>
                                                            <% } else { %>
                                                                Car #<%= booking.getCarId() %>
                                                            <% } %>
                                                        </strong>
                                                    </td>
                                                    <td><%= booking.getBookingDate() %></td>
                                                    <td><%= booking.getRentalDays() %> days</td>
                                                    <td>$<%= String.format("%.2f", booking.getTotalAmount()) %></td>
                                                    <td>
                                                        <span class="booking-status status-<%= booking.getStatus().toLowerCase() %>">
                                                            <%= booking.getStatus() %>
                                                        </span>
                                                    </td>
                                                </tr>
                                                <% } %>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="text-end">
                                        <a href="#bookings" class="btn btn-outline-primary btn-sm" data-bs-toggle="pill">View All Bookings</a>
                                    </div>
                                <% } %>
                            </div>
                        </div>
                    </div>

                    <!-- Personal Information Tab -->
                    <div class="tab-pane fade" id="personal">
                        <div class="card profile-card">
                            <div class="card-header bg-white d-flex justify-content-between align-items-center">
                                <h5 class="card-title mb-0">
                                    <i class="fas fa-user-circle me-2 text-primary"></i>Personal Information
                                </h5>
                                <button class="btn btn-edit btn-sm" data-bs-toggle="modal" data-bs-target="#editProfileModal">
                                    <i class="fas fa-edit me-1"></i>Edit Profile
                                </button>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label text-muted">Full Name</label>
                                        <p class="fs-5"><%= user.getName() %></p>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label text-muted">Email Address</label>
                                        <p class="fs-5"><%= user.getEmail() %></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label text-muted">Phone Number</label>
                                        <p class="fs-5"><%= user.getPhone() %></p>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label text-muted">Member Since</label>
                                        <p class="fs-5">January 2024</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-12 mb-3">
                                        <label class="form-label text-muted">Address</label>
                                        <p class="fs-5"><%= user.getAddress() %></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- License Details Tab -->
                    <div class="tab-pane fade" id="license">
                        <div class="card profile-card">
                            <div class="card-header bg-white">
                                <h5 class="card-title mb-0">
                                    <i class="fas fa-id-card me-2 text-danger"></i>Driving License Information
                                </h5>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-8">
                                        <label class="form-label text-muted">License Number</label>
                                        <p class="license-badge d-inline-block"><%= user.getLicenseNumber() %></p>
                                    </div>
                                    <div class="col-md-4 text-center">
                                        <div class="bg-light rounded p-3">
                                            <i class="fas fa-check-circle fa-2x text-success"></i>
                                            <p class="mb-0 mt-2">Verified</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mt-4">
                                    <div class="col-md-6">
                                        <label class="form-label text-muted">License Type</label>
                                        <p class="fs-5">Class D - Personal Vehicle</p>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label text-muted">Expiry Date</label>
                                        <p class="fs-5">December 2028</p>
                                    </div>
                                </div>
                                <div class="alert alert-info mt-3">
                                    <i class="fas fa-info-circle me-2"></i>
                                    Your license information is verified and up to date. You're eligible to rent any vehicle in our fleet.
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Bookings Tab -->
                    <div class="tab-pane fade" id="bookings">
                        <div class="card profile-card">
                            <div class="card-header bg-white d-flex justify-content-between align-items-center">
                                <h5 class="card-title mb-0">
                                    <i class="fas fa-car me-2 text-success"></i>My Bookings
                                </h5>
                                <span class="badge bg-primary"><%= totalBookings %> Total</span>
                            </div>
                            <div class="card-body">
                                <% if (recentBookings.isEmpty()) { %>
                                    <div class="text-center py-5">
                                        <i class="fas fa-car fa-4x text-muted mb-3"></i>
                                        <h4>No bookings found</h4>
                                        <p class="text-muted mb-4">You haven't made any bookings yet.</p>
                                        <a href="../index.jsp#inventory" class="btn btn-primary btn-lg">
                                            <i class="fas fa-car me-2"></i>Rent Your First Car
                                        </a>
                                    </div>
                                <% } else { %>
                                    <div class="table-responsive">
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th>Booking ID</th>
                                                    <th>Car</th>
                                                    <th>Booking Date</th>
                                                    <th>Pickup Date</th>
                                                    <th>Return Date</th>
                                                    <th>Amount</th>
                                                    <th>Status</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <% for (Booking booking : recentBookings) { %>
                                                <tr>
                                                    <td>#<%= booking.getId() %></td>
                                                    <td>
                                                        <strong>
                                                            <% if (booking.getCarBrand() != null && booking.getCarModel() != null) { %>
                                                                <%= booking.getCarBrand() %> <%= booking.getCarModel() %>
                                                            <% } else { %>
                                                                Car #<%= booking.getCarId() %>
                                                            <% } %>
                                                        </strong>
                                                    </td>
                                                    <td><%= booking.getBookingDate() %></td>
                                                    <td><%= booking.getPickupDate() %></td>
                                                    <td><%= booking.getReturnDate() %></td>
                                                    <td><strong>$<%= String.format("%.2f", booking.getTotalAmount()) %></strong></td>
                                                    <td>
                                                        <span class="booking-status status-<%= booking.getStatus().toLowerCase() %>">
                                                            <%= booking.getStatus() %>
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
                    </div>

                    <!-- Security Tab -->
                    <div class="tab-pane fade" id="security">
                        <div class="card profile-card">
                            <div class="card-header bg-white">
                                <h5 class="card-title mb-0">
                                    <i class="fas fa-shield-alt me-2 text-warning"></i>Security Settings
                                </h5>
                            </div>
                            <div class="card-body">
                                <div class="row mb-4">
                                    <div class="col-md-8">
                                        <h6>Change Password</h6>
                                        <p class="text-muted">Update your password to keep your account secure</p>
                                    </div>
                                    <div class="col-md-4 text-end">
                                        <button class="btn btn-edit" data-bs-toggle="modal" data-bs-target="#changePasswordModal">
                                            Change Password
                                        </button>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-md-8">
                                        <h6>Account Verification</h6>
                                        <p class="text-muted">Your driving license is verified and active</p>
                                    </div>
                                    <div class="col-md-4 text-end">
                                        <span class="badge bg-success">Verified</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="bg-dark text-white py-4 mt-5">
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

    <!-- Edit Profile Modal -->
    <div class="modal fade" id="editProfileModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Edit Profile Information</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form id="editProfileForm" action="${pageContext.request.contextPath}/UserCtrl" method="POST">
                        <input type="hidden" name="action" value="updateProfile">
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Full Name</label>
                                <input type="text" class="form-control" name="fullName" value="<%= user.getName() %>" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Phone Number</label>
                                <input type="tel" class="form-control" name="phone" value="<%= user.getPhone() %>" required>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Address</label>
                            <textarea class="form-control" name="address" rows="3" required><%= user.getAddress() %></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" form="editProfileForm" class="btn btn-primary">Save Changes</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Change Password Modal -->
    <div class="modal fade" id="changePasswordModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Change Password</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form id="changePasswordForm" action="${pageContext.request.contextPath}/UserCtrl" method="POST">
                        <input type="hidden" name="action" value="changePassword">
                        <div class="mb-3">
                            <label class="form-label">Current Password</label>
                            <input type="password" class="form-control" name="currentPassword" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">New Password</label>
                            <input type="password" class="form-control" name="newPassword" required minlength="6">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Confirm New Password</label>
                            <input type="password" class="form-control" name="confirmPassword" required minlength="6">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" form="changePasswordForm" class="btn btn-primary">Update Password</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Password validation
        document.getElementById('changePasswordForm')?.addEventListener('submit', function(e) {
            const newPassword = this.querySelector('input[name="newPassword"]').value;
            const confirmPassword = this.querySelector('input[name="confirmPassword"]').value;
            
            if (newPassword !== confirmPassword) {
                e.preventDefault();
                alert('New password and confirm password do not match!');
                return false;
            }
            
            if (newPassword.length < 6) {
                e.preventDefault();
                alert('Password must be at least 6 characters long!');
                return false;
            }
        });

        // Form validation for edit profile
        document.getElementById('editProfileForm')?.addEventListener('submit', function(e) {
            const phone = this.querySelector('input[name="phone"]').value;
            const address = this.querySelector('textarea[name="address"]').value;
            
            if (!phone || !address) {
                e.preventDefault();
                alert('Please fill in all required fields!');
                return false;
            }
        });

        // Auto-close modals on successful submission
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.get('success')) {
            // Close any open modals
            const modals = document.querySelectorAll('.modal.show');
            modals.forEach(modal => {
                const bootstrapModal = bootstrap.Modal.getInstance(modal);
                if (bootstrapModal) {
                    bootstrapModal.hide();
                }
            });
            
            // Show success message
            alert(urlParams.get('success'));
        }
    </script>
</body>
</html>