<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.servlet.vote.dto.Voter" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Voters | Uni-Vote Admin</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<!-- Navigation -->
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

    <h2>Manage Voters</h2>
    <p class="subtitle">Approve or remove voter accounts registered in the Uni-Vote system.</p>

    <div class="card" style="padding:0;">
        <!-- Styled Table -->
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Approved</th>
                <th>Actions</th>
            </tr>

            <%
                List<Voter> voters = (List<Voter>) request.getAttribute("voters");
                if (voters != null) {
                    for (Voter v : voters) {
            %>

            <tr>
                <td><%= v.getId() %></td>
                <td><%= v.getName() %></td>
                <td><%= v.getEmail() %></td>
                <td><%= v.getPhone() %></td>
                <td>
                    <span style="color:<%= v.isApproved() ? "green" : "orange" %>; font-weight:600;">
                        <%= v.isApproved() ? "Yes" : "No" %>
                    </span>
                </td>

                <td>
                    <form action="ManageVoterServlet" method="post" style="display:flex; gap:10px;">
                        <input type="hidden" name="voterId" value="<%= v.getId() %>">

                        <% if (!v.isApproved()) { %>
                            <button 
                                name="action" 
                                value="approve"
                                style="background:#1a3c6e; color:white; padding:8px 12px; border:none; border-radius:6px;">
                                Approve
                            </button>
                        <% } %>

                        <button 
                            name="action" 
                            value="delete"
                            class="btn-danger"
                            style="padding:8px 12px; border-radius:6px;">
                            Delete
                        </button>
                    </form>
                </td>
            </tr>

            <%
                    }
                }
            %>

        </table>
    </div>

</div>

</body>
</html>
