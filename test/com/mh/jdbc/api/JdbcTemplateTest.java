package com.mh.jdbc.api;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mh.jdbc.api.JdbcTemplate;

public class JdbcTemplateTest {
	private static JdbcTemplate jdbcTemplate = new JdbcTemplate(null);

	@BeforeClass
	public static void setUp() {
		String sql = "delete from user";
		try {
			jdbcTemplate.executeUpdate(sql, null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void executeInsert() {
		String sql = "insert into user(username, password, age, balance, birthday) values(?,?,?,?,?)";
		Object[] args = new Object[] { "jasonyao", "test1234", 27, 123434, new java.sql.Date(new Date().getTime()) };
		int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.NUMERIC, Types.DATE };
		try {
			jdbcTemplate.executeInsert(sql, args, argTypes);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryForList() {
		String sql = "select username, password, age, balance, birthday from user";
		try {
			List<User> lsUser = jdbcTemplate.queryForList(sql, null, null, new User());
			int len = lsUser.size();
			Assert.assertEquals(1, len);
			User user = lsUser.get(0);
			Assert.assertEquals("jasonyao", user.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryForListWithNullParameters() {
		String sql = "select username, password, age, balance, birthday from user";
		try {
			List<User> lsUser = jdbcTemplate.queryForList(sql, new User());
			int len = lsUser.size();
			Assert.assertEquals(1, len);
			User user = lsUser.get(0);
			Assert.assertEquals("jasonyao", user.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryForListWithSingleColumn() {
		String sql = "select username from user";
		try {
			List<String> results = jdbcTemplate.queryForList(sql, null, null, String.class);
			int len = results.size();
			Assert.assertEquals(1, len);
			Assert.assertEquals("jasonyao", results.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryForListWithSingleColumnAndNullParameters() {
		String sql = "select username from user";
		try {
			List<String> results = jdbcTemplate.queryForList(sql, String.class);
			int len = results.size();
			Assert.assertEquals(1, len);
			Assert.assertEquals("jasonyao", results.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryForObject() {
		String sql = "select age from user where username=?";
		Object[] args = new Object[] { "jasonyao" };
		int[] argTypes = new int[] { Types.VARCHAR };
		try {
			int age = jdbcTemplate.queryForObject(sql, args, argTypes, Integer.class);
			Assert.assertEquals(27, age);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(expected = SQLException.class)
	public void queryForObjectWithReturnNull() throws SQLException {
		String sql = "select age from user where username=?";
		Object[] args = new Object[] { "notExistUser" };
		int[] argTypes = new int[] { Types.VARCHAR };
		jdbcTemplate.queryForObject(sql, args, argTypes, Integer.class);
	}

	// This test method must be the last one
	@Test
	public void executeDelete() {
		String sql = "delete from user";
		try {
			int affectRows = jdbcTemplate.executeDelete(sql);
			Assert.assertEquals(1, affectRows);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
