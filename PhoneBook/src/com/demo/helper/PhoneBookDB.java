package com.demo.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.demo.config.DbUtil;

public class PhoneBookDB {

	public static void insertRecord(String fName, String lName, String phone, int prvt) {
		String insert = "insert into phone(first_name,last_name,phone,private) values(?,?,?,?)";
		try {
			PreparedStatement statement = DbUtil.getConnection().prepareStatement(insert);
			statement.setString(1, fName);
			statement.setString(2, lName);
			statement.setString(3, phone);
			statement.setInt(4, prvt);
			statement.executeUpdate();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ResultSet selectRecord() {
		String select = "select * from phone";
		ResultSet resultSet = null;
		try {
			Statement statement = DbUtil.getConnection().createStatement();
			resultSet = statement.executeQuery(select);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public static void updateRecord(int id, String fName, String lName, String phone, int prvt) {
		String update = "update phone set first_name=?, last_name=?, phone=?, private=? WHERE id=?";
		try {
			PreparedStatement statement = DbUtil.getConnection().prepareStatement(update);
			statement.setString(1, fName);
			statement.setString(2, lName);
			statement.setString(3, phone);
			statement.setInt(4, prvt);
			statement.setInt(5, id);
			statement.executeUpdate();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteDelect(int id) {
		String delete = "delete from phone where id = ?";
		try {
			PreparedStatement statement = DbUtil.getConnection().prepareStatement(delete);
			statement.setInt(1, id);
			statement.executeUpdate();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

	}
}
