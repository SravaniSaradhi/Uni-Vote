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

@WebServlet("/VoterRegisterServlet")
public class VoterRegisterServlet extends HttpServlet {

    private VoterDAO vdao = new VoterDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name   = req.getParameter("name");
        String email  = req.getParameter("email");
        String aadhar = req.getParameter("aadhar");
        String dob    = req.getParameter("dob");   // yyyy-MM-dd
        String phone  = req.getParameter("phone");
        String password = req.getParameter("password");

        // 1) Email format validation
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            req.setAttribute("error", "Invalid Email Format!");
            req.getRequestDispatcher("VoterRegister.jsp").forward(req, resp);
            return;
        }

        // 2) Aadhar validation: exactly 12 digits
        if (!aadhar.matches("^[0-9]{12}$")) {
            req.setAttribute("error", "Aadhar must be exactly 12 digits!");
            req.getRequestDispatcher("VoterRegister.jsp").forward(req, resp);
            return;
        }

        // 3) Email already used?
        if (vdao.emailExists(email)) {
            req.setAttribute("error", "Email already registered!");
            req.getRequestDispatcher("VoterRegister.jsp").forward(req, resp);
            return;
        }

        // 4) Aadhar already used?
        if (vdao.aadharExists(aadhar)) {
            req.setAttribute("error", "Aadhar already registered!");
            req.getRequestDispatcher("VoterRegister.jsp").forward(req, resp);
            return;
        }

        // 5) Hash password with BCrypt
        String hashed = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        Voter v = new Voter();
        v.setName(name);
        v.setEmail(email);
        v.setPhone(phone);
        v.setAadhar(aadhar);
        v.setDob(dob);
        v.setPassword(hashed);
        v.setApproved(false);
        v.setHasVoted(false);

        boolean ok = vdao.register(v);

        if (ok) {
            req.setAttribute("success", "Registration successful! Wait for admin approval.");
            req.getRequestDispatcher("VoterLogin.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Registration failed. Try again.");
            req.getRequestDispatcher("VoterRegister.jsp").forward(req, resp);
        }
    }
}
