<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Car Rental System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
        }
        .login-card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-5">
                <!-- Back to Home -->
                <div class="text-center mb-4">
                    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-outline-light">
                        ← Back to Home
                    </a>
                </div>

                <div class="card login-card">
                    <div class="card-header bg-white border-0 text-center pt-4">
                        <h3 class="fw-bold text-primary">Welcome Back</h3>
                        <p class="text-muted">Sign in to your account</p>
                    </div>
                    <div class="card-body p-4">
                        <!-- Display Messages -->
                        <% 
                            String error = (String) request.getAttribute("errorMessage");
                            String success = (String) request.getAttribute("successMessage");
                            
                            if (error != null) {
                        %>
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <%= error %>
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        <%
                            }
                            if (success != null) {
                        %>
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                <%= success %>
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        <%
                            }
                        %>
                        
                        <!-- Login Form -->
                        <form action="${pageContext.request.contextPath}/UserServlet" method="post">
                            <input type="hidden" name="action" value="login">
                            
                            <div class="mb-3">
                                <label for="email" class="form-label">Email Address</label>
                                <input type="email" class="form-control form-control-lg" 
                                       id="email" name="email" placeholder="Enter your email" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" class="form-control form-control-lg" 
                                       id="password" name="password" placeholder="Enter your password" required>
                            </div>
                            
                            <div class="d-grid mb-3">
                                <button type="submit" class="btn btn-primary btn-lg">Sign In</button>
                            </div>
                        </form>
                        
                        <!-- Divider -->
                        <div class="text-center mb-3">
                            <span class="text-muted">Don't have an account?</span>
                        </div>
                        
                        <!-- Register Link -->
                        <div class="d-grid">
                            <a href="${pageContext.request.contextPath}/auth/register.jsp" class="btn btn-outline-primary btn-lg">
                                Create New Account
                            </a>
                        </div>
                    </div>
                </div>
                
                <!-- Additional Links -->
                <div class="text-center mt-3">
                    <a href="${pageContext.request.contextPath}/index.jsp" class="text-white">← Return to Homepage</a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>