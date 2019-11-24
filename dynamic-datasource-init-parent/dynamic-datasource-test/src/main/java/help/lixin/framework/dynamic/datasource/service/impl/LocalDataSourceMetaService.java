package help.lixin.framework.dynamic.datasource.service.impl;

import java.util.List;

import help.lixin.framework.config.Config;
import help.lixin.framework.dynamic.datasource.service.DataSourceParams;
import help.lixin.framework.dynamic.datasource.service.IDataSourceMetaService;

public class LocalDataSourceMetaService implements IDataSourceMetaService {

	private Config config;

	@Override
	public DataSourceParams getDataSourceMeta(String dataSourceName) {
		return null;
	}

	@Override
	public void setConfig(Config config) {
		this.config = config;
	}

	@Override
	public void setMetaChain(List<IDataSourceMetaService> metaServices) {

	}
}
