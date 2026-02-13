<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<%
    List<Map<String,Object>> results =
        (List<Map<String,Object>>) request.getAttribute("results");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Election Results | Uni-Vote</title>
    <link rel="stylesheet" href="css/styles.css">

    <style>
        body { background:#f4f8fc; font-family: Arial, sans-serif; }

        .result-card {
            background: #fff;
            padding: 25px;
            border-radius: 12px;
            margin-bottom: 40px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.05);
        }

        .election-title {
            font-size: 26px;
            font-weight: 700;
            color: #1a3c6e;
            margin-bottom: 25px;
        }

        .candidate-name {
            font-size: 17px;
            font-weight: bold;
            margin-bottom: 6px;
            color: #222;
        }

        .bar-container {
            width: 100%;
            height: 38px;
            background: #E6E6E6;
            border-radius: 8px;
            margin: 12px 0 45px 0; /* extra spacing below */
            position: relative;
            overflow: hidden;
        }

        .bar-fill {
            height: 100%;
            border-radius: 8px;
        }

        .vote-count {
            position: absolute;
            right: 12px;
            top: 8px;
            font-size: 14px;
            font-weight: bold;
            color: #333;
        }

        .winner-badge {
            background: gold;
            color: black;
            font-size: 14px;
            font-weight: bold;
            padding: 6px 14px;
            border-radius: 20px;
            display: inline-block;
            margin-left: 12px;
        }

        .winner-bar {
            border: 3px solid gold;
        }

        /* Chart colors */
        .c1 { background: #FF8C42; }  /* Orange */
        .c2 { background: #0077B6; }  /* Blue */
        .c3 { background: #FFD166; }  /* Yellow */
        .c4 { background: #00A896; }  /* Teal */
        .c5 { background: #9B5DE5; }  /* Purple */
    </style>
</head>
<body>

<div class="navbar">
    <div class="logo">Voter Panel</div>
    <div>
        <a href="VoterDashboardServlet">Dashboard</a>
        <a href="VoterLogoutServlet" class="btn-danger">Logout</a>
    </div>
</div>

<div class="container">
    <h2 style="margin-bottom:30px;">Election Results</h2>

<%
if (results != null && !results.isEmpty()) {

    Map<Integer, List<Map<String,Object>>> grouped = new LinkedHashMap<>();
    String[] colors = {"c1", "c2", "c3", "c4", "c5"};

    for (Map<String,Object> row : results) {
        Integer eid = (Integer) row.get("electionId");
        grouped.putIfAbsent(eid, new ArrayList<>());
        grouped.get(eid).add(row);
    }

    for (Map.Entry<Integer, List<Map<String,Object>>> entry : grouped.entrySet()) {
        List<Map<String,Object>> rows = entry.getValue();
        Map<String,Object> winner = rows.get(0); // highest votes
%>

    <div class="result-card">

        <div class="election-title"><%= winner.get("title") %></div>

        <% int index = 0;
           for (Map<String,Object> r : rows) {
               Integer votes = (Integer) r.get("votes");
               boolean isWinner = r.get("candidateId").equals(winner.get("candidateId"));
               int totalVotes = (Integer) winner.get("votes"); // winner highest
               int width = (int)((votes / (double)totalVotes) * 100);
        %>

        <div class="candidate-name">
            <%= r.get("candidateName") %>  (<%= r.get("party") %>)
            <% if (isWinner) { %>
                <span class="winner-badge">WINNER 🏆</span>
            <% } %>
        </div>

        <div class="bar-container <%= isWinner ? "winner-bar" : "" %>">
            <div class="bar-fill <%= colors[index % colors.length] %>" style="width:<%= width %>%"></div>
            <span class="vote-count"><%= votes %> votes</span>
        </div>

        <% index++; } %>

    </div>

<%
    }
} else {
%>
    <p>No results available yet.</p>
<% } %>

</div>
</body>
</html>
