package com.mh.jdbc.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.mh.jdbc.util.RowMapper;

public class User implements RowMapper<User> {

	private String username;
	private String password;
	private int age;
	private double balance;
	private Date birthday;

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setAge(rs.getInt("age"));
		user.setBalance(rs.getDouble("balance"));
		user.setBirthday(rs.getDate("birthday"));
		return user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}
