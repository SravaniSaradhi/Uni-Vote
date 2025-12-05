package com.servlet.vote.dao;

import com.servlet.vote.dto.Admin;

public interface AdminDAO {
	public boolean register(Admin a);
	public Admin login(String email,String password);

}
