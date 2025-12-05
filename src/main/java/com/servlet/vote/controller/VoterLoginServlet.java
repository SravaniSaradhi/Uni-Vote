package com.servlet.vote.controller;

import java.io.IOException;

import com.servlet.vote.dao.VoterDAO;
import com.servlet.vote.dao.VoterDAOImpl;
import com.servlet.vote.dto.Voter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/VoterLoginServlet")
public class VoterLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VoterDAO vdao = null;

    public VoterLoginServlet() {
        vdao = new VoterDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Voter v = vdao.login(email, password);

        if (v != null) {
            HttpSession session = req.getSession();
            // Store minimal, stable info in session
            session.setAttribute("voterId", v.getId());
            session.setAttribute("voterName", v.getName());
            // redirect to servlet that will fetch fresh audit fields from DB
            resp.sendRedirect("VoterDashboardServlet");
        } else {
            // invalid login — forward back so request attribute is preserved
            req.setAttribute("msg", "Invalid Email or Password");
            req.getRequestDispatcher("VoterLogin.jsp").forward(req, resp);
        }
    }
}
