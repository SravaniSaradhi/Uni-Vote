<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<%
    // read minimal session attributes (set by VoterLoginServlet)
    Integer voterId = (Integer) session.getAttribute("voterId");
    String voterName = (String) session.getAttribute("voterName");

    if (voterId == null) {
        response.sendRedirect("VoterLogin.jsp");
        return;
    }

    // These attributes are set by VoterDashboardServlet
    Boolean approvedObj = (Boolean) request.getAttribute("approved");
    Boolean hasVotedObj = (Boolean) request.getAttribute("hasVoted");

    boolean approved = (approvedObj != null) ? approvedObj.booleanValue() : false;
    boolean hasVoted = (hasVotedObj != null) ? hasVotedObj.booleanValue() : false;

    // elections data (List<Map<String,Object>>) passed from servlet
    List<Map<String, Object>> elections = (List<Map<String, Object>>) request.getAttribute("electionData");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Voter Dashboard | Uni-Vote</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="logo">Voter Panel</div>
    <div>
        <a href="VoterDashboardServlet">Dashboard</a>
        <a href="VoterLogoutServlet" class="btn-danger"
           style="padding:7px 12px; border-radius:6px; background:#dc3545; color:white; text-decoration:none;">
            Logout
        </a>
    </div>
</div>

<div class="container">

    <h2>Welcome, <%= (voterName != null ? voterName : "Voter") %></h2>

    <% if (!approved) { %>
        <p style="color:red; margin-bottom:15px; font-weight:600;">
            Your registration is pending admin approval.
        </p>
    <% } else { %>
        <p style="color:green; margin-bottom:15px; font-weight:600;">
            Your account is approved. You can vote.
        </p>
    <% } %>

    <% if (hasVoted) { %>
        <p style="color:green; margin-bottom:15px; font-weight:600;">
            You have already voted.
        </p>
    <% } %>

    <h3>Election Information</h3>

    <div class="card" style="padding:0; margin-top:20px;">
        <table>
            <tr>
                <th>Election</th>
                <th>Status</th>
                <th>Candidate</th>
                <th>Party</th>
                <th>Vote</th>
            </tr>

            <%
                if (elections != null && !elections.isEmpty()) {
                    for (Map<String, Object> e : elections) {
            %>

            <tr>
                <td><%= e.get("title") %></td>

                <td>
                    <span style="color:<%= "ONGOING".equals(String.valueOf(e.get("status"))) ? "green" : "gray" %>; font-weight:600;">
                        <%= e.get("status") %>
                    </span>
                </td>

                <td><%= e.get("candidateName") != null ? e.get("candidateName") : "-" %></td>
                <td><%= e.get("party") != null ? e.get("party") : "-" %></td>

                <td style="text-align:center;">
                    <% if (approved && !hasVoted && "ONGOING".equals(String.valueOf(e.get("status")))) { %>

                        <form action="VoteServlet" method="post" style="display:inline;">
                            <input type="hidden" name="candidateId" value="<%= e.get("candidateId") %>">
                            <button type="submit"
                                    style="background:#1a3c6e; color:white; padding:8px 12px;
                                           border:none; border-radius:6px;">
                                Vote
                            </button>
                        </form>

                    <% } else { %>
                        <i style="color:#777;">Not Available</i>
                    <% } %>
                </td>
            </tr>

            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="5" style="text-align:center; padding:20px;">
                        No Elections Available
                    </td>
                </tr>
            <% } %>

        </table>
    </div>

    <!-- System Messages -->
    <p style="color:blue; margin-top:15px;">
        <%= request.getAttribute("msg") != null ? request.getAttribute("msg") : "" %>
    </p>

</div>

</body>
</html>
