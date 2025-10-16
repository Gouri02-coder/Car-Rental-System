<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Login - Car Rental System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .admin-login-container {
            height: 100vh;
            background: linear-gradient(rgba(220, 53, 69, 0.8), rgba(220, 53, 69, 0.9)), url('https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7');
            background-size: cover;
            background-position: center;
        }
        .login-card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.1);
        }
        .security-features {
            background: rgba(220, 53, 69, 0.1);
            border-radius: 10px;
            padding: 15px;
        }
        .btn-back {
            position: absolute;
            top: 20px;
            left: 20px;
        }
    </style>
</head>
<body>
    <div class="admin-login-container d-flex align-items-center">
        <a href="LoginOption.jsp" class="btn btn-light btn-back">
            <i class="fas fa-arrow-left me-2"></i>Back
        </a>
        
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6 col-lg-5">
                    <div class="card login-card">
                        <div class="card-body p-5">
                            <div class="text-center mb-4">
                                <i class="fas fa-user-shield fa-3x text-danger mb-3"></i>
                                <h3>Admin Login</h3>
                                <p class="text-muted">Access the administration panel</p>
                            </div>
                            
                            <!-- ONLY CHANGED THE FORM ACTION -->
                            <form action="${pageContext.request.contextPath}/AdminLoginServlet" method="post">
                                <div class="mb-3">
                                    <label for="adminUsername" class="form-label">Admin ID</label>
                                    <div class="input-group">
                                        <span class="input-group-text bg-light border-end-0">
                                            <i class="fas fa-user-tie text-danger"></i>
                                        </span>
                                        <input type="text" class="form-control border-start-0" id="adminUsername" 
                                               name="username" placeholder="Enter admin ID" required>
                                    </div>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="adminPassword" class="form-label">Password</label>
                                    <div class="input-group">
                                        <span class="input-group-text bg-light border-end-0">
                                            <i class="fas fa-key text-danger"></i>
                                        </span>
                                        <input type="password" class="form-control border-start-0" id="adminPassword" 
                                               name="password" placeholder="Enter admin password" required>
                                        <button class="btn btn-outline-secondary" type="button" id="toggleAdminPassword">
                                            <i class="fas fa-eye"></i>
                                        </button>
                                    </div>
                                </div>
                                
                                <div class="mb-3 form-check">
                                    <input type="checkbox" class="form-check-input" id="secureSession">
                                    <label class="form-check-label" for="secureSession">Secure session</label>
                                </div>
                                
                                <div class="d-grid mb-4">
                                    <button type="submit" class="btn btn-danger btn-lg">
                                        <i class="fas fa-sign-in-alt me-2"></i>Login as Admin
                                    </button>
                                </div>
                                
                                <div class="security-features mb-4">
                                    <h6 class="text-danger mb-3"><i class="fas fa-shield-alt me-2"></i>Security Notice</h6>
                                    <ul class="list-unstyled small mb-0">
                                        <li class="mb-1"><i class="fas fa-check-circle text-success me-2"></i>Authorized access only</li>
                                        <li class="mb-1"><i class="fas fa-check-circle text-success me-2"></i>Don't share your credentials</li>
                                    </ul>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('toggleAdminPassword').addEventListener('click', function() {
            const passwordInput = document.getElementById('adminPassword');
            const icon = this.querySelector('i');
            
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else {
                passwordInput.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        });
    </script>
</body>
</html>