package com.servlet.vote.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servlet.vote.util.DbConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ElectionStatusServlet")
public class ElectionStatusServlet extends HttpServlet {
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Map<String, Object>> list = new ArrayList<>();

        try {
            Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(
                "SELECT e.id, e.title, e.status, c.name AS candidateName, c.party " +
                "FROM election e " +
                "JOIN election_candidates ec ON e.id = ec.election_id " +
                "JOIN candidate c ON c.id = ec.candidate_id"
            );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();

                map.put("electionId", rs.getInt("id"));
                map.put("title", rs.getString("title"));
                map.put("status", rs.getString("status"));
                map.put("candidateName", rs.getString("candidateName"));
                map.put("party", rs.getString("party"));

                list.add(map);
            }

        } catch (Exception e) { e.printStackTrace(); }

        req.setAttribute("electionData", list);
        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }

}
