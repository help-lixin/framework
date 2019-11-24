package help.lixin.framework.dynamic.datasource.filter;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import help.lixin.framework.dynamic.datasource.DataSourceMediator;
import help.lixin.framework.dynamic.datasource.factory.CreateDataSourceMediator;
import help.lixin.framework.dynamic.datasource.factory.ICreateDataSource;
import help.lixin.framework.dynamic.datasource.service.DataSourceParams;
import help.lixin.framework.dynamic.datasource.service.DataSourceType;
import help.lixin.framework.dynamic.datasource.service.IDataSourceMetaService;

public class DefaultGetDataSourceService implements GetDataSourceService {
	private static final Logger logger = LoggerFactory.getLogger(DefaultGetDataSourceService.class);

	private IDataSourceMetaService metaChain;

	@Override
	public Connection getConnection(String dataSourceName) throws SQLException {
		Connection connection = null;
		synchronized (DefaultGetDataSourceService.class) {
			// 再次从缓存中获取
			DataSource dataSource = DataSourceMediator.getDataSource(dataSourceName);
			// 缓存中存在对应的dataSource,则从缓存中获取连接
			if (null != dataSource) {
				connection = dataSource.getConnection();
				logger.debug("cache get datasource-name:[{}] , and getConnection:[{}]", dataSourceName, connection);
			} else {
				dataSource = DataSourceMediator.getDataSource(dataSourceName);
				if (null == dataSource) {
					DataSourceParams dataSourceParams = metaChain.getDataSourceMeta(dataSourceName);
					// 获取连接池对应的类型
					DataSourceType dataSourceType = dataSourceParams.getDataSourceType();
					ICreateDataSource createDataSourceImpl = CreateDataSourceMediator
							.getCreateDataSource(dataSourceType.name());
					logger.debug("trigger create datasource-name:[{}]", dataSourceParams.getDataSourceName());
					dataSource = createDataSourceImpl.createDataSource(dataSourceParams);
					if (null == dataSource) {
						throw new SQLException("根据:" + dataSourceName + " 获取连接池失败");
					}
					// 添加到缓存中
					DataSourceMediator.addDataSource(dataSourceName, dataSource);
				}
				// 获取一个连接
				connection = dataSource.getConnection();
				logger.debug("trigger create datasource-name:[{}] , and getConnection:[{}]", dataSourceName,
						connection);
			}
		}
		return connection;
	}

	@Override
	public void setMeataService(IDataSourceMetaService chain) {
		this.metaChain = chain;
	}
}
