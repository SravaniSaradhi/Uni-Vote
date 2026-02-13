<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.servlet.vote.dto.Candidate" %>

<%
    Candidate candidate = (Candidate) session.getAttribute("candidate");
    if (candidate == null) {
        response.sendRedirect("CandidateLogin.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Candidate Dashboard | Uni-Vote</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<style>

</style>
<body>

<!-- Navbar -->
<div class="navbar">
    <div class="logo">Candidate Dashboard</div>
    <div>
        <a href="CandidateDashboard.jsp">Home</a>
        <a href="CandidateProfile.jsp">Edit Profile</a>
        <a href="CandidateLogoutServlet" class="btn-danger" 
            style="padding:7px 12px; border-radius:6px; background:#dc3545; color:white; text-decoration:none;">
            Logout
        </a>
    </div>
</div>

<div class="container">

    <h2>Welcome, <%= candidate.getName() %></h2>

    <!-- Candidate Details -->
    <div class="card">
        <h3>Candidate Details</h3>

        <p><b>Name:</b> <%= candidate.getName() %></p>
        <p><b>Party:</b> <%= candidate.getParty() %></p>
        <p><b>Manifesto:</b> <%= candidate.getManifesto() %></p>
        <p>
            <b>Status:</b> 
            <span style="color:<%= candidate.isApproved() ? "green" : "orange" %>;">
                <%= candidate.isApproved() ? "Approved" : "Pending Approval" %>
            </span>
        </p>

        <a href="CandidateProfile.jsp">
            <button class="btn-secondary">Edit Profile</button>
        </a>
    </div>

    <!-- Vote Count -->
    <div class="card">
        <h3>Live Vote Count</h3>
        <p style="font-size:20px; font-weight:bold; color:#1a3c6e;">
            Votes Received: <%= candidate.getVotes() %>
        </p>
    </div>

</div>

</body>
</html>
