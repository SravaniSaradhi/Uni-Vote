package com.servlet.vote.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.servlet.vote.util.DbConnection;

public class VoteDAO {
	
	 public boolean recordVote(int voterId, int candidateId) {
	        String sql = "INSERT INTO votes(voterId, candidateId) VALUES (?,?)";

	        try {
	            Connection con = DbConnection.getConnector();
	            PreparedStatement ps = con.prepareStatement(sql);
	            ps.setInt(1, voterId);
	            ps.setInt(2, candidateId);

	            return ps.executeUpdate() > 0;

	        } catch (Exception e) { e.printStackTrace(); }
	        return false;
	    }

}
