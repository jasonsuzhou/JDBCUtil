package com.mh.jdbc.api;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	public void executeInsert() throws Exception {
		String sql = "insert into user(username, password, age, balance, birthday) values(?,?,?,?,?)";
		Object[] args = new Object[] { "jasonyao", "test1234", 27, 123434, new java.sql.Date(new Date().getTime()) };
		int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.NUMERIC, Types.DATE };
		jdbcTemplate.executeInsert(sql, args, argTypes);
	}

	@Test
	public void queryForMap() throws Exception {
		String sql = "select username as UserName, password as pass, age from user where username=?";
		Object[] args = new Object[] { "jasonyao" };
		int[] argTypes = new int[] { Types.VARCHAR };
		Map<String, Object> results = jdbcTemplate.queryForMap(sql, args, argTypes);
		Assert.assertEquals("jasonyao", results.get("username"));
		Assert.assertEquals("test1234", results.get("PASS"));
		Assert.assertEquals(27, Integer.parseInt(results.get("age").toString()));
	}

	@Test
	public void queryForMapWithNullParameters() throws Exception {
		String sql = "select username as UserName, password as pass, age from user";
		Map<String, Object> results = jdbcTemplate.queryForMap(sql);
		Assert.assertEquals("jasonyao", results.get("username"));
		Assert.assertEquals("test1234", results.get("PASS"));
		Assert.assertEquals(27, Integer.parseInt(results.get("age").toString()));
	}

	@Test
	public void queryForList() throws Exception {
		String sql = "select username, password, age, balance, birthday from user";
		List<User> lsUser = jdbcTemplate.queryForList(sql, null, null, new User());
		int len = lsUser.size();
		Assert.assertEquals(1, len);
		User user = lsUser.get(0);
		Assert.assertEquals("jasonyao", user.getUsername());
	}

	@Test
	public void queryForListWithNullParameters() throws Exception {
		String sql = "select username, password, age, balance, birthday from user";
		List<User> lsUser = jdbcTemplate.queryForList(sql, new User());
		int len = lsUser.size();
		Assert.assertEquals(1, len);
		User user = lsUser.get(0);
		Assert.assertEquals("jasonyao", user.getUsername());
	}

	@Test
	public void queryForListWithSingleColumn() throws Exception {
		String sql = "select username from user";
		List<String> results = jdbcTemplate.queryForList(sql, null, null, String.class);
		int len = results.size();
		Assert.assertEquals(1, len);
		Assert.assertEquals("jasonyao", results.get(0));
	}

	@Test
	public void queryForListWithSingleColumnAndNullParameters() throws Exception {
		String sql = "select username from user";
		List<String> results = jdbcTemplate.queryForList(sql, String.class);
		int len = results.size();
		Assert.assertEquals(1, len);
		Assert.assertEquals("jasonyao", results.get(0));
	}

	@Test
	public void queryForObject() throws Exception {
		String sql = "select age from user where username=?";
		Object[] args = new Object[] { "jasonyao" };
		int[] argTypes = new int[] { Types.VARCHAR };
		int age = jdbcTemplate.queryForObject(sql, args, argTypes, Integer.class);
		Assert.assertEquals(27, age);
	}

	@Test
	public void queryForObjectWithNullParametersAndMapper() throws Exception {
		String sql = "select username, password, age, balance, birthday from user where username='jasonyao'";
		User user = jdbcTemplate.queryForObject(sql, new User());
		Assert.assertEquals("jasonyao", user.getUsername());
		Assert.assertEquals("test1234", user.getPassword());
	}

	@Test
	public void queryForObjectWithNullParameters() throws Exception {
		String sql = "select password from user where username='jasonyao'";
		String password = jdbcTemplate.queryForObject(sql, String.class);
		Assert.assertEquals("test1234", password);
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
	public void executeDelete() throws Exception {
		String sql = "delete from user";
		int affectRows = jdbcTemplate.executeDelete(sql);
		Assert.assertEquals(1, affectRows);
	}

}
