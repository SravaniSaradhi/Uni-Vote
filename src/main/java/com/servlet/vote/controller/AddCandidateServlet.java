package com.servlet.vote.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.servlet.vote.util.DbConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddCandidateServlet")
public class AddCandidateServlet extends HttpServlet {
	
	 @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	        int electionId = Integer.parseInt(req.getParameter("electionId"));
	        int candidateId = Integer.parseInt(req.getParameter("candidateId"));

	        try {
	            Connection con = DbConnection.getConnector();
	            PreparedStatement ps = con.prepareStatement(
	                "INSERT INTO election_candidates(election_id, candidate_id) VALUES (?,?)"
	            );

	            ps.setInt(1, electionId);
	            ps.setInt(2, candidateId);
	            ps.executeUpdate();

	            req.setAttribute("msg", "Candidate Added to Election Successfully");

	        } catch (Exception e) {
	            e.printStackTrace();
	            req.setAttribute("msg", "Error adding candidate to election");
	        }

	        req.getRequestDispatcher("/admin/manageElections.jsp").forward(req, resp);
	    }

}
