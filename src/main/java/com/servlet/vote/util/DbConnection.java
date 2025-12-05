package com.servlet.vote.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	public static Connection getConnector() {
		Connection con=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		  con=DriverManager.getConnection("jdbc:mysql://localhost:3308/univote","root","sravani19");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}


}
