package com.servlet.vote.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.servlet.vote.dao.ElectionDAO;
import com.servlet.vote.dao.ElectionDAOImpl;
import com.servlet.vote.util.DbConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/VoterDashboardServlet")
public class VoterDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        // Ensure voter is logged in
        if (session == null || session.getAttribute("voterId") == null) {
            resp.sendRedirect("VoterLogin.jsp");
            return;
        }

        int voterId = (Integer) session.getAttribute("voterId");
        String voterName = (String) session.getAttribute("voterName");

        boolean approved = false;
        boolean hasVoted = false;

        // Fetch updated voter status from DB
        try (Connection con = DbConnection.getConnector();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT approved, hasVoted FROM voter WHERE id=?")) {

            ps.setInt(1, voterId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                approved = rs.getBoolean("approved");
                hasVoted = rs.getBoolean("hasVoted");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fetch ONGOING elections + candidates
        ElectionDAO edao = new ElectionDAOImpl();
        List<Map<String, Object>> electionData = edao.getOngoingElectionCandidates();

        req.setAttribute("approved", approved);
        req.setAttribute("hasVoted", hasVoted);
        req.setAttribute("electionData", electionData);
        req.setAttribute("voterName", voterName);

        req.getRequestDispatcher("VoterDashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
