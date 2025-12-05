package com.servlet.vote.dao;

import com.servlet.vote.dto.Admin;
import com.servlet.vote.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAOimpl implements AdminDAO{

	@Override
	public boolean register(Admin a) {
		String sql = "INSERT INTO admin(username, email, password) VALUES(?,?,?)";
        try {
            Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, a.getUsername());
            ps.setString(2, a.getEmail());
            ps.setString(3, a.getPassword());

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
	}

	@Override
	public Admin login(String email, String password) {
		 String sql = "SELECT * FROM admin WHERE email=?";
	        try {
	            Connection con = DbConnection.getConnector();
	            PreparedStatement ps = con.prepareStatement(sql);
	            ps.setString(1, email);
	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                Admin a = new Admin();
	                a.setId(rs.getInt("id"));
	                a.setUsername(rs.getString("username"));
	                a.setEmail(rs.getString("email"));
	                a.setPassword(rs.getString("password"));
	                return a;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	}

}
