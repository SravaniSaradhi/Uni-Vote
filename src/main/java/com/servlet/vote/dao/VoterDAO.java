package com.servlet.vote.dao;

import com.servlet.vote.dto.Voter;

public interface VoterDAO {

    boolean register(Voter v);

    // get voter by email (for login)
    Voter login(String email);

    // admin approves voter
    boolean approveVoter(int voterId);

    // mark voter has voted
    boolean markVoted(int voterId);

    // check if voter has already voted (global flag)
    boolean hasVoted(int voterId);

    // new: validation helpers
    boolean emailExists(String email);
    boolean aadharExists(String aadhar);
}
