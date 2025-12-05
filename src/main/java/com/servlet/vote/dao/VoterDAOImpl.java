package com.servlet.vote.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.servlet.vote.dto.Voter;
import com.servlet.vote.util.DbConnection;

public class VoterDAOImpl implements VoterDAO{

	@Override
	public boolean register(Voter v) {
		 String sql = "INSERT INTO voter(id,name, email, password, phone) VALUES (?,?,?,?,?)";
	        try {
	            Connection con = DbConnection.getConnector();
	            PreparedStatement ps = con.prepareStatement(sql);
	            ps.setInt(1,v.getId());
	            ps.setString(2, v.getName());
	            ps.setString(3, v.getEmail());
	            ps.setString(4, v.getPassword());
	            ps.setString(5, v.getPhone());
	            return ps.executeUpdate() > 0;
	        } catch (Exception e) { e.printStackTrace(); }
	        return false;
		
	}

	@Override
	public Voter login(String email, String Password) {
		
	        try {
	            String sql = "SELECT * FROM voter WHERE email=?";
	            Connection con = DbConnection.getConnector();
	            PreparedStatement ps = con.prepareStatement(sql);
	            ps.setString(1, email);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                Voter v = new Voter();
	                v.setId(rs.getInt("id"));
	                v.setName(rs.getString("name"));
	                v.setEmail(rs.getString("email"));
	                v.setPassword(rs.getString("password"));
	                v.setApproved(rs.getBoolean("approved"));
	                v.setHasVoted(rs.getBoolean("hasVoted"));
	                return v;
	            }
	        } catch (Exception e) {
	        	e.printStackTrace(); 
	        	}
	        return null;
	    
	}

	@Override
	public boolean approveVoter(int voterId) {
		String sql = "UPDATE voter SET approved=true WHERE id=?";
        try {
            Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, voterId);
            int r=ps.executeUpdate();
            if(r>0) {
            	return true;
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
        	}
        return false;
	}

	@Override
	public boolean markVoted(int voterId) {
		String sql = "UPDATE voter SET hasVoted=true WHERE id=?";
        try {
            Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, voterId);
            int r=ps.executeUpdate();
            if(r>0) {
            	return true;
            }

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
	
	@Override
	public boolean hasVoted(int voterId) {
	    String sql = "SELECT hasVoted FROM voter WHERE id=?";
	    try {
	        Connection con = DbConnection.getConnector();
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, voterId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getBoolean("hasVoted");
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return false;
	}

	}


