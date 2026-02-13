<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.servlet.vote.dao.VoteDAO"%>

<%
    Integer voterId = (Integer) session.getAttribute("voterId");
    String voterName = (String) session.getAttribute("voterName");

    if (voterId == null) {
        response.sendRedirect("VoterLogin.jsp");
        return;
    }

    Boolean approvedObj = (Boolean) request.getAttribute("approved");
    boolean approved = (approvedObj != null) ? approvedObj : false;

    // list from servlet
    List<Map<String,Object>> elections = 
        (List<Map<String,Object>>)request.getAttribute("electionData");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Voter Dashboard | Uni-Vote</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<!-------------- NAVBAR ----------------->
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

   <div class="container">

    <!-- HEADER ROW -->
    <div style="
        display:flex;
        justify-content:space-between;
        align-items:center;
        margin-bottom:20px;
    ">
        <h2 style="margin:0;">
            Welcome, <%= voterName %>
        </h2>

        <a href="DownloadVoterID"
           style="
               padding:10px 18px;
               background:#1a3c6e;
               color:white;
               border-radius:6px;
               text-decoration:none;
               font-weight:600;
           ">
           ⬇ Download Voter ID
        </a>
        <a href="VoterResultsServlet"
   style="margin-left:15px;
          padding:10px 18px;
          background:#198754;
          color:white;
          border-radius:6px;
          text-decoration:none;">
   View Results
</a>
        
    </div>

    <% if (!approved) { %>
        <p style="color:red; font-weight:600;">
            Your registration is pending admin approval.
        </p>
    <% } else { %>
        <p style="color:green; font-weight:600;">
            Account approved ✔ You can vote!
        </p>
    <% } %>

   
   
    
    
	   
    <h3 style="margin-top:30px;">Available Elections</h3>

    <%
        if(elections != null && !elections.isEmpty()){
            
            // group candidates by election
            Map<Integer, List<Map<String,Object>>> grouped = new HashMap<>();

            for(Map<String,Object> row : elections){
                Integer eid = (Integer)row.get("electionId");

                grouped.putIfAbsent(eid, new ArrayList<>());
                grouped.get(eid).add(row); 
            }

            VoteDAO voteDAO = new VoteDAO();

            for(Map.Entry<Integer, List<Map<String,Object>>> entry : grouped.entrySet()){
                List<Map<String,Object>> rows = entry.getValue();
                Map<String,Object> first = rows.get(0);

                int electionId = (Integer)first.get("electionId");
                String title    = String.valueOf(first.get("title"));
                String status   = String.valueOf(first.get("status"));
                String type     = String.valueOf(first.get("type"));

                boolean alreadyVoted = voteDAO.hasVotedInElection(voterId, electionId);
    %>

    <!------------ Election Card ---------------->
    <div style="margin-top:25px;
                border:1px solid #ccc;
                border-radius:8px;
                padding:15px;">

        <h3><%= title %></h3>
        <p>
            Status:
            <span style="color:<%= ("ONGOING".equals(status) ? "green" : "gray") %>; font-weight:600;">
                <%= status %>
            </span>
        </p>
        <p> Type: <%= type %> </p>

        <table style="width:100%;">
            <tr>
                <th>Candidate</th>
                <th>Party</th>
                <th>Action</th>
            </tr>

            <% 
                for(Map<String,Object> row : rows){
                    Integer cid = (Integer)row.get("candidateId");
                    String cname = (String)row.get("candidateName");
                    String party = (String)row.get("party");

                    if(cid == null){
            %>
            <tr><td colspan="3"><i>No candidates registered yet</i></td></tr>
            <%
                    }else{
            %>
            <tr>
                <td><%= cname %></td>
                <td><%= party %></td>
                <td style="text-align:center;">

                    <% if(!approved){ %>
                        <i style="color:red;">Not approved yet</i>

                    <% } else if(!"ONGOING".equals(status)){ %>
                        <i style="color:gray;">Not Active</i>

                    <% } else if(alreadyVoted){ %>
                        <i style="color:red;">Already Voted</i>

                    <% } else { %>
                        <form action="VoteServlet" method="post" style="display:inline;">
                            <input type="hidden" name="candidateId" value="<%= cid %>">
                            <button type="submit"
                                    style="background:#1a3c6e; color:white; padding:8px 12px;
                                           border:none; border-radius:6px;">
                                Vote
                            </button>
                        </form>
                    <% } %>

                </td>
            </tr>
            <%  } // if candidate %>

            <% } // loop rows %>

        </table>

    </div> <!-- election card -->

    <%  
            } // group loop

        }else{ 
    %>

        <p style="margin-top:30px;">No elections available</p>

    <% } %>


    <!------ system messages ----->
    <p style="color:blue; margin-top:20px;">
        <%= (request.getAttribute("msg")!=null?request.getAttribute("msg"):"") %>
    </p>

</div>

</body>
</html>
