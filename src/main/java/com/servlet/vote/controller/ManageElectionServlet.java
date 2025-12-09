package com.servlet.vote.controller;

import jakarta.servlet.http.HttpServlet;
import com.servlet.vote.dao.ElectionDAO;
import com.servlet.vote.dao.ElectionDAOImpl;
import com.servlet.vote.dto.Election;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/ManageElectionServlet")
public class ManageElectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ElectionDAO dao = new ElectionDAOImpl();
        List<Election> list = dao.getAll();
        HttpSession session=req.getSession();
        
        req.setAttribute("elections", list);
        req.getRequestDispatcher("ManageElection.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String title = req.getParameter("title");
        String desc  = req.getParameter("description");
        String type  = req.getParameter("type"); // PUBLIC / YOUTH / COLLEGE / CLASS

        Election e = new Election();
        e.setTitle(title);
        e.setDescription(desc);
        e.setStatus("CREATED");
        e.setType(type);

        ElectionDAO dao = new ElectionDAOImpl();
        dao.create(e);

        resp.sendRedirect("ManageElectionServlet");
    }
}
