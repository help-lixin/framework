package help.lixin.framework.dynamic.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import help.lixin.framework.config.Config;
import help.lixin.framework.dynamic.datasource.filter.DefaultGetDataSourceService;
import help.lixin.framework.dynamic.datasource.filter.GetDataSourceService;
import help.lixin.framework.dynamic.datasource.service.DataSourceMetaServiceChain;
import help.lixin.framework.dynamic.datasource.service.IDataSourceMetaService;
import help.lixin.framework.dynamic.datasource.service.impl.LocalDataSourceMetaService;
import help.lixin.framework.dynamic.datasource.service.impl.RemoteDataSourceMetaService;

public class VirtuaDataSourceDelegatorTest {
	
	private Config config = null;
	
	@Before
	public void before(){
		config = Config
				.fromResourcePath(VirtuaDataSourceDelegatorTest.class.getResource("/config.ini").getPath());
	}
	

	@Test
	public void testGetConnection() {
		// 具体获取元数据的对象
		List<IDataSourceMetaService> metaServices = new ArrayList<IDataSourceMetaService>(2);
		LocalDataSourceMetaService localDataSourceMetaService = new LocalDataSourceMetaService();
		localDataSourceMetaService.setConfig(config);

		RemoteDataSourceMetaService remoteDataSourceMetaService = new RemoteDataSourceMetaService();
		remoteDataSourceMetaService.setConfig(config);

		metaServices.add(localDataSourceMetaService);
		metaServices.add(remoteDataSourceMetaService);

		IDataSourceMetaService dataSourceMetaService = new DataSourceMetaServiceChain();
		dataSourceMetaService.setMetaChain(metaServices);

		// 具体获取数据源服务
		GetDataSourceService dataSourceService = new DefaultGetDataSourceService();
		dataSourceService.setMeataService(dataSourceMetaService);

		VirtuaDataSourceDelegator virtuaDataSourceDelegator = new VirtuaDataSourceDelegator();
		virtuaDataSourceDelegator.setGetDataSourceService(dataSourceService);
		Connection conn = null;
		try {
			conn = virtuaDataSourceDelegator.getConnection("test");
			conn = virtuaDataSourceDelegator.getConnection("test");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
