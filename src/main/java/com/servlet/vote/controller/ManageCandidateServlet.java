package com.servlet.vote.controller;

import java.util.ArrayList;
import java.util.List;

import com.servlet.vote.dao.CandidateDAO;
import com.servlet.vote.dao.CandidateDAOImpl;
import com.servlet.vote.dto.Candidate;
import com.servlet.vote.util.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ManageCandidateServlet")
public class ManageCandidateServlet extends HttpServlet {
	
	 @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	        List<Candidate> list = new ArrayList<>();

	        try {
	            Connection con = DbConnection.getConnector();
	            PreparedStatement ps = con.prepareStatement("SELECT * FROM candidate");
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                Candidate c = new Candidate();
	                c.setId(rs.getInt("id"));
	                c.setName(rs.getString("name"));
	                c.setParty(rs.getString("party"));
	                c.setManifesto(rs.getString("manifesto"));
	                c.setApproved(rs.getBoolean("approved"));
	                list.add(c);
	            }

	        } catch (Exception e) { e.printStackTrace(); }

	        req.setAttribute("candidates", list);
	        req.getRequestDispatcher("ManageCandidates.jsp").forward(req, resp);
	    }

	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	        int candidateId = Integer.parseInt(req.getParameter("candidateId"));
	        String action = req.getParameter("action");

	        CandidateDAO dao = new CandidateDAOImpl();

	        if ("approve".equals(action)) {
	            dao.approve(candidateId);
	        } else if ("delete".equals(action)) {
	            try {
	                Connection con =  DbConnection.getConnector();
	                PreparedStatement ps = con.prepareStatement("DELETE FROM candidate WHERE id=?");
	                ps.setInt(1, candidateId);
	                ps.executeUpdate();
	            } catch (Exception e) { e.printStackTrace(); }
	        }

	        resp.sendRedirect("ManageCandidateServlet");
	    }

}
