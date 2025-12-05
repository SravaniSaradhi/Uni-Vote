package com.servlet.vote.dto;

public class Candidate {

	private int id;
    private String name;
    private String party;
    private String manifesto;
    private boolean approved;
    private String password;
    private int votes;
    private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParty() {
		return party;
	}
	public void setParty(String party) {
		this.party = party;
	}
	public String getManifesto() {
		return manifesto;
	}
	public void setManifesto(String manifesto) {
		this.manifesto = manifesto;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public int getVotes() {
		return votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		
	}
	public String getPassword() {
		return password;
	}
}
