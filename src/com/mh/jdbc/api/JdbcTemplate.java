package com.mh.jdbc.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.mh.jdbc.util.ColumnMapRowMapper;
import com.mh.jdbc.util.DBUtil;
import com.mh.jdbc.util.RowMapper;
import com.mh.jdbc.util.SingleColumnRowMapper;

public class JdbcTemplate implements JdbcOperations {
	private DataSource dataSrouce = null;

	public JdbcTemplate(DataSource dataSource) {
		this.dataSrouce = dataSource;
	}

	public Connection getConnection() {
		Connection conn = null;
		if (this.dataSrouce == null) {
			conn = DBUtil.getConnection();
		} else {
			try {
				conn = this.dataSrouce.getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	@Override
	public Map<String, Object> queryForMap(String sql) throws SQLException {
		return queryForObject(sql, getColumnMapRowMapper());
	}
	
	@Override
	public Map<String, Object> queryForMap(String sql, Object[] args, int[] argTypes) throws SQLException {
		return queryForObject(sql, args, argTypes, this.getColumnMapRowMapper());
	}
	
	@Override
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper) throws SQLException {
		List<T> results = queryForList(sql, rowMapper);
		return DBUtil.requiredSingleResult(results);
	}
	
	@Override
	public <T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws SQLException {
		List<T> results = queryForList(sql, args, argTypes, rowMapper);
		return DBUtil.requiredSingleResult(results);
	}
	
	@Override
	public <T> T queryForObject(String sql, Class<T> requiredType) throws SQLException {
		List<T> lsResult = this.queryForList(sql, new SingleColumnRowMapper<T>(requiredType));
		return DBUtil.requiredSingleResult(lsResult);
	}
	
	@Override
	public <T> T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType) throws SQLException {
		List<T> lsResult = this.queryForList(sql, args, argTypes, new SingleColumnRowMapper<T>(requiredType));
		return DBUtil.requiredSingleResult(lsResult);
	}
	
	@Override
	public <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> requiredType) throws SQLException {
		return this.queryForList(sql, args, argTypes, new SingleColumnRowMapper<T>(requiredType));
	}
	
	@Override
	public <T> List<T> queryForList(String sql, Class<T> requiredType) throws SQLException {
		return this.queryForList(sql, null, null, new SingleColumnRowMapper<T>(requiredType));
	}
	
	@Override
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper) throws SQLException {
		return this.queryForList(sql, null, null, rowMapper);
	}

	@Override
	public <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<T> lsResult = new ArrayList<T>();
		try {
			conn = this.getConnection();
			pstmt = prepareStatement(sql, args, argTypes, conn);
			rs = pstmt.executeQuery();
			int rowNum = 1;
			while (rs.next()) {
				lsResult.add(rowMapper.mapRow(rs, rowNum));
				rowNum++;
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			DBUtil.closeAll(conn, pstmt, rs);
		}
		return lsResult;
	}

	@Override
	public boolean executeInsert(String sql, Object[] args, int[] argTypes) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean success = true;
		try {
			conn = this.getConnection();
			pstmt = this.prepareStatement(sql, args, argTypes, conn);
			success = pstmt.execute();
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtil.closeConnAndStmt(conn, pstmt);
		}
		return success;
	}

	@Override
	public int executeUpdate(String sql, Object[] args, int[] argTypes) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int affectRows = 0;
		try {
			conn = this.getConnection();
			pstmt = this.prepareStatement(sql, args, argTypes, conn);
			affectRows = pstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtil.closeConnAndStmt(conn, pstmt);
		}
		return affectRows;
	}
	
	@Override
	public int executeUpdate(String sql) throws SQLException {
		return this.executeUpdate(sql, null, null);
	}

	@Override
	public int executeDelete(String sql, Object[] args, int[] argTypes) throws SQLException {
		return this.executeUpdate(sql, args, argTypes);
	}
	
	@Override
	public int executeDelete(String sql) throws SQLException {
		return this.executeUpdate(sql, null, null);
	}

	private PreparedStatement prepareStatement(String sql, Object[] args, int[] argTypes, Connection conn)
			throws SQLException {
		PreparedStatement pstmt;
		pstmt = conn.prepareStatement(sql);
		if (args != null) {
			int temp = 1;
			for (Object arg : args) {
				pstmt.setObject(temp, arg, argTypes[temp - 1]);
				temp++;
			}
		}
		return pstmt;
	}
	
	private RowMapper<Map<String, Object>> getColumnMapRowMapper() {
		return new ColumnMapRowMapper();
	}

}
