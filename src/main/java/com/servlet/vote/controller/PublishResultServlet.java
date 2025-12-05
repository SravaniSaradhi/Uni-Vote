package com.servlet.vote.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.servlet.vote.dto.Candidate;
import com.servlet.vote.util.DbConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PublishResultsServlet")
public class PublishResultServlet extends HttpServlet{

	 @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	        List<Candidate> results = new ArrayList<>();

	        try {
	            Connection con = DbConnection.getConnector();
	            PreparedStatement ps = con.prepareStatement("SELECT * FROM candidate ORDER BY votes DESC");
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                Candidate c = new Candidate();
	                c.setId(rs.getInt("id"));
	                c.setName(rs.getString("name"));
	                c.setParty(rs.getString("party"));
	                c.setVotes(rs.getInt("votes"));
	                results.add(c);
	            }

	        } catch (Exception e) { e.printStackTrace(); }

	        req.setAttribute("results", results);
	        req.getRequestDispatcher("Results.jsp").forward(req, resp);
	    }
}
