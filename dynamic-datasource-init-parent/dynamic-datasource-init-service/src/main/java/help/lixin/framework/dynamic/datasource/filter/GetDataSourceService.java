package help.lixin.framework.dynamic.datasource.filter;

import java.sql.Connection;
import java.sql.SQLException;

import help.lixin.framework.dynamic.datasource.service.IDataSourceMetaService;

public interface GetDataSourceService {
	/**
	 * 获取连接
	 * 
	 * @param dataSourceName
	 * @return
	 */
	public Connection getConnection(String dataSourceName) throws SQLException;
	
	
	public void setMeataService(IDataSourceMetaService chain);
}
