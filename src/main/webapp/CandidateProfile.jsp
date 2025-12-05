<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.servlet.vote.dto.Candidate" %>

<%
    Candidate candidate = (Candidate) session.getAttribute("candidate");
    if (candidate == null) {
        response.sendRedirect(request.getContextPath() + "/candidate/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Candidate Profile | Uni-Vote</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<!-- Navbar -->
<div class="navbar">
    <div class="logo">Candidate Panel</div>
    <div>
        <a href="CandidateDashboard.jsp">Dashboard</a>
        <a href="CandidateProfile.jsp">Profile</a>
        <a href="CandidateLogoutServlet" class="btn-danger"
           style="padding:8px 14px; border-radius:6px; background:#dc3545; color:white; text-decoration:none;">
            Logout
        </a>
    </div>
</div>

<div class="container">

    <h2>Update Profile</h2>

    <div class="card">
        <form action="UpdateCandidateServlet" method="post">

            <input type="hidden" name="id" value="<%= candidate.getId() %>">

            <label>Name</label>
            <input type="text" name="name" value="<%= candidate.getName() %>" required>

            <label>Party</label>
            <input type="text" name="party" value="<%= candidate.getParty() %>" required>

            <label>Manifesto</label>
            <textarea name="manifesto" rows="4" required><%= candidate.getManifesto() %></textarea>

            <br>
            <input type="submit" value="Update">
        </form>
    </div>

</div>

</body>
</html>
