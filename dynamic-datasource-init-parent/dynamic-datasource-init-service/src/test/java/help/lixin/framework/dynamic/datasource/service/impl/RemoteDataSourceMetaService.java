package help.lixin.framework.dynamic.datasource.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import help.lixin.framework.config.Config;
import help.lixin.framework.dynamic.datasource.service.DataSourceParams;
import help.lixin.framework.dynamic.datasource.service.DataSourceType;
import help.lixin.framework.dynamic.datasource.service.IDataSourceMetaService;

public class RemoteDataSourceMetaService implements IDataSourceMetaService {
	private Config config;

	@Override
	public DataSourceParams getDataSourceMeta(String dataSourceName) {
		String groupKey = "remote-datasource";
		String urlPrefix = config.getSectionProperty(groupKey, "datasource.url.prefix");
		String urlSuffix = config.getSectionProperty(groupKey, "datasource.url.subfix");
		String requestUrl = urlPrefix + urlSuffix + "/" + dataSourceName;
		
		Map<String,String> responseDatas = new HashMap<String,String>();
		
		DataSourceParams dataSourceParams  = new DataSourceParams();
		dataSourceParams.setDataSourceName("test");
		dataSourceParams.setDataSourceType(DataSourceType.DRUID);
		dataSourceParams.setDriverClassName("com.mysql.jdbc.Driver");
		dataSourceParams.setUrl("jdbc:mysql://127.0.0.1:3306/fw?useUnicode=true&characterEncoding=UTF8");
		dataSourceParams.setUsername("root");
		dataSourceParams.setPassword("123456");
		
		return dataSourceParams;
	}

	@Override
	public void setConfig(Config config) {
		this.config = config;
	}

	@Override
	public void setMetaChain(List<IDataSourceMetaService> metaServices) {

	}
}
