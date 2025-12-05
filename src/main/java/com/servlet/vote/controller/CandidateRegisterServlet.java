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

@WebServlet("/CandidateRegisterServlet")
public class CandidateRegisterServlet extends HttpServlet {
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String party = req.getParameter("party");
        String manifesto = req.getParameter("manifesto");
        String password = req.getParameter("password");

        Candidate c = new Candidate();
        c.setName(name);
        c.setParty(party);
        c.setManifesto(manifesto);
        c.setApproved(false);
        c.setVotes(0);

        // Only storing password in DAO?  
        // If you want password for candidate login, add a "password" column in DB.
        // (already present in your model/DAO)
        ((Candidate) c).setPassword(password);

        CandidateDAO dao = new CandidateDAOImpl();

        if (dao.register(c)) {
            req.setAttribute("success", "Candidate Registered Successfully! Await admin approval.");
        } else {
            req.setAttribute("error", "Failed to Register Candidate");
        }

        req.getRequestDispatcher("CandidateRegister.jsp").forward(req, resp);
    }

}
