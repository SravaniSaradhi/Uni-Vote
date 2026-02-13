<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Voter Login | Uni-Vote</title>

    <style>
      /* ---------- COMMON BACKGROUND ---------- */
body {
    margin: 0;
    font-family: Arial, Helvetica, sans-serif;
    min-height: 100vh;
    background:
        linear-gradient(rgba(0,0,0,0.2), rgba(0,0,0,0.2)),
        url("assets/images/voting.jpg")
        no-repeat center center;
    background-size: cover;

    /* 🌟 Animation Start */
    opacity: 0;
    transform: translateY(-45px);
    animation: pageLoad 1s ease-out forwards;
}

@keyframes pageLoad {
    0% {
        opacity: 0;
        transform: translateY(-45px);
    }
    100% {
        opacity: 1;
        transform: translateY(0);
    }
}
/* 🌟 End Animation */

/* ---------- NAVBAR ---------- */
.navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 18px 40px;
    background: rgba(0, 0, 0, 0.45);
}

.navbar .logo {
    color: #ffffff;
    font-size: 22px;
    font-weight: bold;
}

.navbar a {
    color: #ffffff;
    text-decoration: none;
    margin-left: 20px;
    font-weight: 500;
}

.navbar a:hover {
    text-decoration: underline;
}

/* ---------- WRAPPER ---------- */
.auth-bg {
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: calc(100vh - 80px);
}

/* ---------- CARD ---------- */
.auth-card {
    background: rgba(255, 255, 255, 0.96);
    padding: 35px 40px;
    width: 380px;
    border-radius: 14px;
    box-shadow: 0 20px 45px rgba(0,0,0,0.35);

    /* ✨ Card Float Animation */
    opacity: 0;
    transform: translateY(60px) scale(0.93);
    animation: cardFloat 1s ease-out forwards 0.3s;
}

@keyframes cardFloat {
    0% {
        opacity: 0;
        transform: translateY(60px) scale(0.93);
    }
    100% {
        opacity: 1;
        transform: translateY(0) scale(1);
    }
}

/* ---------- TITLES ---------- */
.auth-card h2 {
    text-align: center;
    margin-bottom: 25px;
    color: #1a3c6e;
}

/* ---------- FORM INPUTS ---------- */
.auth-card label {
    font-weight: 600;
    color: #333;
}

.auth-card input {
    width: 100%;
    padding: 12px;
    margin-top: 6px;
    margin-bottom: 18px;
    border-radius: 8px;
    border: 1px solid #cfd6e0;
    background: #f9fbfe;
    font-size: 14px;
}

.auth-card input:focus {
    outline: none;
    border-color: #1a3c6e;
}

/* ---------- BUTTON ---------- */
.auth-card input[type="submit"] {
    width: 100%;
    padding: 12px;
    background: #1a3c6e;
    color: white;
    border: none;
    border-radius: 30px;
    font-weight: bold;
    font-size: 15px;
    cursor: pointer;
    transition: 0.3s ease;
}

.auth-card input[type="submit"]:hover {
    background: #102a4c;
    transform: translateY(-4px);
    box-shadow: 0 12px 25px rgba(0,0,0,0.25);
}

.auth-card input[type="submit"]:active {
    transform: scale(0.95);
}

/* ---------- MESSAGE ---------- */
.error-msg {
    text-align: center;
    color: red;
    margin-top: 10px;
    font-size: 14px;
}

/* ---------- FOOTER ---------- */
.footer {
    text-align: center;
    color: #ddd;
    font-size: 14px;
    padding: 15px;
}
      
    </style>
</head>

<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="logo">🗳️ Uni-Vote</div>
    <div>
        <a href="index.jsp">Home</a>
        <a href="CandidateLogin.jsp">Candidate Login</a>
        <a href="AdminLogin.jsp">Admin Login</a>
    </div>
</div>

<!-- LOGIN CARD -->
<div class="auth-bg">
    <div class="auth-card">

        <h2>Voter Login</h2>

        <form action="VoterLoginServlet" method="post">

            <label>Email</label>
            <input type="email" name="email" required>

            <label>Password</label>
            <input type="password" name="password" required>

            <input type="submit" value="Login">
        </form>

        <div class="error-msg">
            <%= request.getAttribute("msg") != null ? request.getAttribute("msg") : "" %>
        </div>

    </div>
</div>

<div class="footer">
    © 2025 Uni-Vote | Secure Online Voting System
</div>

</body>
</html>
