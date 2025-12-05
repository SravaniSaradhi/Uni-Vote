package com.servlet.vote.controller;

import java.io.IOException;

import com.servlet.vote.dao.ElectionDAO;
import com.servlet.vote.dao.ElectionDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EndElectionServlet")
public class EndElectionServlet extends HttpServlet{
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {

        int electionId = Integer.parseInt(req.getParameter("id"));

        ElectionDAO dao = new ElectionDAOImpl();
        dao.updateStatus(electionId, "ENDED");

        resp.sendRedirect("ManageElectionServlet");
    }

}
