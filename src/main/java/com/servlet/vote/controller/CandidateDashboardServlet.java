package com.servlet.vote.controller;

import java.io.IOException;

import com.servlet.vote.dao.CandidateDAO;
import com.servlet.vote.dao.CandidateDAOImpl;
import com.servlet.vote.dto.Candidate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CandidateDashboardServlet")
public class CandidateDashboardServlet extends HttpServlet {
	
	 @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {

	        HttpSession session = req.getSession(false);

	        if (session == null || session.getAttribute("candidate") == null) {
	            resp.sendRedirect(req.getContextPath() + "/candidate/login.jsp");
	            return;
	        }

	        Candidate c = (Candidate) session.getAttribute("candidate");

	        // If you want live vote count refresh
	        CandidateDAO dao = new CandidateDAOImpl();
	        Candidate updated = dao.login(c.getName(),c.getPassword());

	        req.setAttribute("candidate", updated);

	        req.getRequestDispatcher("CandidateDashboard.jsp").forward(req, resp);
	    }
	

}
