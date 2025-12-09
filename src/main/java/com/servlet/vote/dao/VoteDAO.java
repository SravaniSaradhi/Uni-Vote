package com.servlet.vote.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.servlet.vote.util.DbConnection;

public class VoteDAO {

    public boolean recordVote(int voterId, int candidateId, int electionId) {
        String sql = "INSERT INTO votes(voterId, candidateId, election_id) VALUES (?,?,?)";
        try(Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, voterId);
            ps.setInt(2, candidateId);
            ps.setInt(3, electionId);

            return ps.executeUpdate() > 0;

        }catch(Exception e){ e.printStackTrace(); }
        return false;
    }

    public boolean hasVotedInElection(int voterId, int electionId){
        String sql = "SELECT id FROM votes WHERE voterId=? AND election_id=?";
        try(Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setInt(1, voterId);
            ps.setInt(2, electionId);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
