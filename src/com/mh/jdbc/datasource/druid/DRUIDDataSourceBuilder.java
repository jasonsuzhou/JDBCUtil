package com.mh.jdbc.datasource.druid;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.mh.jdbc.datasource.DataSourceBuilder;
import com.mh.jdbc.util.JDBCConfig;

public class DRUIDDataSourceBuilder implements DataSourceBuilder {

	private static DRUIDDataSourceBuilder instance = new DRUIDDataSourceBuilder();

	public static DataSourceBuilder getInstance() {
		return instance;
	}

	@Override
	public DataSource buildDataSource(JDBCConfig config) {
		DruidDataSource ds = new DruidDataSource();
		ds.setUsername(config.getUsername());
		ds.setPassword(config.getPassword());
		ds.setUrl(config.getUrl());
		ds.setDriverClassName(config.getDriverClassName());
		ds.setInitialSize(config.getInitPoolSize());
		ds.setMinIdle(config.getMinPoolSize());
		ds.setMaxActive(config.getMaxPoolSize());
		ds.setMinEvictableIdleTimeMillis(config.getMinEvictableIdleTimeMillis());
		// 用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。
		ds.setValidationQuery("select 'x'");
		// 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
		ds.setTestWhileIdle(true);
		// 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
		ds.setTestOnReturn(false);
		// 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
		ds.setTestOnBorrow(false);
		return ds;
	}

}
