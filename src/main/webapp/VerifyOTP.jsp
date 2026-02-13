<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Email Verification | Uni-Vote</title>
    <link rel="stylesheet" href="css/styles.css">

    <style>
        /* Background same as login */
        .auth-bg {
            min-height: 100vh;
            background:
                linear-gradient(rgba(0,0,0,0.55), rgba(0,0,0,0.55)),
                url("assets/images/voting-bg.jpg") no-repeat center center;
            background-size: cover;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        /* Card */
        .auth-card {
            background: #ffffff;
            padding: 35px;
            width: 380px;
            border-radius: 12px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.3);
        }

        .auth-card h2 {
            text-align: center;
            margin-bottom: 10px;
            color: #1a3c6e;
        }

        .auth-card p.info {
            text-align: center;
            font-size: 14px;
            color: #555;
            margin-bottom: 20px;
        }

        .auth-card label {
            font-weight: 600;
            color: #333;
        }

        .auth-card input {
            width: 100%;
            padding: 12px;
            margin-top: 8px;
            margin-bottom: 18px;
            border-radius: 8px;
            border: 1px solid #cfd6e0;
            background: #f9fbfe;
            transition: 0.3s ease;
        }

        .auth-card input:focus {
            outline: none;
            border-color: #1a3c6e;
            background: #ffffff;
        }

        .auth-card input[type="submit"] {
            width: 100%;
            padding: 12px;
            background: #1a3c6e;
            color: white;
            border: none;
            border-radius: 8px;
            font-weight: bold;
            cursor: pointer;
            transition: 0.3s ease;
        }

        .auth-card input[type="submit"]:hover {
            background: #0f2a52;
        }

        .error {
            color: red;
            text-align: center;
            margin-top: 12px;
            font-size: 14px;
        }
    </style>
</head>

<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="logo">Uni-Vote</div>
    <div>
        <a href="index.jsp">Home</a>
        <a href="VoterRegister.jsp">Register</a>
        <a href="VoterLogin.jsp">Login</a>
    </div>
</div>

<!-- OTP CARD -->
<div class="auth-bg">
    <div class="auth-card">

        <h2>Email Verification</h2>
        <p class="info">
            Please enter the OTP sent to your registered email address.
        </p>

        <form action="VerifyOTPServlet" method="post">
            <label>One-Time Password (OTP)</label>
            <input type="text" name="otp" placeholder="Enter 6-digit OTP" required>

            <input type="submit" value="Verify OTP">
        </form>

        <p class="error">
            <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
        </p>

    </div>
</div>

</body>
</html>
