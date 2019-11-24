package help.lixin.framework.dynamic.datasource;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import help.lixin.framework.config.Config;
import help.lixin.framework.dynamic.datasource.aop.aspectj.DynamicDataSourceAspect;
import help.lixin.framework.dynamic.datasource.filter.DefaultGetDataSourceService;
import help.lixin.framework.dynamic.datasource.filter.GetDataSourceService;
import help.lixin.framework.dynamic.datasource.service.DataSourceMetaServiceChain;
import help.lixin.framework.dynamic.datasource.service.IDataSourceMetaService;

@Configuration
@ConditionalOnBean(value = { Config.class, DataSourceCallback.class })
public class DynamicDataSourceAutoConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAutoConfiguration.class);

	/**
	 * 元数据服务(留给业务自己去实现,具体获取数据源信息)
	 * 
	 * @return
	 */
	@Bean
	public DataSourceMetaServiceFactoryBean metaServices() {
		DataSourceMetaServiceFactoryBean dataSourceMetaServiceFactoryBean = new DataSourceMetaServiceFactoryBean();
		return dataSourceMetaServiceFactoryBean;
	}

	/**
	 * 元数据责任链
	 * 
	 * @return
	 */
	@Bean
	public IDataSourceMetaService dataSourceMetaService(List<IDataSourceMetaService> metaServices) {
		if (null == metaServices) {
			logger.error("用户必须自己实现[IDataSourceMetaService],并注入到Spring中");
		}
		IDataSourceMetaService dataSourceMetaService = new DataSourceMetaServiceChain();
		dataSourceMetaService.setMetaChain(metaServices);
		return dataSourceMetaService;
	}

	@Bean
	public GetDataSourceService dataSourceService(IDataSourceMetaService dataSourceMetaService) {
		GetDataSourceService dataSourceService = new DefaultGetDataSourceService();
		dataSourceService.setMeataService(dataSourceMetaService);
		return dataSourceService;
	}

	/**
	 * 元数据代理处理对象
	 * 
	 * @return
	 */
	@Bean
	public VirtuaDataSourceDelegator dataSourceDelegator(GetDataSourceService dataSourceService) {
		VirtuaDataSourceDelegator virtuaDataSourceDelegator = new VirtuaDataSourceDelegator();
		virtuaDataSourceDelegator.setGetDataSourceService(dataSourceService);
		return virtuaDataSourceDelegator;
	}

	// 当容器里没有指定的Bean时才创建该Bean
	@Bean
	@ConditionalOnMissingBean
	public DataSource dataSource(VirtuaDataSourceDelegator dataSourceDelegator) {
		VirtualDataSource virtualDataSource = new VirtualDataSource();
		virtualDataSource.setDataSourceDelegator(dataSourceDelegator);
		return virtualDataSource;
	}

	@Bean
	public DynamicDataSourceAspect dynamicDataSourceAspect(DataSourceCallback dataSourceCallback) {
		DynamicDataSourceAspect dynamicDataSourceAspect = new DynamicDataSourceAspect();
		dynamicDataSourceAspect.setDataSourceCallback(dataSourceCallback);
		return dynamicDataSourceAspect;
	}
}
