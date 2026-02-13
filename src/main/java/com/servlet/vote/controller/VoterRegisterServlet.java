package com.servlet.vote.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import at.favre.lib.crypto.bcrypt.BCrypt;

import com.servlet.vote.dao.VoterDAO;
import com.servlet.vote.dao.VoterDAOImpl;
import com.servlet.vote.dto.Voter;
import com.servlet.vote.util.EmailUtil;
import com.servlet.vote.util.OTPUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/VoterRegisterServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class VoterRegisterServlet extends HttpServlet {

    private VoterDAO voterDAO = new VoterDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name").trim();
        String email = req.getParameter("email").trim();
        String password = req.getParameter("password");
        String phone = req.getParameter("phone").trim();
        String aadhar = req.getParameter("aadhar").trim();
        String dob = req.getParameter("dob");

        // 🔍 Email already exists validation
        if (voterDAO.emailExists(email)) {
            req.setAttribute("error", "Email already registered! Please login or use another email.");
            req.getRequestDispatcher("VoterRegister.jsp").forward(req, resp);
            return;
        }

        // 🔍 Aadhaar already exists validation
        if (voterDAO.aadharExists(aadhar)) {
            req.setAttribute("error", "Aadhaar number already exists in database!");
            req.getRequestDispatcher("VoterRegister.jsp").forward(req, resp);
            return;
        }

        // 📌 PHOTO UPLOAD
        Part photoPart = req.getPart("photo");
        String fileName = null;

        if (photoPart != null && photoPart.getSize() > 0 && photoPart.getSubmittedFileName() != null) {
            String original = photoPart.getSubmittedFileName();

            if (original.contains(".")) {
                String ext = original.substring(original.lastIndexOf(".")).toLowerCase();
                fileName = "photo_" + System.currentTimeMillis() + ext;

                String uploadDir = "C:/UniVoteUploads/";
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();

                Files.copy(photoPart.getInputStream(), Paths.get(uploadDir, fileName));
                System.out.println("PHOTO SAVED: " + fileName);
            }
        } else {
            req.setAttribute("error", "Photo upload is required!");
            req.getRequestDispatcher("VoterRegister.jsp").forward(req, resp);
            return;
        }

        // 📧 Send OTP
        String otp = OTPUtil.generateOTP();
        EmailUtil.sendOTP(email, otp);

        // 🔐 Password Hashing
        String hashedPwd = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        // 🧠 Store pending voter in session
        Voter voter = new Voter();
        voter.setName(name);
        voter.setEmail(email);
        voter.setPassword(hashedPwd);
        voter.setPhone(phone);
        voter.setAadhar(aadhar);
        voter.setDob(dob);
        voter.setPhoto(fileName);

        HttpSession session = req.getSession();
        session.setAttribute("otp", otp);
        session.setAttribute("pendingVoter", voter);

        System.out.println("📌 STORED IN SESSION → VOTER: " + voter.getPhoto());

        resp.sendRedirect("VerifyOTP.jsp");
    }
}
