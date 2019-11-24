1.思想
	动态数据源的思想是:在调用SQL之前,根据用户设置的"数据源名称",去元数据中心获得DataSource,并缓存DataSource

2.不与Spring整合
	DataSourceContext.datasource("test");

3. 实现IDataSourceMetaService,当用户设置"数据源名称"后,去元数据中心下载DataSouce需要的元数据,并创建:DataSource
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
		// dataSourceName是必填项
		dataSourceParams.setDataSourceName("test");
		dataSourceParams.setDataSourceType(DataSourceType.DRUID);
		dataSourceParams.setDriverClassName("com.mysql.jdbc.Driver");
		dataSourceParams.setUrl("jdbc:mysql://127.0.0.1:3306/fw?useUnicode=true&characterEncoding=UTF8");
		dataSourceParams.setUsername("root");
		dataSourceParams.setPassword("123456");
		return dataSourceParams;
	}
	public void setConfig(Config config) {
		this.config = config;
	}
	public void setMetaChain(List<IDataSourceMetaService> metaServices) {
	}
}

	
4.与Spring整合(添加依赖)
	<dependency>
		<groupId>help.lixin.framework.dynamic.datasource</groupId>
		<artifactId>dynamic-datasource-init-start</artifactId>
		<version>1.0.0-SNAPSHOT</version>
	</dependency>

5.与Spring整合(实现DataSourceCallback(在调用SQL之前设置"数据源名称"),主要解决手工重复第二部的操作)
  内部有一个AOP,针对@Service注解进行代理,然后,"数据源名称"留出一个函数给开发自己配置.
  
package help.lixin.framework.dynamic.datasource.test;
import java.lang.reflect.Method;
import org.springframework.stereotype.Component;
import help.lixin.framework.dynamic.datasource.DataSourceCallback;

// 需要将对象交给Spring管理
@Component
public class TestDataSourceCallback implements DataSourceCallback {
	@Override
	public String datasource(Class<?> targetClass, Method targetMethod) {
		// 业务自行处理
		return "test";
	}
}
	
6.与Spring整合(把元数据中心的Bean交给Spring管理)
@Configuration
public class DynamicDataSourceConfig {
	@Bean
	public IDataSourceMetaService localDataSourceMetaService(Config config) {
		IDataSourceMetaService localDataSourceMetaService = new LocalDataSourceMetaService();
		localDataSourceMetaService.setConfig(config);
		return localDataSourceMetaService;
	}
	@Bean
	public IDataSourceMetaService remoteDataSourceMetaService(Config config) {
		IDataSourceMetaService remoteDataSourceMetaService = new RemoteDataSourceMetaService();
		remoteDataSourceMetaService.setConfig(config);
		return remoteDataSourceMetaService;
	}
}
