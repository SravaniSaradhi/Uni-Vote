package com.servlet.vote.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import com.servlet.vote.util.DbConnection;

public class ResultDAOImpl implements ResultDAO {

    @Override
    public List<Map<String, Object>> getElectionWiseResults() {

        List<Map<String, Object>> results = new ArrayList<>();

        String sql =
            "SELECT e.id AS electionId, e.title, " +
            "c.id AS candidateId, c.name, c.party, c.votes " +
            "FROM election e " +
            "JOIN candidate c ON c.election_id = e.id " +
            "ORDER BY e.id, c.votes DESC";

        try (
        		Connection con = DbConnection.getConnector();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()
           )
        {

            Map<Integer, Map<String, Object>> electionMap = new LinkedHashMap<>();

            while (rs.next()) {
                int electionId = rs.getInt("electionId");

                electionMap.putIfAbsent(electionId, new HashMap<>());
                Map<String, Object> election = electionMap.get(electionId);

                election.put("title", rs.getString("title"));

                List<Map<String, Object>> candidates =
                    (List<Map<String, Object>>) election.getOrDefault("candidates", new ArrayList<>());

                Map<String, Object> candidate = new HashMap<>();
                candidate.put("name", rs.getString("name"));
                candidate.put("party", rs.getString("party"));
                candidate.put("votes", rs.getInt("votes"));

                candidates.add(candidate);
                election.put("candidates", candidates);
            }

            results.addAll(electionMap.values());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }
}
