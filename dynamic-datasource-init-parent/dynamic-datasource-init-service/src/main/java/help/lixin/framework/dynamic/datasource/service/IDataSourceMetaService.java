package help.lixin.framework.dynamic.datasource.service;

import java.sql.SQLException;
import java.util.List;

import help.lixin.framework.config.Config;

public interface IDataSourceMetaService {
	public DataSourceParams getDataSourceMeta(String dataSourceName) throws SQLException;

	public void setConfig(Config config);

	public void setMetaChain(List<IDataSourceMetaService> metaServices);
}
