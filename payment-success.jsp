<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Successful</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #00c853, #b2ff59);
            font-family: 'Poppins', sans-serif;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .card {
            background: #fff;
            border: none;
            border-radius: 20px;
            padding: 40px;
            text-align: center;
            box-shadow: 0 8px 20px rgba(0,0,0,0.2);
            animation: fadeIn 1s ease-in-out;
            width: 400px;
        }
        .card i {
            font-size: 80px;
            color: #00c853;
            margin-bottom: 20px;
        }
        h2 {
            font-weight: 700;
            color: #333;
        }
        p {
            font-size: 1.1rem;
            color: #555;
        }
        .countdown {
            font-size: 1rem;
            color: #00c853;
            margin-top: 10px;
        }
        .actions a {
            margin: 10px;
            border-radius: 10px;
            padding: 10px 25px;
            text-decoration: none;
            font-weight: 500;
            transition: all 0.3s ease;
        }
        .btn-dashboard {
            background-color: #00c853;
            color: white;
        }
        .btn-dashboard:hover {
            background-color: #009624;
        }
        .btn-home {
            background-color: #333;
            color: white;
        }
        .btn-home:hover {
            background-color: #000;
        }
        @keyframes fadeIn {
            from {opacity: 0; transform: scale(0.9);}
            to {opacity: 1; transform: scale(1);}
        }
    </style>
</head>
<body>
    <div class="card">
        <i class="fas fa-check-circle"></i>
        <h2>Payment Successful!</h2>
        <p>Thank you for your payment. Your transaction was completed successfully.</p>

        <div class="actions">
            <a href="dashboard.jsp" class="btn btn-dashboard">Go to Dashboard</a>
            <a href="index.jsp" class="btn btn-home">Back to Home</a>
        </div>

        <div class="countdown">
            Redirecting to Dashboard in <span id="timer">20</span> seconds...
        </div>
    </div>

    <script>
        let seconds = 20;
        const timerEl = document.getElementById('timer');
        const countdown = setInterval(() => {
            seconds--;
            timerEl.textContent = seconds;
            if (seconds <= 0) {
                clearInterval(countdown);
                window.location.href = "dashboard.jsp";
            }
        }, 1000);
    </script>
</body>
</html>
