JdbcTemplate with H2 database
---
- APIs show below:
```
	Map<String, Object> queryForMap(String sql) throws SQLException;
	Map<String, Object> queryForMap(String sql, Object[] args, int[] argTypes) throws SQLException;
	<T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws SQLException;
	<T> T queryForObject(String sql, Class<T> requiredType) throws SQLException;
	<T> T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType) throws SQLException;
	List<Map<String, Object>> queryForListMap(String sql, Object[] args, int[] argTypes) throws SQLException;
	List<Map<String, Object>> queryForListMap(String sql) throws SQLException;
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
	int queryForInt(String sql, Object[] args, int[] argTypes) throws SQLException;
	int queryForInt(String sql) throws Exception;
	long queryForLong(String sql, Object[] args, int[] argTypes) throws SQLException;
	long queryForLong(String sql) throws Exception;
	double queryForDouble(String sql, Object[] args, int[] argTypes) throws SQLException;
	double queryForDouble(String sql) throws Exception;
	<T> Pager<T> queryForPager(String selectSQL, String countSQL, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
			throws SQLException;
	<T> Pager<T> queryForPager(String selectSQL, String countSQL, RowMapper<T> rowMapper) throws SQLException;
```
- Use client tool to connect to the database
```
$ java -cp h2*.jar org.h2.tools.Shell
Welcome to H2 Shell 1.4.181 (2014-08-06)
Exit with Ctrl+C
[Enter]   jdbc:h2:~/test
URL       jdbc:h2:/Users/jasonyao/project/workspace_list/biyesheji/h2demo/h2demo
[Enter]   org.h2.Driver
Driver
[Enter]
User
[Enter]   Hide
Password
Password
Connected
Commands are case insensitive; SQL statements end with ';'
help or ?      Display this help
list           Toggle result list / stack trace mode
maxwidth       Set maximum column width (default is 100)
autocommit     Enable or disable autocommit
history        Show the last 20 statements
quit or exit   Close the connection and exit

sql> show tables;
TABLE_NAME | TABLE_SCHEMA
(0 rows, 46 ms)
```
- To create one test table
```
sql> create table User (username varchar(32), password varchar(32), age int(11), balance number, primary key (username));
(Update count: 0, 10 ms)
sql> show tables;
TABLE_NAME | TABLE_SCHEMA
USER       | PUBLIC
(1 row, 5 ms)
sql> select * from user;
USERNAME | PASSWORD | AGE | BALANCE
(0 rows, 1 ms)
sql> alter table user add birthday date;
```