package com.servlet.vote.dto;

public class Voter {

    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private boolean approved;
    private boolean hasVoted;
    private String aadhar;
    private String dob;
    private String voterUid;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public String getVoterUid() {
        return voterUid;
    }

    public void setVoterUid(String voterUid) {
        this.voterUid = voterUid;
    }


    public Voter() {}

    public Voter(int id, String name, String email, String password, 
                 String phone, boolean approved, boolean hasVoted,
                 String aadhar, String dob) 
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.approved = approved;
        this.hasVoted = hasVoted;
        this.aadhar = aadhar;
        this.dob = dob;
    }

    // getters and setters below 👇

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

    public String getEmail() { 
    	return email; 
    	}
    public void setEmail(String email) {
    	this.email = email;
    	}

    public String getPassword() {
    	return password;
    	}
    public void setPassword(String password) { 
    	this.password = password;
    	}

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    public boolean isHasVoted() { return hasVoted; }
    public void setHasVoted(boolean hasVoted) { this.hasVoted = hasVoted; }

    public String getAadhar() { return aadhar; }
    public void setAadhar(String aadhar) { this.aadhar = aadhar; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

}
