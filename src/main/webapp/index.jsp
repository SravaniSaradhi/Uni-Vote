<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Uni-Vote | Secure Online Voting</title>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: "Segoe UI", Arial, sans-serif;
        }

        /* ---------- BACKGROUND ---------- */
        body {
            min-height: 100vh;
            background:
                linear-gradient(rgba(0,0,0,0.2), rgba(0,0,0,0.2)),
                url("assets/images/voting.jpg") no-repeat center center;
            background-size: cover;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        /* ---------- HEADER ---------- */
        .landing-header {
            margin-top: 30px;
            font-size: 36px;
            font-weight: bold;
            color: #ffffff;
            letter-spacing: 1px;
        }

        /* ---------- WRAPPER ---------- */
        .landing-wrapper {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            width: 100%;
        }

        /* ---------- MAIN CARD ---------- */
        .landing-card {
            background: rgba(255, 255, 255, 0.96);
            width: 950px;
            padding: 45px;
            border-radius: 22px;
            display: flex;
            gap: 40px;
            box-shadow: 0 25px 55px rgba(0,0,0,0.45);
        }

        /* ---------- OPTION CARDS ---------- */
        .option-card {
            flex: 1;
            background: #ffffff;
            padding: 35px;
            border-radius: 16px;
            text-align: center;
            box-shadow: 0 12px 30px rgba(0,0,0,0.15);
        }

        .option-card h2 {
            color: #1a3c6e;
            margin-bottom: 12px;
        }

        .option-card p {
            color: #555;
            font-size: 15px;
            margin-bottom: 25px;
            line-height: 1.6;
        }

        /* ---------- BUTTON GROUP ---------- */
        .btn-group {
            display: flex;
            flex-direction: column;
            gap: 14px;
        }

        /* ---------- BUTTONS ---------- */
        .btn {
            padding: 12px 36px;
            border-radius: 30px;
            text-decoration: none;
            font-weight: bold;
            color: #fff;
            transition: 0.3s ease;
            display: inline-block;
        }

        .btn-primary {
            background: #1a3c6e;
        }

        .btn-primary:hover {
            background: #102a4c;
        }

        .btn-secondary {
            background: #6c757d;
        }

        .btn-secondary:hover {
            background: #565e64;
        }

        .btn-danger {
            background: #dc3545;
        }

        .btn-danger:hover {
            background: #b52a37;
        }

        /* ---------- FOOTER ---------- */
        .landing-footer {
            margin-bottom: 20px;
            color: #e0e0e0;
            font-size: 14px;
        }

        /* ---------- RESPONSIVE ---------- */
        @media (max-width: 900px) {
            .landing-card {
                flex-direction: column;
                width: 90%;
            }
        }
        /* 🌟 PAGE ENTRANCE ANIMATION 🌟 */
.page-enter {
    opacity: 0;
    transform: translateY(-30px);
    animation: slideFadeIn 0.9s ease-out forwards;
}

@keyframes slideFadeIn {
    0% {
        opacity: 0;
        transform: translateY(-35px);
    }
    60% {
        opacity: 1;
        transform: translateY(3px);
    }
    100% {
        opacity: 1;
        transform: translateY(0);
    }
}

/* ✨ CARD FLOAT ANIMATION ✨ */
.landing-card {
    opacity: 0;
    transform: scale(0.95);
    animation: cardFloatIn 1.1s ease-out forwards 0.2s;
}

@keyframes cardFloatIn {
    0% {
        opacity: 0;
        transform: translateY(60px) scale(0.93);
    }
    100% {
        opacity: 1;
        transform: translateY(0) scale(1);
    }
}

/* 🔘 BUTTON INTERACTION */
.btn {
    transition: transform 0.2s ease, box-shadow 0.2s ease;
}
.btn:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 22px rgba(0,0,0,0.25);
}
.btn:active {
    transform: scale(0.95);
}
        
    </style>
</head>

<body class="page-enter">

    <!-- HEADER -->
    <div class="landing-header">
        🗳️ Uni-Vote
    </div>

    <!-- MAIN CONTENT -->
    <div class="landing-wrapper">
        <div class="landing-card">

            <!-- REGISTER SECTION -->
            <div class="option-card">
                <h2>New User?</h2>
                <p>
                    Register yourself to participate in secure,
                    transparent and verified digital elections.
                </p>

                <div class="btn-group">
                    <a href="VoterRegister.jsp" class="btn btn-primary">
                        Voter Register
                    </a>

                    <a href="CandidateRegisterServlet" class="btn btn-secondary">
                        Candidate Register
                    </a>
                </div>
            </div>

            <!-- LOGIN SECTION -->
            <div class="option-card">
                <h2>Already Registered?</h2>
                <p>
                    Login based on your role to access dashboards,
                    manage elections and cast votes securely.
                </p>

                <div class="btn-group">
                    <a href="VoterLogin.jsp" class="btn btn-primary">
                        Voter Login
                    </a>

                    <a href="CandidateLogin.jsp" class="btn btn-secondary">
                        Candidate Login
                    </a>

                    <a href="AdminLogin.jsp" class="btn btn-danger">
                        Admin Login
                    </a>
                </div>
            </div>

        </div>
    </div>

    <!-- FOOTER -->
    <div class="landing-footer">
        © 2025 Uni-Vote | Secure Online Voting System
    </div>

</body>
</html>
