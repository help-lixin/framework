package help.lixin.framework.dynamic.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import help.lixin.framework.config.Config;
import help.lixin.framework.dynamic.datasource.filter.GetDataSourceService;

/**
 * 虚拟数据源获取委托类
 * 
 * @author lixin
 */
public class VirtuaDataSourceDelegator {
	private Config config;

	private GetDataSourceService getDataSourceService;

	public void setGetDataSourceService(GetDataSourceService getDataSourceService) {
		this.getDataSourceService = getDataSourceService;
	}

	public GetDataSourceService getGetDataSourceService() {
		return getDataSourceService;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Config getConfig() {
		return config;
	}

	public Connection getConnection(String dataSourceName) throws SQLException {
		return getGetDataSourceService().getConnection(dataSourceName);
	}
}
