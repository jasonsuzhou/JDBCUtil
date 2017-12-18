package com.mh.jdbc.datasource;

import javax.sql.DataSource;

import com.mh.jdbc.util.JDBCConfig;

public interface DataSourceBuilder {
	
	DataSource buildDataSource(JDBCConfig config);
	
}
