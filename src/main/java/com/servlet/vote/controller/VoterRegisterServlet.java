package com.servlet.vote.controller;

import java.io.IOException;

import com.servlet.vote.dao.VoterDAOImpl;
import com.servlet.vote.dao.VoterDAO;
import com.servlet.vote.dto.Voter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/VoterRegisterServlet")
public class VoterRegisterServlet extends HttpServlet {
	
	VoterDAO vdao=null;
	
	
	public VoterRegisterServlet() {
		
		vdao=new VoterDAOImpl();
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
       int id = 0;
       String name=req.getParameter("name");
       String email=req.getParameter("email");
       String password=req.getParameter("password");
       String phone=req.getParameter("phone"); 
       boolean approved=false;
       boolean hasVoted=false;
       Voter v=new Voter(id,  name,  email, password,  phone,  approved, hasVoted);
       boolean isRegistered=vdao.register(v);
       if(isRegistered) {
			req.getRequestDispatcher("VoterLogin.jsp")
			.forward(req,resp);;
		}
		else {
			req.getRequestDispatcher("VoterRegister.jsp")
			.forward(req,resp);;
		}
    }

}
