package com.demo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
	private static final String URL = "jdbc:mysql://localhost:3306/phonebook";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static Connection conn = null;

	static {
		try {
			if (conn == null) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static Connection getConnection() {
		return conn;
	}
}
