package com.servlet.vote.controller;

import java.io.IOException;
import java.util.List;

import com.servlet.vote.dao.CandidateDAO;
import com.servlet.vote.dao.CandidateDAOImpl;
import com.servlet.vote.dao.ElectionDAO;
import com.servlet.vote.dao.ElectionDAOImpl;
import com.servlet.vote.dto.Candidate;
import com.servlet.vote.dto.Election;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CandidateRegisterServlet")
public class CandidateRegisterServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    ElectionDAO edao = new ElectionDAOImpl();
	    List<Election> list = edao.getAll();
	    System.out.println(list);

	    req.setAttribute("elections", list);
	    req.getRequestDispatcher("CandidateRegister.jsp").forward(req, resp);
	}


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String party = req.getParameter("party");
        String manifesto = req.getParameter("manifesto");
        String password = req.getParameter("password");
        int electionId = Integer.parseInt(req.getParameter("electionId"));

        Candidate c = new Candidate();
        c.setName(name);
        c.setParty(party);
        c.setManifesto(manifesto);
        c.setApproved(false);
        c.setVotes(0);
        c.setPassword(password);
        c.setElectionId(electionId);

        CandidateDAO dao = new CandidateDAOImpl();

        if (dao.register(c)) {
            req.setAttribute("success", "Candidate Registered Successfully!");
        } else {
            req.setAttribute("error", "Failed to Register Candidate");
        }

        doGet(req, resp);
    }
}
