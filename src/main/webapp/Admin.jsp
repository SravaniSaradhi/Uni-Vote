<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Login | Uni-Vote</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<!-- Navigation Bar -->
<div class="navbar">
    <div class="logo">Uni-Vote</div>
    <div>
        <a href="index.jsp">Home</a>
        <a href="VoterLogin.jsp">Voter Login</a>
        <a href="CandidateLogin.jsp">Candidate Login</a>
    </div>
</div>

<!-- Center Login Box -->
<div class="center-box">
    <h2>Admin Login</h2>

    <form action="AdminLoginServlet" method="post">

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
