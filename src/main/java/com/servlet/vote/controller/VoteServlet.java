package com.servlet.vote.controller;

import java.io.IOException;

import com.servlet.vote.dao.CandidateDAO;
import com.servlet.vote.dao.CandidateDAOImpl;
import com.servlet.vote.dao.VoteDAO;
import com.servlet.vote.dao.VoterDAO;
import com.servlet.vote.dao.VoterDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/VoteServlet")
public class VoteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        // Check login
        if (session == null || session.getAttribute("voterId") == null) {
            resp.sendRedirect("VoterLogin.jsp");
            return;
        }

        int voterId = (int) session.getAttribute("voterId");

        // Fetch hasVoted from DB to avoid outdated session data
        VoterDAO voterDAO = new VoterDAOImpl();
        boolean hasVoted = voterDAO.hasVoted(voterId);   // <-- you must add this method in DAO

        if (hasVoted) {
            req.setAttribute("msg", "You have already voted!");
            req.getRequestDispatcher("VoterDashboardServlet").forward(req, resp);
            return;
        }

        int candidateId = Integer.parseInt(req.getParameter("candidateId"));

        VoteDAO voteDAO = new VoteDAO();
        CandidateDAO candidateDAO = new CandidateDAOImpl();

        boolean voteInserted = voteDAO.recordVote(voterId, candidateId);

        if (voteInserted) {
            candidateDAO.addVote(candidateId);
            voterDAO.markVoted(voterId);

            req.setAttribute("msg", "Vote Cast Successfully!");
        } else {
            req.setAttribute("msg", "Failed to cast vote. Try again!");
        }

        req.getRequestDispatcher("VoterDashboardServlet").forward(req, resp);
    }
}
