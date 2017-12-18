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
		// ������������Ƿ���Ч��sql��Ҫ����һ����ѯ��䡣���validationQueryΪnull��testOnBorrow��testOnReturn��testWhileIdle�����������á�
		ds.setValidationQuery("select 'x'");
		// ��������Ϊtrue����Ӱ�����ܣ����ұ�֤��ȫ�ԡ��������ӵ�ʱ���⣬�������ʱ�����timeBetweenEvictionRunsMillis��ִ��validationQuery��������Ƿ���Ч��
		ds.setTestWhileIdle(true);
		// �黹����ʱִ��validationQuery��������Ƿ���Ч������������ûή������
		ds.setTestOnReturn(false);
		// ��������ʱִ��validationQuery��������Ƿ���Ч������������ûή������
		ds.setTestOnBorrow(false);
		return ds;
	}

}
