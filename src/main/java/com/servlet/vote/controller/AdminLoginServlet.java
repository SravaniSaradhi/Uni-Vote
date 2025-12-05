package com.servlet.vote.controller;

import java.io.IOException;

import com.servlet.vote.dao.AdminDAO;
import com.servlet.vote.dao.AdminDAOimpl;
import com.servlet.vote.dto.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
	
	 protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	        String email = req.getParameter("email");
	        String password = req.getParameter("password");

	        AdminDAO dao = new AdminDAOimpl();
	        Admin admin = dao.login(email, password);

	        if (admin == null ) {
	            req.setAttribute("msg", "Invalid admin credentials");
	            req.getRequestDispatcher("AdminLogin.jsp").forward(req, resp);
	            return;
	        }

	        HttpSession session = req.getSession();
	        session.setAttribute("admin", admin);

	        resp.sendRedirect("AdminDashboard.jsp");
	    }

}
