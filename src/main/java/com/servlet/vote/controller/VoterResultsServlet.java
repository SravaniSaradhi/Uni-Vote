package com.servlet.vote.controller;



import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.servlet.vote.dao.VoteDAO;
import com.servlet.vote.dao.VoterDAO;
import com.servlet.vote.dao.VoterDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/VoterResultsServlet")
public class VoterResultsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        VoterDAO voteDAO = new VoterDAOImpl();
        List<Map<String, Object>> results = voteDAO.getElectionResults();

        req.setAttribute("results", results);
        req.getRequestDispatcher("VoterResults.jsp")
           .forward(req, resp);
    }
}

