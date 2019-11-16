package help.lixin.framework.dynamic.datasource;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import help.lixin.framework.config.Config;
import help.lixin.framework.dynamic.datasource.filter.DefaultGetDataSourceService;
import help.lixin.framework.dynamic.datasource.filter.GetDataSourceService;
import help.lixin.framework.dynamic.datasource.service.DataSourceMetaServiceChain;
import help.lixin.framework.dynamic.datasource.service.IDataSourceMetaService;
import help.lixin.framework.dynamic.datasource.service.impl.LocalDataSourceMetaService;
import help.lixin.framework.dynamic.datasource.service.impl.RemoteDataSourceMetaService;

@Configuration
public class DynamicDataSourceAutoConfiguration {

	@Resource
	private Config config;

	@Bean
	public List<IDataSourceMetaService> metaServices() {
		List<IDataSourceMetaService> metaList = new ArrayList<IDataSourceMetaService>();
		LocalDataSourceMetaService localDataSourceMetaService = new LocalDataSourceMetaService();
		localDataSourceMetaService.setConfig(config);

		RemoteDataSourceMetaService remoteDataSourceMetaService = new RemoteDataSourceMetaService();
		remoteDataSourceMetaService.setConfig(config);

		metaList.add(remoteDataSourceMetaService);
		metaList.add(localDataSourceMetaService);
		return metaList;
	}

	@Bean
	public IDataSourceMetaService dataSourceMetaService() {
		IDataSourceMetaService dataSourceMetaService = new DataSourceMetaServiceChain();
		dataSourceMetaService.setMetaChain(metaServices());
		return dataSourceMetaService;
	}

	@Bean
	public GetDataSourceService dataSourceService() {
		GetDataSourceService dataSourceService = new DefaultGetDataSourceService();
		dataSourceService.setMeataService(dataSourceMetaService());
		return dataSourceService;
	}

	@Bean
	public VirtuaDataSourceDelegator dataSourceDelegator() {
		VirtuaDataSourceDelegator virtuaDataSourceDelegator = new VirtuaDataSourceDelegator();
		virtuaDataSourceDelegator.setGetDataSourceService(dataSourceService());
		return virtuaDataSourceDelegator;
	}

	// 当容器里没有指定的Bean时才创建该Bean
	@Bean
	@ConditionalOnMissingBean
	public DataSource dataSource() {
		VirtualDataSource virtualDataSource = new VirtualDataSource();
		virtualDataSource.setDataSourceDelegator(dataSourceDelegator());
		return virtualDataSource;
	}
}
