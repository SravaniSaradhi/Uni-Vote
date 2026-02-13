package com.servlet.vote.controller;

import java.io.IOException;

import com.servlet.vote.dao.VoterDAO;
import com.servlet.vote.dao.VoterDAOImpl;
import com.servlet.vote.dto.Voter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/VoterLoginServlet")
public class VoterLoginServlet extends HttpServlet {

    private VoterDAO voterDAO = new VoterDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Voter voter = voterDAO.login(email);

        if (voter != null) {

            BCrypt.Result result =
                BCrypt.verifyer().verify(
                    password.toCharArray(),
                    voter.getPassword()
                );

            if (result.verified) {

                HttpSession session = req.getSession();
                session.setAttribute("voterId", voter.getId());
                session.setAttribute("voterName", voter.getName());
                session.setAttribute("voterUid", voter.getVoterUid());

                resp.sendRedirect("VoterDashboardServlet");
                return;
            }
        }

        req.setAttribute("msg", "Invalid Email or Password");
        req.getRequestDispatcher("VoterLogin.jsp").forward(req, resp);
    }
}
