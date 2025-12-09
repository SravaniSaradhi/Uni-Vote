package com.servlet.vote.controller;

import java.io.IOException;

import at.favre.lib.crypto.bcrypt.BCrypt;
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

    private VoterDAO vdao = new VoterDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Voter v = vdao.login(email);

        if (v == null) {
            req.setAttribute("msg", "Email not registered");
            req.getRequestDispatcher("VoterLogin.jsp").forward(req, resp);
            return;
        }

        BCrypt.Result result = BCrypt.verifyer()
                                     .verify(password.toCharArray(), v.getPassword());

        if (!result.verified) {
            req.setAttribute("msg", "Invalid password");
            req.getRequestDispatcher("VoterLogin.jsp").forward(req, resp);
            return;
        }

        if (!v.isApproved()) {
            req.setAttribute("msg", "Your registration is pending admin approval");
            req.getRequestDispatcher("VoterLogin.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("voter", v);          // store full voter (includes dob, aadhar)
        session.setAttribute("voterId", v.getId());
        session.setAttribute("voterName", v.getName());

        resp.sendRedirect("VoterDashboardServlet");
    }
}
