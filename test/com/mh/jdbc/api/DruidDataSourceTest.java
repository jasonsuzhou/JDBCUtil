package com.mh.jdbc.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mh.jdbc.datasource.druid.DRUIDDataSourceBuilder;
import com.mh.jdbc.util.DBUtil;
import com.mh.jdbc.util.JDBCConfig;

public class DruidDataSourceTest {
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

	private static DataSource ds = null;
	private static JdbcTemplate jdbcTemplate = null;

	@BeforeClass
	public static void setUp() {
		String url = "jdbc:mysql://localhost:3306/flux?characterEncoding=utf-8&useOldAliasMetadataBehavior=true";
		JDBCConfig config = new JDBCConfig(USERNAME, PASSWORD, url);
		ds = DRUIDDataSourceBuilder.getInstance().buildDataSource(config);
		jdbcTemplate = new JdbcTemplate(ds);
	}

	@Test
	public void testBatchInsert() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = jdbcTemplate.getConnection(false);
			pstmt = conn.prepareStatement(
					"insert into sys_project_config(param_key,param_description,param_value) values (?,?,?)");
			for (int i = 0; i < 10; i++) {
				jdbcTemplate.prepareForBatch(pstmt, new Object[] { "key::" + i, "desc::" + i, "value::" + i },
						new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });
			}
			pstmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnAndStmt(conn, pstmt);
		}
	}

	@Test
	public void testBatchInsert2() throws Exception {
		String sql = "insert into sys_project_config(param_key,param_description,param_value) values (?,?,?)";
		List<Object[]> listArgs = new ArrayList<Object[]>();
		for (int i = 0; i < 10; i++) {
			listArgs.add(new Object[] { "key::" + i, "desc::" + i, "value::" + i });
		}
		jdbcTemplate.executeForBatch(sql, listArgs, new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });
	}

}
