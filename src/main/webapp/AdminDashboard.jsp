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

    <style>
        body {
    margin: 0;
    font-family: "Segoe UI", sans-serif;
    background: #f4f7fb;

    /* 🌊 PAGE ANIMATION */
    opacity: 0;
    transform: translateY(-40px);
    animation: pageSlideDown 0.9s ease-out forwards;
}

@keyframes pageSlideDown {
    from {
        opacity: 0;
        transform: translateY(-40px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* NAVBAR */
.navbar {
    background: #1a3c6e;
    padding: 16px 40px;
    color: white;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.navbar .logo {
    font-size: 22px;
    font-weight: bold;
}

.navbar a {
    color: white;
    text-decoration: none;
    margin-left: 18px;
    font-weight: 500;
}

.navbar a:hover {
    text-decoration: underline;
}

/* CONTAINER */
.container {
    max-width: 1100px;
    margin: 40px auto;
    padding: 0 20px;
}

.container h2 {
    color: #1a3c6e;
    text-align: center;
    font-size: 32px;
    margin-bottom: 5px;
}

.subtitle {
    text-align: center;
    color: #555;
    margin-bottom: 40px;
    animation: fadeIn 0.8s ease-in;
}

@keyframes fadeIn {
    from { opacity: 0; }
    to { opacity: 1; }
}

/* GRID */
.dashboard-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 30px;
}

/* CARD */
.card {
    background: #ffffff;
    padding: 30px;
    border-radius: 16px;
    box-shadow: 0 12px 30px rgba(0,0,0,0.1);
    text-align: center;
    transition: 0.3s ease;

    /* 🎴 CARD ANIMATION */
    opacity: 0;
    transform: translateY(50px) scale(0.96);
    animation: cardRise 0.8s ease-out forwards;
}

/* Stagger animation */
.card:nth-child(1) { animation-delay: 0.2s; }
.card:nth-child(2) { animation-delay: 0.35s; }
.card:nth-child(3) { animation-delay: 0.5s; }
.card:nth-child(4) { animation-delay: 0.65s; }

@keyframes cardRise {
    from {
        opacity: 0;
        transform: translateY(50px) scale(0.96);
    }
    to {
        opacity: 1;
        transform: translateY(0) scale(1);
    }
}

.card:hover {
    transform: translateY(-6px);
    box-shadow: 0 18px 40px rgba(0,0,0,0.15);
}

.card h3 {
    color: #1a3c6e;
    margin-bottom: 10px;
}

.card p {
    color: #666;
    font-size: 14px;
    margin-bottom: 22px;
}

/* BUTTONS */
.card button {
    padding: 12px 26px;
    border-radius: 30px;
    border: none;
    font-weight: bold;
    cursor: pointer;
    background: #1a3c6e;
    color: white;
    transition: all 0.25s ease;
}

.btn-secondary {
    background: #6c757d;
}

.btn-danger {
    background: #dc3545;
}

.card button:hover {
    transform: translateY(-3px);
    box-shadow: 0 10px 22px rgba(0,0,0,0.2);
}

/* RESPONSIVE */
@media (max-width: 768px) {
    .dashboard-grid {
        grid-template-columns: 1fr;
    }
}
        
    </style>
</head>

<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="logo">🗳️ Uni-Vote Admin</div>
    <div>
        <a href="AdminDashboard.jsp">Dashboard</a>
        <a href="AdminLogoutServlet"
           style="background:#dc3545; padding:8px 14px; border-radius:6px;">
            Logout
        </a>
    </div>
</div>

<!-- MAIN CONTENT -->
<div class="container">

    <h2>Welcome, <%= admin.getUsername() %></h2>
    <p class="subtitle">Use the admin panel below to manage voting activities</p>

    <!-- 2 × 2 GRID -->
    <div class="dashboard-grid">

        <div class="card">
            <h3>Manage Voters</h3>
            <p>Add, update, approve or remove voters.</p>
            <a href="ManageVoterServlet">
                <button>Voter Management</button>
            </a>
        </div>

        <div class="card">
            <h3>Manage Candidates</h3>
            <p>Approve candidate registrations and profiles.</p>
            <a href="ManageCandidateServlet">
                <button class="btn-secondary">Candidate Management</button>
            </a>
        </div>

        <div class="card">
            <h3>Manage Elections</h3>
            <p>Create, start and end elections.</p>
            <a href="ManageElectionServlet">
                <button class="btn-danger">Election Management</button>
            </a>
        </div>

        <div class="card">
            <h3>View Results</h3>
            <p>Check final vote counts and results.</p>
            <a href="PublishResultsServlet">
                <button>View Results</button>
            </a>
        </div>

    </div>
</div>

</body>
</html>
