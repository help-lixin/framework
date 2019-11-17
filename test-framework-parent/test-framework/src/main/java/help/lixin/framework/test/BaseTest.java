package help.lixin.framework.test;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

import com.alibaba.druid.pool.DruidDataSource;

public abstract class BaseTest {
	private DruidDataSource dataSource = null;

	public DruidDataSource getDataSource() {
		return dataSource;
	}

	private void setDataSource(DruidDataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Before
	public void init() throws SQLException {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl("jdbc:mysql://127.0.0.1:3306/fw?useUnicode=true&characterEncoding=UTF8");
		datasource.setUsername("root");
		datasource.setPassword("123456");
		datasource.setDriverClassName("com.mysql.jdbc.Driver");

		datasource.setInitialSize(1);
		datasource.setMinIdle(1);
		datasource.setMaxActive(20);
		datasource.setMaxWait(60000);
		datasource.setValidationQuery("SELECT 1");

		datasource.setRemoveAbandoned(true);
		datasource.setTimeBetweenEvictionRunsMillis(60000);
		datasource.setMinEvictableIdleTimeMillis(300000);

		datasource.setTestWhileIdle(Boolean.TRUE);
		datasource.setTestOnBorrow(Boolean.FALSE);
		datasource.setTestOnReturn(Boolean.FALSE);
		datasource.setPoolPreparedStatements(Boolean.TRUE);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(20);
		datasource.init();

		setDataSource(datasource);
	}

	@After
	public void destory() {
		getDataSource().close();
	}
}
