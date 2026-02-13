package com.servlet.vote.dao;

import com.servlet.vote.dto.Candidate;

public interface CandidateDAO {

	public boolean register(Candidate c);
	public Candidate login(String name, String password);
	public boolean approve(int id);
	public boolean addVote(int candidateId);
}
