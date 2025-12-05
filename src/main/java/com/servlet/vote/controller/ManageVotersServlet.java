 package com.servlet.vote.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.servlet.vote.dao.VoterDAO;
import com.servlet.vote.dao.VoterDAOImpl;
import com.servlet.vote.dto.Voter;
import com.servlet.vote.util.DbConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ManageVoterServlet")
public class ManageVotersServlet extends HttpServlet {
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Voter> voters = new ArrayList<>();

        try {
            Connection con =DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM voter");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Voter v = new Voter();
                v.setId(rs.getInt("id"));
                v.setName(rs.getString("name"));
                v.setEmail(rs.getString("email"));
                v.setPhone(rs.getString("phone"));
                v.setApproved(rs.getBoolean("approved"));
                voters.add(v);
            }

        } catch (Exception e) { e.printStackTrace(); }

        req.setAttribute("voters", voters);
        req.getRequestDispatcher("ManageVoters.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int voterId = Integer.parseInt(req.getParameter("voterId"));
        String action = req.getParameter("action");

        VoterDAO dao = new VoterDAOImpl();
        
        if ("approve".equals(action)) {
            dao.approveVoter(voterId);
           
        } 
        else if ("delete".equals(action)) {
            try {
                Connection con = DbConnection.getConnector();
                PreparedStatement ps = con.prepareStatement("DELETE FROM voter WHERE id=?");
                ps.setInt(1, voterId);
                ps.executeUpdate();
            } catch (Exception e) { e.printStackTrace(); }
        }

        resp.sendRedirect("ManageVoterServlet");
    }

}
