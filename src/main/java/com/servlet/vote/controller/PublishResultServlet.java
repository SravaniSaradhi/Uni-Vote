package com.servlet.vote.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.servlet.vote.dao.ResultDAO;
import com.servlet.vote.dao.ResultDAOImpl;
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
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {

	    	ResultDAO  dao = new ResultDAOImpl();
	        req.setAttribute("results", dao.getElectionWiseResults());

	        req.getRequestDispatcher("Results.jsp").forward(req, resp);
	    }
	

}
