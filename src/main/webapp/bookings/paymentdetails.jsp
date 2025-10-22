<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Check if user is logged in
    String userEmail = (String) session.getAttribute("userEmail");
    if (userEmail == null) {
        response.sendRedirect("../auth/login/LoginOption.jsp");
        return;
    }
    
    String carId = request.getParameter("carId");
    String carName = "";
    String carPrice = "";
    String carImage = "";
    
    // Get car details based on carId
    if ("1".equals(carId)) {
        carName = "Toyota Corolla";
        carPrice = "35";
        carImage = "https://images.unsplash.com/photo-1544636331-e26879cd4d9b";
    } else if ("2".equals(carId)) {
        carName = "Honda CR-V";
        carPrice = "55";
        carImage = "https://images.unsplash.com/photo-1549317661-bd32c8ce0db2";
    } else if ("3".equals(carId)) {
        carName = "BMW 5 Series";
        carPrice = "89";
        carImage = "https://images.unsplash.com/photo-1503376780353-7e6692767b70";
    }
    
    // Redirect if no car selected
    if (carId == null || carName.isEmpty()) {
        response.sendRedirect("../index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Details | DriveEasy Rentals</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        :root {
            --primary: #2c3e50;
            --secondary: #3498db;
            --accent: #e74c3c;
        }
        
        .payment-container {
            background: #f8f9fa;
            min-height: 100vh;
            padding: 30px 0;
        }
        
        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        }
        
        .car-summary {
            background: linear-gradient(135deg, var(--secondary), var(--primary));
            color: white;
            border-radius: 10px;
            padding: 20px;
        }
        
        .form-control {
            border-radius: 10px;
            padding: 12px 15px;
            border: 1px solid #dee2e6;
        }
        
        .form-control:focus {
            box-shadow: 0 0 0 0.25rem rgba(52, 152, 219, 0.25);
            border-color: var(--secondary);
        }
        
        .btn-primary {
            background: var(--secondary);
            border: none;
            border-radius: 10px;
            padding: 12px;
            font-weight: 600;
        }
        
        .btn-primary:hover {
            background: #2980b9;
        }
        
        .step-indicator {
            display: flex;
            justify-content: center;
            margin-bottom: 30px;
        }
        
        .step {
            display: flex;
            align-items: center;
            margin: 0 15px;
        }
        
        .step-number {
            width: 35px;
            height: 35px;
            border-radius: 50%;
            background: #dee2e6;
            color: #6c757d;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            margin-right: 10px;
        }
        
        .step.active .step-number {
            background: var(--secondary);
            color: white;
        }
        
        .step.completed .step-number {
            background: var(--accent);
            color: white;
        }
        
        .step-text {
            font-size: 14px;
            color: #6c757d;
        }
        
        .step.active .step-text {
            color: var(--secondary);
            font-weight: 600;
        }
        
        .step.completed .step-text {
            color: var(--accent);
            font-weight: 600;
        }
    </style>
