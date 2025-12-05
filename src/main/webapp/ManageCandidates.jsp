<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.servlet.vote.dto.Candidate" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Candidates | Uni-Vote Admin</title>
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

    <h2>Manage Candidates</h2>
    <p>Approve or delete candidate accounts registered in the system.</p>

    <!-- Styled Table -->
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Party</th>
            <th>Manifesto</th>
            <th>Approved</th>
            <th>Actions</th>
        </tr>

        <%
            List<Candidate> list = (List<Candidate>) request.getAttribute("candidates");
            if (list != null) {
                for (Candidate c : list) {
        %>

        <tr>
            <td><%= c.getId() %></td>
            <td><%= c.getName() %></td>
            <td><%= c.getParty() %></td>
            <td><%= c.getManifesto() %></td>
            <td><%= c.isApproved() ? "Yes" : "No" %></td>

            <td>
                <form action="ManageCandidateServlet" method="post" style="display:flex; gap:10px;">

                    <input type="hidden" name="candidateId" value="<%= c.getId() %>">

                    <% if (!c.isApproved()) { %>
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

</body>
</html>
