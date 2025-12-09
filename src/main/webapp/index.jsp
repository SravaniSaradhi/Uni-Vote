<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>UniVote - Smart Voting System</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="logo">Uni-Vote</div>
    <div>
        <a href="AdminLogin.jsp">Admin Login</a>
        <a href="VoterLogin.jsp">Voter Login</a>
        <a href="CandidateLogin.jsp">Candidate Login</a>
    </div>
</div>

<!-- MAIN HOME SECTION -->
<div class="container" style="text-align:center;">

    <h1 style="color:#1a3c6e; margin-bottom:10px;">Welcome to UniVote</h1>
    <p>Your secure and smart online voting platform</p>

    <br><br>

    <!-- LOGIN SECTION -->
    <div class="card" style="padding:30px;">
        <h2>Select Login Module</h2>

        <div style="margin-top:20px; display:flex; flex-direction:column; gap:15px; align-items:center;">
            <a href="AdminLogin.jsp"><button class="btn-danger">Admin Login</button></a>
            <a href="VoterLogin.jsp"><button>Voter Login</button></a>
            <a href="CandidateLogin.jsp"><button class="btn-secondary">Candidate Login</button></a>
        </div>
    </div>

    <br>

    <!-- REGISTRATION SECTION -->
    <div class="card" style="padding:30px;">
        <h2>New Registration</h2>

        <div style="margin-top:20px; display:flex; flex-direction:column; gap:15px; align-items:center;">
            <a href="VoterRegister.jsp"><button>Voter Registration</button></a>
            <a href="CandidateRegisterServlet"><button class="btn-secondary">Candidate Registration</button></a>
        </div>
    </div>

</div>

</body>
</html>
