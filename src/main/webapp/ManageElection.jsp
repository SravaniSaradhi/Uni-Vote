<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.servlet.vote.dto.Election" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Elections | Uni-Vote Admin</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<!-- Navigation Bar -->
<div class="navbar">
    <div class="logo">Admin Panel</div>
    <div>
        <a href="AdminDashboard.jsp">Dashboard</a>
        <a href="ManageVoters.jsp">Manage Voters</a>
        <a href="ManageCandidates.jsp">Manage Candidates</a>
        <a href="ManageElection.jsp">Manage Election</a>

        <a href="index.jsp" class="btn-danger" 
           style="padding:8px 14px; border-radius:6px; background:#dc3545; color:white; text-decoration:none;">
            Logout
        </a>
    </div>
</div>

<div class="container">

    <h2>Manage Elections</h2>

    <!-- Create Election Form -->
    <div class="card">
        <h3>Create New Election</h3>

        <form action="ManageElectionServlet" method="post">

            <label>Election Title</label>
            <input type="text" name="title" placeholder="Enter election title" required>

            <label>Description</label>
            <textarea name="description" placeholder="Enter description" rows="4" required></textarea>

            <br>
            <input type="submit" value="Create Election">
        </form>
    </div>

    <!-- Existing Elections Table -->
    <h3 style="margin-top:30px;">Existing Elections</h3>

    <table>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>

        <%
            List<Election> list = (List<Election>) request.getAttribute("elections");
            if (list != null) {
                for (Election e : list) {
        %>

        <tr>
            <td><%= e.getId() %></td>
            <td><%= e.getTitle() %></td>
            <td><%= e.getStatus() %></td>

            <td style="display:flex; gap:10px;">

                <a href="StartElectionServlet?id=<%= e.getId() %>">
                    <button style="background:#1a3c6e; color:white; padding:8px 12px; 
                                   border:none; border-radius:6px;">
                        Start
                    </button>
                </a>

                <a href="EndElectionServlet?id=<%= e.getId() %>">
                    <button class="btn-danger" 
                            style="padding:8px 12px; border-radius:6px;">
                        End
                    </button>
                </a>
            </td>
        </tr>

        <%
                }
            }
        %>

    </table>

</div>

</body>
</html>
