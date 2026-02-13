package com.servlet.vote.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.servlet.vote.dto.Candidate;
import com.servlet.vote.util.DbConnection;

public class CandidateDAOImpl implements CandidateDAO {

    @Override
    public boolean register(Candidate c) {
        String sql = "INSERT INTO candidate(name, party, password, manifesto, approved, votes, election_id) "
                   + "VALUES(?,?,?,?,?,?,?)";

        try {
            Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getName());
            ps.setString(2, c.getParty());
            ps.setString(3, c.getPassword());  
            ps.setString(4, c.getManifesto());// NEW: save password
            ps.setBoolean(5, c.isApproved());       // false initially
            ps.setInt(6, c.getVotes());             // normally 0
            ps.setInt(7, c.getElectionId());        // NEW: link to election

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Candidate login(String name ,String password) {
        String sql = "SELECT * FROM candidate WHERE name=? and password=?";
        try {
            Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2,password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                Candidate c = new Candidate();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setParty(rs.getString("party"));
                c.setManifesto(rs.getString("manifesto"));
                c.setApproved(rs.getBoolean("approved"));
                c.setVotes(rs.getInt("votes"));
                c.setPassword(rs.getString("password"));     // NEW
                c.setElectionId(rs.getInt("election_id"));   // NEW

                return c;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public boolean approve(int id) {
        String sql = "UPDATE candidate SET approved=true WHERE id=?";
        try {
            Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean addVote(int candidateId) {
        String sql = "UPDATE candidate SET votes = votes + 1 WHERE id=?";
        try {
            Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, candidateId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