</head>
<body>
    <div class="payment-container">
        <div class="container">
            <!-- Navigation -->
            <nav class="navbar navbar-light mb-4">
                <div class="container-fluid">
                    <a class="navbar-brand fw-bold" href="../index.jsp">
                        <i class="fas fa-car me-2"></i>DriveEasy Rentals
                    </a>
                    <div class="d-flex align-items-center">
                        <span class="me-3"><i class="fas fa-user me-2"></i><%= userEmail %></span>
                        <a href="../user/dashboard.jsp" class="btn btn-outline-primary btn-sm">Dashboard</a>
                    </div>
                </div>
            </nav>

            <!-- Step Indicator -->
            <div class="step-indicator">
                <div class="step completed">
                    <div class="step-number">1</div>
                    <div class="step-text">Register</div>
                </div>
                <div class="step active">
                    <div class="step-number">2</div>
                    <div class="step-text">Payment & Details</div>
                </div>
                <div class="step">
                    <div class="step-number">3</div>
                    <div class="step-text">Confirmation</div>
                </div>
            </div>

            <div class="row justify-content-center">
                <div class="col-lg-10">
                    <div class="card">
                        <div class="card-body p-4">
                            <div class="row">
                                <!-- Car Summary -->
                                <div class="col-md-5">
                                    <div class="car-summary h-100">
                                        <h4 class="mb-4">Booking Summary</h4>
                                        <div class="mb-4">
                                            <img src="<%= carImage %>" alt="<%= carName %>" 
                                                 class="img-fluid rounded mb-3" 
                                                 style="height: 150px; width: 100%; object-fit: cover;">
                                            <h5><%= carName %></h5>
                                            <p class="mb-2"><i class="fas fa-tag me-2"></i>$<%= carPrice %> per day</p>
                                            <p class="mb-0"><i class="fas fa-check-circle me-2"></i>Available for rental</p>
                                        </div>
                                        
                                        <div class="border-top pt-3">
                                            <h6>Price Breakdown</h6>
                                            <div class="d-flex justify-content-between mb-2">
                                                <span>Daily Rate:</span>
                                                <span>$<%= carPrice %></span>
                                            </div>
                                            <div class="d-flex justify-content-between mb-2">
                                                <span>Insurance:</span>
                                                <span>$15.00</span>
                                            </div>
                                            <div class="d-flex justify-content-between mb-2">
                                                <span>Taxes & Fees:</span>
                                                <span>$8.50</span>
                                            </div>
                                            <div class="d-flex justify-content-between fw-bold fs-5">
                                                <span>Total (est.):</span>
                                                <span>$<%= Integer.parseInt(carPrice) + 15 + 8 %>.50/day</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Payment Form -->
                                <div class="col-md-7">
                                    <h4 class="mb-4">Complete Your Bookings</h4>
                                    
                                    <form action="ProcessBookingServlet" method="post">
                                        <input type="hidden" name="carId" value="<%= carId %>">
                                        
                                        <!-- Rental Details -->
                                        <div class="mb-4">
                                            <h5 class="mb-3"><i class="fas fa-calendar-alt me-2"></i>Rental Details</h5>
                                            <div class="row">
                                                <div class="col-md-6 mb-3">
                                                    <label class="form-label">Pickup Date *</label>
                                                    <input type="date" class="form-control" name="pickupDate" required>
                                                </div>
                                                <div class="col-md-6 mb-3">
                                                    <label class="form-label">Return Date *</label>
                                                    <input type="date" class="form-control" name="returnDate" required>
                                                </div>
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label class="form-label">Pickup Location *</label>
                                                <select class="form-select" name="pickupLocation" required>
                                                    <option value="">Select location</option>
                                                    <option value="airport">City Airport</option>
                                                    <option value="downtown">Downtown Office</option>
                                                    <option value="mall">Shopping Mall Branch</option>
                                                    <option value="hotel">Hotel Delivery</option>
                                                </select>
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label class="form-label">Special Requests</label>
                                                <textarea class="form-control" name="specialRequests" 
                                                          placeholder="Any special requirements?" rows="2"></textarea>
                                            </div>
                                        </div>

                                        <!-- Payment Information -->
                                        <div class="mb-4">
                                            <h5 class="mb-3"><i class="fas fa-credit-card me-2"></i>Payment Information</h5>
                                            <div class="mb-3">
                                                <label class="form-label">Cardholder Name *</label>
                                                <input type="text" class="form-control" name="cardName" 
                                                       placeholder="Name on card" required>
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label class="form-label">Card Number *</label>
                                                <input type="text" class="form-control" name="cardNumber" 
                                                       placeholder="1234 5678 9012 3456" required
                                                       pattern="[0-9\s]{13,19}" maxlength="19">
                                            </div>
                                            
                                            <div class="row mb-3">
                                                <div class="col-md-6">
                                                    <label class="form-label">Expiry Date *</label>
                                                    <input type="text" class="form-control" name="expiryDate" 
                                                           placeholder="MM/YY" required pattern="(0[1-9]|1[0-2])\/[0-9]{2}">
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">CVV *</label>
                                                    <input type="text" class="form-control" name="cvv" 
                                                           placeholder="123" required pattern="[0-9]{3,4}">
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Insurance Options -->
                                        <div class="mb-4">
                                            <h5 class="mb-3"><i class="fas fa-shield-alt me-2"></i>Insurance Options</h5>
                                            <div class="form-check mb-2">
                                                <input class="form-check-input" type="radio" name="insurance" 
                                                       id="basicInsurance" value="basic" checked>
                                                <label class="form-check-label" for="basicInsurance">
                                                    Basic Insurance - $15/day (Included)
                                                </label>
                                            </div>
                                            <div class="form-check mb-2">
                                                <input class="form-check-input" type="radio" name="insurance" 
                                                       id="premiumInsurance" value="premium">
                                                <label class="form-check-label" for="premiumInsurance">
                                                    Premium Insurance - $25/day (Full coverage)
                                                </label>
                                            </div>
                                        </div>

                                        <!-- Terms and Submit -->
                                        <div class="mb-4">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" id="terms" required>
                                                <label class="form-check-label" for="terms">
                                                    I agree to the <a href="#" data-bs-toggle="modal" data-bs-target="#termsModal">rental terms and conditions</a>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="d-grid">
                                            <button type="submit" class="btn btn-primary btn-lg">
                                                <i class="fas fa-lock me-2"></i>Complete Booking - $<%= carPrice %>/day
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Terms Modal -->
    <div class="modal fade" id="termsModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Rental Terms & Conditions</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <h6>Important Information</h6>
                    <ul>
                        <li>Minimum rental age: 21 years</li>
                        <li>Valid driver's license required</li>
                        <li>Security deposit: $200 (refundable)</li>
                        <li>Fuel policy: Return with same level</li>
                        <li>Late returns: Additional charges apply</li>
                        <li>No smoking in vehicles</li>
                    </ul>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Set minimum dates
        const today = new Date().toISOString().split('T')[0];
        document.querySelector('input[name="pickupDate"]').setAttribute('min', today);
        document.querySelector('input[name="returnDate"]').setAttribute('min', today);
        
        // Update return date min when pickup date changes
        document.querySelector('input[name="pickupDate"]').addEventListener('change', function() {
            document.querySelector('input[name="returnDate"]').setAttribute('min', this.value);
        });
        
        // Format card number
        document.querySelector('input[name="cardNumber"]').addEventListener('input', function(e) {
            let value = e.target.value.replace(/\s+/g, '').replace(/[^0-9]/gi, '');
            let formattedValue = value.match(/.{1,4}/g)?.join(' ');
            if (formattedValue) {
                e.target.value = formattedValue;
            }
        });
        
        // Format expiry date
        document.querySelector('input[name="expiryDate"]').addEventListener('input', function(e) {
            let value = e.target.value.replace(/\D/g, '');
            if (value.length >= 2) {
                value = value.substring(0, 2) + '/' + value.substring(2, 4);
            }
            e.target.value = value;
        });
        
        // Form submission handler
        document.querySelector('form').addEventListener('submit', function(e) {
            const submitBtn = this.querySelector('button[type="submit"]');
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Processing...';
            submitBtn.disabled = true;
        });
    </script>
</body>
</html>