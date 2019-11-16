package help.lixin.framework.dynamic.datasource.factory;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

import help.lixin.framework.dynamic.datasource.service.DataSourceParams;

public class DruidCreateDataSource implements ICreateDataSource {
	@Override
	public DataSource createDataSource(DataSourceParams dataSourceParams) throws SQLException {
		DruidDataSource datasource = new DruidDataSource();

		datasource.setUrl(dataSourceParams.getUrl());
		datasource.setUsername(dataSourceParams.getUsername());
		datasource.setPassword(dataSourceParams.getPassword());
		datasource.setDriverClassName(dataSourceParams.getDriverClassName());

		datasource.setInitialSize(dataSourceParams.getInitialSize());
		datasource.setMinIdle(dataSourceParams.getMinIdle());
		datasource.setMaxActive(dataSourceParams.getMaxActive());
		datasource.setMaxWait(dataSourceParams.getMaxWait());
		datasource.setValidationQuery(dataSourceParams.getValidationQuery());

		datasource.setRemoveAbandoned(dataSourceParams.isRemoveAbandoned());
		datasource.setTimeBetweenEvictionRunsMillis(dataSourceParams.getTimeBetweenEvictionRunsMillis());
		datasource.setMinEvictableIdleTimeMillis(dataSourceParams.getMinEvictableIdleTimeMillis());

		datasource.setTestWhileIdle(dataSourceParams.isTestWhileIdle());
		datasource.setTestOnBorrow(dataSourceParams.isTestOnBorrow());
		datasource.setTestOnReturn(dataSourceParams.isTestOnReturn());
		// TODO
		datasource.setPoolPreparedStatements(dataSourceParams.isPoolPreparedStatements());
		datasource.setMaxPoolPreparedStatementPerConnectionSize(dataSourceParams.getMaxOpenPreparedStatements());
		datasource.setConnectionInitSqls(dataSourceParams.getConnectionInitSqls());
		if (null != dataSourceParams.getFilters()) {
			try {
				datasource.setFilters(dataSourceParams.getFilters());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		datasource.init();
		return datasource;
	}
}
