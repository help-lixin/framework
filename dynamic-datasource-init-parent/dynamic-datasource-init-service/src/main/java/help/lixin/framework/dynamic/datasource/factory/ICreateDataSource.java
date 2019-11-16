package help.lixin.framework.dynamic.datasource.factory;

import java.sql.SQLException;

import javax.sql.DataSource;

import help.lixin.framework.dynamic.datasource.service.DataSourceParams;

/**
 * 创建数据源
 * 
 * @author lixin
 *
 */
public interface ICreateDataSource {
	DataSource createDataSource(DataSourceParams dataSourceParams) throws SQLException; 
}
