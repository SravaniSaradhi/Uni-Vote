package com.servlet.vote.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.sql.*;

import com.servlet.vote.dao.VoteDAO;
import com.servlet.vote.dao.CandidateDAOImpl;
import com.servlet.vote.dao.CandidateDAO;
import com.servlet.vote.dao.VoterDAOImpl;
import com.servlet.vote.dao.VoterDAO;
import com.servlet.vote.dto.Voter;
import com.servlet.vote.util.DbConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/VoteServlet")
public class VoteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("voter") == null) {
            resp.sendRedirect("VoterLogin.jsp");
            return;
        }

        Voter voter = (Voter) session.getAttribute("voter");

        int candidateId = Integer.parseInt(req.getParameter("candidateId"));

        String electionType = null;
        int electionId = 0;

        // find election by candidate
        try(Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(
                "SELECT e.id, e.type FROM election e JOIN candidate c ON c.election_id=e.id WHERE c.id=?"
            )) {

            ps.setInt(1, candidateId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                electionId = rs.getInt("id");
                electionType = rs.getString("type");
            }

        }catch(Exception e){ e.printStackTrace(); }


        VoteDAO voteDAO = new VoteDAO();

        // 🔥 KEY PART: per-election check
        if(voteDAO.hasVotedInElection(voter.getId(), electionId)){
            req.setAttribute("msg", "You already voted in this election!");
            req.getRequestDispatcher("VoterDashboardServlet").forward(req, resp);
            return;
        }

        // =============================
        // Age check only for PUBLIC/YOUTH
        // =============================
        if(electionType.equals("PUBLIC") || electionType.equals("YOUTH"))
        {
            String dobStr = voter.getDob();
            LocalDate dob = LocalDate.parse(dobStr);
            int age = Period.between(dob, LocalDate.now()).getYears();

            if(age < 18){
                req.setAttribute("msg","You must be 18+ for this election");
                req.getRequestDispatcher("VoterDashboardServlet").forward(req, resp);
                return;
            }
        }

        boolean ok = voteDAO.recordVote(voter.getId(), candidateId, electionId);

        if(ok){
            CandidateDAO cdao = new CandidateDAOImpl();
            cdao.addVote(candidateId);

            req.setAttribute("msg","Vote recorded!");
        }else{
            req.setAttribute("msg","Vote failed!");
        }

        req.getRequestDispatcher("VoterDashboardServlet").forward(req, resp);
    }
}
