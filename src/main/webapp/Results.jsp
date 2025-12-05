<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.servlet.vote.dto.Candidate" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Election Results | Uni-Vote</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<!-- Navbar -->
<div class="navbar">
    <div class="logo">Admin Panel</div>
    <div>
        <a href="AdminDashboard.jsp">Dashboard</a>
        <a href="ManageElection.jsp">Elections</a>
        <a href="index.jsp" class="btn-danger"
           style="padding:8px 14px; border-radius:6px; background:#dc3545; color:white; text-decoration:none;">
            Logout
        </a>
    </div>
</div>

<div class="container">

    <h2>Election Results</h2>
    <p class="subtitle">Final vote count for all candidates.</p>

    <div class="card" style="padding:0;">
        <table>
            <tr>
                <th>Candidate</th>
                <th>Party</th>
                <th>Votes</th>
            </tr>

            <%
                List<Candidate> results = (List<Candidate>) request.getAttribute("results");
                if (results != null) {
                    for (Candidate c : results) {
            %>

            <tr>
                <td><%= c.getName() %></td>
                <td><%= c.getParty() %></td>
                <td style="font-weight:bold; color:#1a3c6e;"><%= c.getVotes() %></td>
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
