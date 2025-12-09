<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.servlet.vote.dto.Election" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Candidate Registration | Uni-Vote</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<!-- Navigation Bar -->
<div class="navbar">
    <div class="logo">Uni-Vote</div>
    <div>
        <a href="index.jsp">Home</a>
        <a href="CandidateLogin.jsp">Candidate Login</a>
        <a href="VoterLogin.jsp">Voter Login</a>
        <a href="AdminLogin.jsp">Admin Login</a>
    </div>
</div>

<div class="center-box">
    <h2>Candidate Registration</h2>

    <form action="CandidateRegisterServlet" method="post">

        <label>Candidate Name</label>
        <input type="text" name="name" required>

        <label>Party Name</label>
        <input type="text" name="party" required>

        <label>Manifesto</label>
        <textarea name="manifesto" rows="4" required></textarea>

        <!-- New Election Dropdown -->
        <label>Select Election</label>
        <select name="electionId" required>
            <option value="">-- Select Election --</option>

            <%
                List<Election> elections = (List<Election>)request.getAttribute("elections");
                if (elections != null) {
                    for (Election e : elections) {
            %>
                <option value="<%= e.getId() %>"><%= e.getTitle() %></option>
            <%
                    }
                }
            %>
        </select>

        <label>Password</label>
        <input type="password" name="password" required>

        <br>
        <input type="submit" value="Register">
    </form>

    <!-- Success Message -->
    <p style="color:green; margin-top:10px;">
        <%= request.getAttribute("success") != null ? request.getAttribute("success") : "" %>
    </p>

    <!-- Error Message -->
    <p style="color:red; margin-top:5px;">
        <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
    </p>

</div>

</body>
</html>
