package help.lixin.framework.dynamic.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import help.lixin.framework.dynamic.datasource.context.DataSourceContext;

public class VirtualDataSource implements DataSource {
	private PrintWriter out;
	private int seconds;
	private VirtuaDataSourceDelegator dataSourceDelegator;

	public void setDataSourceDelegator(VirtuaDataSourceDelegator dataSourceDelegator) {
		this.dataSourceDelegator = dataSourceDelegator;
	}

	public VirtuaDataSourceDelegator getDataSourceDelegator() {
		return dataSourceDelegator;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return out;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		this.out = out;
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		this.seconds = seconds;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return seconds;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return getConnection(null, null);
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		String dataSourceName = DataSourceContext.datasource();
		return dataSourceDelegator.getConnection(dataSourceName);
	}
}
