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

    private VoteDAO voteDAO = new VoteDAO();
    private VoterDAO voterDAO = new VoterDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        // ✅ FIXED SESSION CHECK
        if (session == null || session.getAttribute("voterId") == null) {
            resp.sendRedirect("VoterLogin.jsp");
            return;
        }

        int voterId = (Integer) session.getAttribute("voterId");

        // Fetch full voter info
        Voter voter = voterDAO.getVoterById(voterId);

        int candidateId = Integer.parseInt(req.getParameter("candidateId"));

        String electionType = null;
        int electionId = 0;

        // Fetch election details
        try (Connection con = DbConnection.getConnector();
             PreparedStatement ps = con.prepareStatement(
                "SELECT e.id, e.type FROM election e JOIN candidate c ON c.election_id=e.id WHERE c.id=?")) {

            ps.setInt(1, candidateId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                electionId = rs.getInt("id");
                electionType = rs.getString("type");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 🔐 Prevent multiple votes per election
        if (voteDAO.hasVotedInElection(voterId, electionId)) {
            resp.sendRedirect("VoterDashboardServlet?msg=You have already voted in this election!");
            return;
        }

        // Age restriction (PUBLIC/YOUTH)
        if ("PUBLIC".equals(electionType) || "YOUTH".equals(electionType)) {
            LocalDate dob = LocalDate.parse(voter.getDob());
            int age = Period.between(dob, LocalDate.now()).getYears();

            if (age < 18) {
                resp.sendRedirect("VoterDashboardServlet?msg=You must be 18+ to vote in this election!");
                return;
            }
        }

        // 🎯 Cast Vote
        boolean voteSaved = voteDAO.recordVote(voterId, candidateId, electionId);

        if (voteSaved) {
            CandidateDAO cdao = new CandidateDAOImpl();
            cdao.addVote(candidateId);
            resp.sendRedirect("VoterDashboardServlet?msg=Vote recorded successfully!");
        } else {
            resp.sendRedirect("VoterDashboardServlet?msg=Failed to record vote!");
        }
    }
}
