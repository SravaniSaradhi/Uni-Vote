package com.servlet.vote.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servlet.vote.dto.Voter;
import com.servlet.vote.util.DbConnection;

public class VoterDAOImpl implements VoterDAO {

    // ===================== REGISTER (AFTER OTP VERIFICATION) =====================
    @Override
    public boolean register(Voter v) {

        String insertSql =
            "INSERT INTO voter(name, email, password, phone, aadhar, dob, approved, hasVoted, photo) " +
            "VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection con = DbConnection.getConnector();
             PreparedStatement ps =
                     con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, v.getName());
            ps.setString(2, v.getEmail());
            ps.setString(3, v.getPassword()); // already hashed
            ps.setString(4, v.getPhone());
            ps.setString(5, v.getAadhar());
            ps.setString(6, v.getDob());
            ps.setBoolean(7, false); // approved (admin will approve)
            ps.setBoolean(8, false);// hasVoted
            ps.setString(9,v.getPhoto());
            

            ps.executeUpdate();

            // 🔑 Get auto-generated DB ID
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);

                // 🆔 Generate Unique Voter ID
                String voterUid = generateVoterUid(generatedId);

                // 🔄 Update voter_uid in DB
                String updateSql = "UPDATE voter SET voter_uid=? WHERE id=?";
                PreparedStatement ps2 = con.prepareStatement(updateSql);
                ps2.setString(1, voterUid);
                ps2.setInt(2, generatedId);
                ps2.executeUpdate();
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===================== LOGIN =====================
    @Override
    public Voter login(String email) {

        String sql = "SELECT * FROM voter WHERE email=?";

        try (Connection con = DbConnection.getConnector();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Voter v = new Voter();
                v.setId(rs.getInt("id"));
                v.setName(rs.getString("name"));
                v.setEmail(rs.getString("email"));
                v.setPassword(rs.getString("password"));
                v.setPhone(rs.getString("phone"));
                v.setAadhar(rs.getString("aadhar"));
                v.setDob(rs.getString("dob"));
                v.setApproved(rs.getBoolean("approved"));
                v.setHasVoted(rs.getBoolean("hasVoted"));
                v.setVoterUid(rs.getString("voter_uid"));
                return v;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===================== ADMIN APPROVAL =====================
    @Override
    public boolean approveVoter(int voterId) {
        String sql = "UPDATE voter SET approved=true WHERE id=?";
        try (Connection con = DbConnection.getConnector();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, voterId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===================== VOTING =====================
    @Override
    public boolean markVoted(int voterId) {
        String sql = "UPDATE voter SET hasVoted=true WHERE id=?";
        try (Connection con = DbConnection.getConnector();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, voterId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean hasVoted(int voterId) {
        String sql = "SELECT hasVoted FROM voter WHERE id=?";
        try (Connection con = DbConnection.getConnector();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, voterId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("hasVoted");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===================== VALIDATIONS =====================
    @Override
    public boolean emailExists(String email) {
        String sql = "SELECT id FROM voter WHERE email=?";
        try (Connection con = DbConnection.getConnector();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean aadharExists(String aadhar) {
        String sql = "SELECT id FROM voter WHERE aadhar=?";
        try (Connection con = DbConnection.getConnector();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, aadhar);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===================== VOTER ID GENERATION =====================
    private String generateVoterUid(int id) {
        int year = Year.now().getValue();
        return "UV-" + year + "-" + String.format("%04d", id);
    }

    @Override
    public Voter getVoterById(int voterId) {

        String sql = "SELECT * FROM voter WHERE id=?";
        try (Connection con = DbConnection.getConnector();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, voterId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Voter v = new Voter();
                v.setId(rs.getInt("id"));
                v.setVoterUid(rs.getString("voter_uid"));
                v.setName(rs.getString("name"));
                v.setEmail(rs.getString("email"));
                v.setDob(rs.getString("dob"));
                v.setAadhar(rs.getString("aadhar"));
                v.setApproved(rs.getBoolean("approved"));
                v.setPhoto(rs.getString("photo"));
                return v;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Map<String, Object>> getElectionResults() {

        List<Map<String, Object>> list = new ArrayList<>();

        String sql =
            "SELECT e.id AS electionId, e.title, " +
            "c.id AS candidateId, c.name AS candidateName, " +
            "c.party, c.votes " +
            "FROM election e " +
            "JOIN candidate c ON c.election_id = e.id " +
            "ORDER BY e.id, c.votes DESC";

        try {
            Connection con = DbConnection.getConnector();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();

                row.put("electionId", rs.getInt("electionId"));
                row.put("title", rs.getString("title"));
                row.put("candidateId", rs.getInt("candidateId"));
                row.put("candidateName", rs.getString("candidateName"));
                row.put("party", rs.getString("party"));
                row.put("votes", rs.getInt("votes"));

                list.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
