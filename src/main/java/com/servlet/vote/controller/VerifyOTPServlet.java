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

@WebServlet("/VerifyOTPServlet")
public class VerifyOTPServlet extends HttpServlet {

    private VoterDAO voterDAO = new VoterDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("VoterRegister.jsp");
            return;
        }

        String enteredOtp = req.getParameter("otp");
        String storedOtp = (String) session.getAttribute("otp");
        Voter voter = (Voter) session.getAttribute("pendingVoter");

        if (storedOtp == null || voter == null) {
            req.setAttribute("error", "Session expired! Register again.");
            req.getRequestDispatcher("VoterRegister.jsp").forward(req, resp);
            return;
        }

        if (enteredOtp.equals(storedOtp)) {

            // Debug — print photo value
            System.out.println("📌 Photo in voter object before insert: " + voter.getPhoto());

            if (voter.getPhoto() == null || voter.getPhoto().trim().isEmpty()) {
                System.err.println("❌ ERROR: Photo missing in voter object");
            }

            boolean isSaved = voterDAO.register(voter);

            if (isSaved) {
                System.out.println("✔ Voter saved in DB successfully with photo: " + voter.getPhoto());
            } else {
                System.err.println("❌ DB save failed!");
            }

            // Clear OTP session
            session.removeAttribute("otp");
            session.removeAttribute("pendingVoter");

            req.setAttribute("success", "Email verified successfully! Please login.");
            req.getRequestDispatcher("VoterLogin.jsp").forward(req, resp);

        } else {
            req.setAttribute("error", "Invalid OTP! Please try again.");
            req.getRequestDispatcher("VerifyOTP.jsp").forward(req, resp);
        }
    }
}
