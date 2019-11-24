package help.lixin.framework.dynamic.datasource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import help.lixin.framework.config.Config;
import help.lixin.framework.dynamic.datasource.service.IDataSourceMetaService;
import help.lixin.framework.dynamic.datasource.service.impl.LocalDataSourceMetaService;
import help.lixin.framework.dynamic.datasource.service.impl.RemoteDataSourceMetaService;

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
