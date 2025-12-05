<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Voter Login | Uni-Vote</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
   input{
     width: 100%;
    padding: 12px;
    border: 1px solid #cfd6e0;
    border-radius: 8px;
    background: #f9fbfe;
    transition: 0.3s ease;
    margin-bottom: 18px;
    }
    </style>
</head>
<body>

<!-- Navigation Bar -->
<div class="navbar">
    <div class="logo">Uni-Vote</div>
    <div>
        <a href="index.jsp">Home</a>
        <a href="CandidateLogin.jsp">Candidate Login</a>
        <a href="AdminLogin.jsp">Admin Login</a>
    </div>
</div>

<!-- Center Login Card -->
<div class="center-box">
    <h2>Voter Login</h2>

    <form action="VoterLoginServlet" method="post">

        <label>Email</label>
        <input type="email" name="email" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <br>
        <input type="submit" value="Login">
    </form>

    <!-- Error Message -->
    <p style="color:red; margin-top:15px;">
        <%= request.getAttribute("msg") != null ? request.getAttribute("msg") : "" %>
    </p>

</div>

</body>
</html>
