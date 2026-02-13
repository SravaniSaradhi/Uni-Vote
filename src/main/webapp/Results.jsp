<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
    <title>Election Results | Uni-Vote</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        .winner {
            background: #d4edda;
            font-weight: bold;
            color: #155724;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Election Results</h2>
    <p>Election-wise final vote count.</p>

<%
    Object obj = request.getAttribute("results");

    if (obj == null) {
%>
        <p style="color:red;">Results not available. Please open via Admin Dashboard.</p>
<%
    } else {
        List<Map<String, Object>> results =
            (List<Map<String, Object>>) obj;

        if (results.isEmpty()) {
%>
            <p>No elections found.</p>
<%
        } else {
            for (Map<String, Object> election : results) {

                String title = (String) election.get("title");
                List<Map<String, Object>> candidates =
                    (List<Map<String, Object>>) election.get("candidates");

                if (candidates == null || candidates.isEmpty()) {
                    continue;
                }
%>

    <div class="card" style="margin-top:25px;">
        <h3><%= title %></h3>

        <table>
            <tr>
                <th>Candidate</th>
                <th>Party</th>
                <th>Votes</th>
            </tr>

<%
                boolean isWinner = true;
                for (Map<String, Object> c : candidates) {
%>
            <tr class="<%= isWinner ? "winner" : "" %>">
                <td><%= c.get("name") %></td>
                <td><%= c.get("party") %></td>
                <td><%= c.get("votes") %></td>
            </tr>
<%
                    isWinner = false;
                }
%>
        </table>

        <p style="margin-top:10px; font-weight:600;">
            🏆 Winner: <%= candidates.get(0).get("name") %>
        </p>
    </div>

<%
            }
        }
    }
%>

</div>
</body>
</html>
