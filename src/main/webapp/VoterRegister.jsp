<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Voter Registration | Uni-Vote</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<div class="navbar">
    <div class="logo">Uni-Vote</div>
    <div>
        <a href="index.jsp">Home</a>
        <a href="VoterLogin.jsp">Voter Login</a>
        <a href="CandidateLogin.jsp">Candidate Login</a>
        <a href="AdminLogin.jsp">Admin Login</a>
    </div>
</div>

<div class="center-box">
    <h2>Voter Registration</h2>

    <form action="VoterRegisterServlet" method="post">
        <label>Full Name</label>
        <input type="text" name="name" required>

        <label>Email</label>
        <input type="email" name="email" required>

        <label>Aadhar Number</label>
        <input type="text" name="aadhar" maxlength="12" required>

        <label>Date of Birth</label>
        <input type="date" name="dob" required>

        <label>Phone</label>
        <input type="text" name="phone" maxlength="10" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <br>
        <input type="submit" value="Register">
    </form>

    <p style="color:green; margin-top:10px;">
        <%= request.getAttribute("success") != null ? request.getAttribute("success") : "" %>
    </p>

    <p style="color:red; margin-top:5px;">
        <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
    </p>
</div>

</body>
</html>
