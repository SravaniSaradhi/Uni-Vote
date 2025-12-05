package com.servlet.vote.dto;

public class Vote {
	 private int id;
	    private int voterId;
	    private int candidateId;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getVoterId() {
			return voterId;
		}
		public void setVoterId(int voterId) {
			this.voterId = voterId;
		}
		public int getCandidateId() {
			return candidateId;
		}
		public void setCandidateId(int candidateId) {
			this.candidateId = candidateId;
		}
}
