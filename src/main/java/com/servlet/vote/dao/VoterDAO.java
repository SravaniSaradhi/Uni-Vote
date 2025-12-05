package com.servlet.vote.dao;

import com.servlet.vote.dto.Voter;

public interface VoterDAO {
	
	public boolean register(Voter v);
	public Voter login(String email,String Password);
    public boolean approveVoter(int voterId);
    public boolean markVoted(int voterId);
    boolean hasVoted(int voterId);

}
