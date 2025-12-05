package com.servlet.vote.dao;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.servlet.vote.dto.Election;
import com.servlet.vote.util.DbConnection;

public class ElectionDAOImpl implements ElectionDAO {

    @Override
    public boolean create(Election e) {
        String sql = "INSERT INTO election(title, description, status) VALUES(?,?,?)";
        try {
            Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, e.getTitle());
            ps.setString(2, e.getDescription());
            ps.setString(3, e.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception ex) { ex.printStackTrace(); }
        return false;
    }

    @Override
    public List<Election> getAll() {
        List<Election> list = new ArrayList<>();
        String sql = "SELECT * FROM election";

        try {
            Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Election e = new Election();
                e.setId(rs.getInt("id"));
                e.setTitle(rs.getString("title"));
                e.setDescription(rs.getString("description"));
                e.setStatus(rs.getString("status"));
                list.add(e);
            }
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    @Override
    public boolean updateStatus(int electionId, String status) {
        String sql = "UPDATE election SET status=? WHERE id=?";
        try {
            Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, electionId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // ------------------------ NEW METHODS ------------------------

    /** Get all elections currently ONGOING */
    public List<Election> getOngoingElections() {
        List<Election> list = new ArrayList<>();
        String sql = "SELECT * FROM election WHERE status='ONGOING'";

        try {
            Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Election e = new Election();
                e.setId(rs.getInt("id"));
                e.setTitle(rs.getString("title"));
                e.setDescription(rs.getString("description"));
                e.setStatus(rs.getString("status"));
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Get all candidates linked to ONGOING elections */
    public List<Map<String, Object>> getOngoingElectionCandidates() {
        List<Map<String, Object>> list = new ArrayList<>();

        String sql = "SELECT e.id AS electionId, e.title, e.status, " +
                     "c.id AS candidateId, c.name AS candidateName, c.party " +
                     "FROM election e " +
                     "LEFT JOIN candidate c ON c.election_id = e.id " +
                     "WHERE e.status='ONGOING'";

        try {
            Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();

                row.put("electionId", rs.getInt("electionId"));
                row.put("title", rs.getString("title"));
                row.put("status", rs.getString("status"));
                row.put("candidateId", rs.getInt("candidateId"));
                row.put("candidateName", rs.getString("candidateName"));
                row.put("party", rs.getString("party"));

                list.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
