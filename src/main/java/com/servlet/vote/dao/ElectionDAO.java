package com.servlet.vote.dao;

import java.util.List;
import java.util.Map;

import com.servlet.vote.dto.Election;

public interface ElectionDAO {

    // Create new election
    public boolean create(Election e);

    // Get all elections (admin)
    public List<Election> getAll();

    // Update election status (e.g., ONGOING, CLOSED)
    public boolean updateStatus(int electionId, String status);

    // ---------------- NEW METHODS ----------------

    // Get all elections currently ONGOING (basic info)
    public List<Election> getOngoingElections();

    // Get ONGOING elections with their candidates (for voter dashboard)
    public List<Map<String, Object>> getOngoingElectionCandidates();
}
