package help.lixin.framework.dynamic.datasource.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import help.lixin.framework.config.Config;

public class DataSourceMetaServiceChain implements IDataSourceMetaService {

	private List<IDataSourceMetaService> metaServices = new ArrayList<IDataSourceMetaService>();

	public void setMetaChain(List<IDataSourceMetaService> metaServices) {
		this.metaServices = metaServices;
	}

	@Override
	public DataSourceParams getDataSourceMeta(String dataSourceName) throws SQLException {
		// 从缓存中获取
		DataSourceParams dataSourceParams = DataSourceMetaMediator.getMeta(dataSourceName);
		if (null == dataSourceParams) {
			// 责任链为空,抛出异常
			if (metaServices.isEmpty()) {
				throw new SQLException("根据数据源名称:[" + dataSourceName + "]获取元数据失败!");
			}
			// 调用责任链直接获取为止
			for (int i = 0; i < metaServices.size(); i++) {
				IDataSourceMetaService dataSourceMetaService = metaServices.get(i);
				dataSourceParams = dataSourceMetaService.getDataSourceMeta(dataSourceName);
				if (null != dataSourceParams) {
					break;
				}
			}
			// 抛出异常
			if (null == dataSourceParams) {
				throw new SQLException("根据数据源名称:[" + dataSourceName + "]获取元数据失败!");
			}
			// 添加到缓存
			DataSourceMetaMediator.addMeta(dataSourceName, dataSourceParams);
		}
		return dataSourceParams;
	}

	@Override
	public void setConfig(Config config) {
		// TODO
	}
}
