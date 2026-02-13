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

@WebServlet("/CandidateLoginServlet")
public class CandidateLoginServlet extends HttpServlet{
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");   // login using email
        String password = req.getParameter("password");
        String name=req.getParameter("name");

        CandidateDAO dao = new CandidateDAOImpl ();
        Candidate c = dao.login(name,password);

        if (c == null) {
            req.setAttribute("msg", "Invalid Email or Password");
            req.getRequestDispatcher("CandidateLogin.jsp").forward(req, resp);
            return;
        }

        if (!c.isApproved()) {
            req.setAttribute("msg", "Your profile is pending Admin approval");
            req.getRequestDispatcher("CandidateLogin.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("candidate", c);

        resp.sendRedirect("CandidateDashboard.jsp");
    }

}
