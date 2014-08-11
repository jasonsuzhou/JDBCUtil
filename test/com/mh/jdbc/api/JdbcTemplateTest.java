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
import com.mh.jdbc.util.Pager;

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
	public void queryForListMap() throws Exception {
		String sql = "select username as UserName, password as pass, age from user where username=?";
		Object[] args = new Object[] { "jasonyao" };
		int[] argTypes = new int[] { Types.VARCHAR };
		List<Map<String, Object>> lsMap = jdbcTemplate.queryForListMap(sql, args, argTypes);
		Assert.assertEquals(1, lsMap.size());
		Map<String, Object> map = lsMap.get(0);
		Assert.assertEquals("test1234", map.get("pass"));
	}

	@Test
	public void queryForListMapWithNullParameters() throws Exception {
		String sql = "select username as UserName, password as pass, age from user where username='jasonyao'";
		List<Map<String, Object>> lsMap = jdbcTemplate.queryForListMap(sql);
		Assert.assertEquals(1, lsMap.size());
		Map<String, Object> map = lsMap.get(0);
		Assert.assertEquals("test1234", map.get("pass"));
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

	@Test
	public void queryForInt() throws Exception {
		String sql = "select age from user where username=?";
		Object[] args = new Object[] { "jasonyao" };
		int[] argTypes = new int[] { Types.VARCHAR };
		int age = jdbcTemplate.queryForInt(sql, args, argTypes);
		Assert.assertEquals(27, age);
	}

	@Test
	public void queryForLongWithNullParameters() throws Exception {
		String sql = "select count(*) from user";
		long total = jdbcTemplate.queryForLong(sql);
		Assert.assertEquals(1, total);
	}

	@Test
	public void queryForLong() throws Exception {
		String sql = "select age from user where username=?";
		Object[] args = new Object[] { "jasonyao" };
		int[] argTypes = new int[] { Types.VARCHAR };
		long age = jdbcTemplate.queryForLong(sql, args, argTypes);
		Assert.assertEquals(27, age);
	}

	@Test
	public void queryForDoubleWithNullParameters() throws Exception {
		String sql = "select count(*) from user";
		double total = jdbcTemplate.queryForDouble(sql);
		Assert.assertEquals(1.0, total);
	}

	@Test
	public void queryForDouble() throws Exception {
		String sql = "select age from user where username=?";
		Object[] args = new Object[] { "jasonyao" };
		int[] argTypes = new int[] { Types.VARCHAR };
		double age = jdbcTemplate.queryForDouble(sql, args, argTypes);
		Assert.assertEquals(27.0, age);
	}

	@Test
	public void queryForIntWithNullParameters() throws Exception {
		String sql = "select count(*) from user";
		int total = jdbcTemplate.queryForInt(sql);
		Assert.assertEquals(1, total);
	}

	@Test
	public void executeDelete() throws Exception {
		String sql = "delete from user";
		int affectRows = jdbcTemplate.executeDelete(sql);
		Assert.assertEquals(1, affectRows);
	}

	@Test
	public void queryForPagerWithNullParameters() throws Exception {
		this.prepareMultiRecords(32);
		String selectSQL = "select username, password, age, balance, birthday from user limit 10 offset 10";
		String countSQL = "select count(*) from user";
		Pager<User> pagerUser = jdbcTemplate.queryForPager(selectSQL, countSQL, new User());
		Assert.assertEquals(pagerUser.getTotalCount(), 32);
		for (User user : pagerUser.getResultSet()) {
			System.out.println(user.getUsername());
		}
	}

	@Test
	public void queryForPager() throws Exception {
		String selectSQL = "select username, password, age, balance, birthday from user where username like ? limit 10 offset 10";
		String countSQL = "select count(*) from user where username like ?";
		Object[] args = new Object[] { "%jasonyao%" };
		int[] argTypes = new int[] { Types.VARCHAR };
		Pager<User> pagerUser = jdbcTemplate.queryForPager(selectSQL, countSQL, args, argTypes, new User());
		Assert.assertEquals(pagerUser.getTotalCount(), 32);
		for (User user : pagerUser.getResultSet()) {
			System.out.println(user.getUsername());
		}
	}

	private void prepareMultiRecords(int number) throws Exception {
		String sql = "insert into user(username, password, age, balance, birthday) values(?,?,?,?,?)";
		for (int i = 0; i < number; i++) {
			Object[] args = new Object[] { "jasonyao" + i, "test1234", 27, 123434,
					new java.sql.Date(new Date().getTime()) };
			int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.NUMERIC, Types.DATE };
			jdbcTemplate.executeInsert(sql, args, argTypes);
		}
	}

}
