<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Candidate Login | Uni-Vote</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<!-- Navigation Bar -->
<div class="navbar">
    <div class="logo">Uni-Vote</div>
    <div>
        <a href="index.jsp">Home</a>
        <a href="AdminLogin.jsp">Admin Login</a>
        <a href="VoterLogin.jsp">Voter Login</a>
    </div>
</div>

<!-- Login Card -->
<div class="center-box">
    <h2>Candidate Login</h2>

    <form action="CandidateLoginServlet" method="post">

        <label>Candidate Name</label>
        <input type="text" name="name" required>

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
