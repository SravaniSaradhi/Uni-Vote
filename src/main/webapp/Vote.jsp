<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cast Your Vote | Uni-Vote</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<!-- Navigation Bar -->
<div class="navbar">
    <div class="logo">Voter Panel</div>
    <div>
        <a href="VoterDashboard.jsp">Dashboard</a>
        <a href="VoterLogoutServlet" class="btn-danger"
           style="padding:7px 12px; border-radius:6px; background:#dc3545; color:white; text-decoration:none;">
            Logout
        </a>
    </div>
</div>

<!-- Center Vote Box -->
<div class="center-box">
    <h2>Confirm Your Vote</h2>

    <p style="margin-bottom:20px; font-size:16px;">
        Are you sure you want to vote for this candidate?
    </p>

    <form action="VoteServlet" method="post">

        <!-- Candidate ID (Hidden) -->
        <input type="hidden" name="candidateId" value="<%= request.getParameter("candidateId") %>">

        <input type="submit" value="Yes, Vote">

        <br><br>

        <a href="VoterDashboard.jsp">
            <button type="button" class="btn-secondary">Cancel</button>
        </a>
    </form>
</div>

</body>
</html>
