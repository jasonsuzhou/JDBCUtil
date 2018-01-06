package com.mh.jdbc.util;

import java.io.Serializable;

public class JDBCConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8388900445535888087L;
	private String dbType;
	private String username;
	private String password;
	/**
	 * mysql::jdbc:mysql://host:port/databaseName?characterEncoding=utf-8&useOldAliasMetadataBehavior=true
	 * </br>
	 * oracle::jdbc:oracle:thin:@host:port:sid </br>
	 * sqlserver::jdbc:sqlserver://host:port;databaseName=databaseName;integratedSecurity=true;
	 */
	private String url;
	private String driverClassName;
	
	public JDBCConfig() {}

	public JDBCConfig(String username, String password, String url) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * ��ʼ�����ӳش�С
	 */
	private int initPoolSize = 1;
	/**
	 * ��С������
	 */
	private int minPoolSize = 1;
	/**
	 * ���������
	 */
	private int maxPoolSize = 5;
	/**
	 * ��ȡ���ӵȴ���ʱ��ʱ�䣬��λ����
	 */
	private int waitTimeout = 60000;

	/**
	 * �����òŽ���һ�μ�⣬�����Ҫ�رյĿ������ӣ���λ�Ǻ���
	 */
	private int timeBetweenEvictionRunsMillis = 6000;

	/**
	 * һ�������ڳ�����С�����ʱ�䣬��λ�Ǻ���
	 */
	private int minEvictableIdleTimeMillis = 300000;

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriverClassName() {
		if (url != null) {
			if (url.contains("mysql")) {
				this.driverClassName = "com.mysql.jdbc.Driver";
			} else if (url.contains("oracle")) {
				this.driverClassName = "oracle.jdbc.driver.OracleDriver";
			} else if (url.contains("sqlserver")) {
				this.driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			}
		}
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public int getInitPoolSize() {
		return initPoolSize;
	}

	public void setInitPoolSize(int initPoolSize) {
		this.initPoolSize = initPoolSize;
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getWaitTimeout() {
		return waitTimeout;
	}

	public void setWaitTimeout(int waitTimeout) {
		this.waitTimeout = waitTimeout;
	}

	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public int getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

}
