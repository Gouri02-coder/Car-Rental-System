<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Car Rental - Secure Payment</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            font-family: "Poppins", sans-serif;
            background: linear-gradient(135deg, #0072ff, #00c6ff);
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .payment-container {
            background: #fff;
            border-radius: 20px;
            box-shadow: 0 8px 35px rgba(0,0,0,0.15);
            padding: 35px;
            width: 420px;
            text-align: center;
            animation: fadeIn 0.8s ease-in-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        h2 {
            color: #0072ff;
            font-weight: 600;
            margin-bottom: 10px;
        }

        .payment-options {
            display: flex;
            justify-content: space-between;
            margin: 20px 0;
        }

        .option {
            flex: 1;
            margin: 0 5px;
            background: #f0f7ff;
            border: 2px solid transparent;
            border-radius: 12px;
            padding: 10px;
            cursor: pointer;
            transition: 0.3s;
            font-weight: 500;
        }

        .option:hover, .option.active {
            background: #0072ff;
            color: #fff;
            border-color: #005fcc;
        }

        .upi-section, .card-section {
            display: none;
            margin-top: 20px;
        }

        .upi-section img {
            width: 220px;
            border-radius: 12px;
            border: 2px solid #eee;
        }

        .card-form input {
            width: 90%;
            padding: 10px;
            margin: 8px 0;
            border: 1px solid #ccc;
            border-radius: 8px;
            outline: none;
            font-size: 14px;
        }

        .card-form input:focus {
            border-color: #0072ff;
            box-shadow: 0 0 5px rgba(0,114,255,0.3);
        }

        .btn {
            background: #0072ff;
            color: #fff;
            border: none;
            border-radius: 8px;
            padding: 10px 18px;
            font-weight: 600;
            cursor: pointer;
            margin-top: 10px;
            transition: 0.3s;
        }

        .btn:hover {
            background: #005fcc;
        }

        .note {
            margin-top: 15px;
            font-size: 13px;
            color: #777;
        }

        .divider {
            height: 1px;
            background: #e0e0e0;
            margin: 15px 0;
        }

        @media (max-width: 450px) {
            .payment-container {
                width: 90%;
                padding: 25px;
            }
        }
    </style>
</head>
<body>

<div class="payment-container">
    <h2>Secure Payment Gateway</h2>
    <p>Select your preferred payment method</p>

    <div class="payment-options">
        <div class="option" id="upiOpt" onclick="showSection('upi')">UPI</div>
        <div class="option" id="creditOpt" onclick="showSection('credit')">Credit Card</div>
        <div class="option" id="debitOpt" onclick="showSection('debit')">Debit Card</div>
    </div>

    <div class="divider"></div>

    <!-- ✅ UPI Payment Section -->
    <div class="upi-section" id="upi">
        <h3>Scan & Pay via PhonePe</h3>
        <!-- Replace with your QR path -->
        <img src="<%= request.getContextPath() %>/assets/qr/upi.jpg" alt="PhonePe UPI QR Code">
        <p class="note">Scan this QR using PhonePe, GPay, or Paytm</p>
        <a href="upi://pay?pa=merchant@bank&pn=CarRental&am=1500&cu=INR&tn=CarRentalBooking" class="btn">Open in UPI App</a>
    </div>

    <!-- 💳 Credit Card Form -->
    <div class="card-section" id="credit">
        <h3>Pay using Credit Card</h3>
        <form class="card-form" onsubmit="simulatePayment(event, 'Credit Card')">
            <input type="text" placeholder="Cardholder Name" required><br>
            <input type="text" maxlength="16" placeholder="Card Number" required><br>
            <input type="text" maxlength="5" placeholder="MM/YY" required><br>
            <input type="text" maxlength="3" placeholder="CVV" required><br>
            <button class="btn" type="submit">Proceed to Pay</button>
        </form>
    </div>

    <!-- 🏧 Debit Card Form -->
    <div class="card-section" id="debit">
        <h3>Pay using Debit Card</h3>
        <form class="card-form" onsubmit="simulatePayment(event, 'Debit Card')">
            <input type="text" placeholder="Cardholder Name" required><br>
            <input type="text" maxlength="16" placeholder="Card Number" required><br>
            <input type="text" maxlength="5" placeholder="MM/YY" required><br>
            <input type="text" maxlength="3" placeholder="CVV" required><br>
            <button class="btn" type="submit">Proceed to Pay</button>
        </form>
    </div>

    <p class="note">⚠ </p>
</div>

<script>
    function showSection(type) {
        document.querySelectorAll('.upi-section, .card-section').forEach(el => el.style.display = 'none');
        document.querySelectorAll('.option').forEach(btn => btn.classList.remove('active'));
        if (type === 'upi') {
            document.getElementById('upi').style.display = 'block';
            document.getElementById('upiOpt').classList.add('active');
        } else if (type === 'credit') {
            document.getElementById('credit').style.display = 'block';
            document.getElementById('creditOpt').classList.add('active');
        } else if (type === 'debit') {
            document.getElementById('debit').style.display = 'block';
            document.getElementById('debitOpt').classList.add('active');
        }
    }

    function simulatePayment(event, method) {
        event.preventDefault();
        alert(method + " Payment Successful ✅ (Demo)");
        window.location.href = "<%= request.getContextPath() %>/user/payment-success.jsp";
    }
</script>

</body>
</html>