package com.mh.jdbc.api;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.mh.jdbc.util.RowMapper;

public interface JdbcOperations {
	
	Map<String, Object> queryForMap(String sql) throws SQLException;
	
	Map<String, Object> queryForMap(String sql, Object[] args, int[] argTypes) throws SQLException;
	
	<T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws SQLException;
	
	<T> T queryForObject(String sql, Class<T> requiredType) throws SQLException;

	<T> T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType) throws SQLException;
	
	<T> List<T> queryForList(String sql, Class<T> requiredType) throws SQLException;

	<T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> requiredType) throws SQLException;
	
	<T> T queryForObject(String sql, RowMapper<T> rowMapper) throws SQLException;
	
	<T> List<T> queryForList(String sql, RowMapper<T> rowMapper) throws SQLException;
	
	<T> List<T> queryForList(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws SQLException;

	boolean executeInsert(String sql, Object[] args, int[] argTypes) throws SQLException;

	int executeUpdate(String sql, Object[] args, int[] argTypes) throws SQLException;

	int executeUpdate(String sql) throws SQLException;

	int executeDelete(String sql, Object[] args, int[] argTypes) throws SQLException;

	int executeDelete(String sql) throws SQLException;
}
