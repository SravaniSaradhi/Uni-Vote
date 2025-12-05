<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.servlet.vote.dto.Admin" %>

<%
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("AdminLogin.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard | Uni-Vote</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="logo">Admin Panel</div>
    <div>
        <a href="AdminDashboard.jsp">Dashboard</a>
        <a href="ManageVoterServlet">Manage Voters</a>
        <a href="ManageCandidateServlet">Manage Candidates</a>
        <a href="ManageElectionServlet">Manage Elections</a>
        <a href="PublishResultsServlet">Results</a>

        <a href="AdminLogoutServlet" class="btn-danger"
           style="padding:8px 14px; border-radius:6px; background:#dc3545; color:white; text-decoration:none;">
            Logout
        </a>
    </div>
</div>

<div class="container">

    <h2>Welcome, <%= admin.getUsername() %></h2>
    <p class="subtitle">Use the admin panel below to manage voting activities.</p>

    <!-- DASHBOARD CARDS -->
    <div class="card">
        <h3>Manage Voters</h3>
        <p>Add, update, remove voters from the system.</p>
        <a href="ManageVoterServlet"><button>Go to Voter Management</button></a>
    </div>

    <div class="card">
        <h3>Manage Candidates</h3>
        <p>Approve candidate registrations and update profiles.</p>
        <a href="ManageCandidateServlet"><button class="btn-secondary">Go to Candidate Management</button></a>
    </div>

    <div class="card">
        <h3>Manage Elections</h3>
        <p>Create, start, end elections, and monitor statuses.</p>
        <a href="ManageElectionServlet"><button class="btn-danger">Go to Election Management</button></a>
    </div>

    <div class="card">
        <h3>View Results</h3>
        <p>Check the final election results after voting ends.</p>
        <a href="PublishResultsServlet"><button>View Results</button></a>
    </div>

</div>

</body>
</html>
