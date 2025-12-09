package com.servlet.vote.dao;

import java.util.List;
import java.util.Map;

import com.servlet.vote.dto.Election;

public interface ElectionDAO {

    boolean create(Election e);
    List<Election> getAll();
    boolean updateStatus(int electionId, String status);

    List<Election> getOngoingElections();
    List<Map<String, Object>> getOngoingElectionCandidates();
}
