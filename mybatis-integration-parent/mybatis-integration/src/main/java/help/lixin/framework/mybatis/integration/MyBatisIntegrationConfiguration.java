package help.lixin.framework.mybatis.integration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;

import help.lixin.framework.config.Config;

@Configuration
@ConditionalOnClass(value = { DataSource.class, SqlSessionFactory.class })
@ConditionalOnBean(value = { Config.class, DataSource.class })
public class MyBatisIntegrationConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(MyBatisIntegrationConfiguration.class);

	/**
	 * 需要扫描的Mapper路径
	 * 
	 * @param config
	 * @return
	 */
	@Bean
	public Resource[] mapperResources(Config config) {
		String path = config.getSectionProperty("mybatis", "mapperLocations");
		String[] paths = path.split(",");
		ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		List<Resource> resources = new ArrayList<Resource>();
		if (null != paths) {
			for (String mapperLocation : paths) {
				try {
					Resource[] mappers = resourceResolver.getResources(mapperLocation);
					resources.addAll(Arrays.asList(mappers));
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return resources.toArray(new Resource[resources.size()]);
	}

	/**
	 * 用户自定义的拦截器
	 * 
	 * @return
	 */
	@Bean
	public InterceptorFactoryBean interceptors() {
		// 把:Interceptor留给业务去做,因为:Interceptor需要的属性(setProperties(Propertis
		// properties))多元化,留给业务自己去注入属性.
		InterceptorFactoryBean interceptorFactoryBean = new InterceptorFactoryBean();
		return interceptorFactoryBean;
	}

	/**
	 * 类型别名支持
	 * 
	 * @param config
	 * @return
	 */
	@Bean
	public Class<?> typeAliasesSuperType(Config config) {
		String typeAliasesSuperTypeClazz = config.getSectionProperty("mybatis", "typeAliasesSuperType");
		if (!StringUtils.isEmpty(typeAliasesSuperTypeClazz)) {
			try {
				return Class.forName(typeAliasesSuperTypeClazz);
			} catch (ClassNotFoundException ignore) {
			}
		}
		return null;
	}

	/**
	 * 创建SqlSessionFactory
	 * 
	 * @param config
	 * @param dataSource
	 * @param mapperResources
	 * @param interceptors
	 * @param typeAliasesSuperType
	 * @return
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory(Config config, // 配置
			DataSource dataSource, // 数据源
			Resource[] mapperResources, // Mapper.xml路径
			Interceptor[] interceptors, // 拦截器
			Class<?> typeAliasesSuperType) throws Exception {
		// 多个之间用","号分隔
		String typeAliasesPackage = config.getSectionProperty("mybatis", "typeAliasesPackage");
		String typeHandlersPackage = config.getSectionProperty("mybatis", "typeHandlersPackage");
		logger.debug("use setting typeAliasesPackage:[{}] , typeHandlersPackage:[{}]", typeAliasesPackage,
				typeHandlersPackage);
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(mapperResources);
		sqlSessionFactoryBean.setPlugins(interceptors);
		sqlSessionFactoryBean.setTypeHandlersPackage(typeHandlersPackage);
		sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
		return sqlSessionFactoryBean.getObject();
	}

	/**
	 * 创建SqlSessionTemplate
	 * 
	 * @param config
	 * @param sqlSessionFactory
	 * @return
	 */
//	@Bean
//	@ConditionalOnMissingBean
	public SqlSessionTemplate sqlSessionTemplate(Config config, SqlSessionFactory sqlSessionFactory) {
		String executorTypeString = config.getSectionProperty("mybatis", "executorType");
		logger.debug("use setting ExecutorType:[{}]", executorTypeString);
		ExecutorType executorType = null;
		try {
			executorType = ExecutorType.valueOf(executorTypeString);
		} catch (Exception ignore) {
		}
		if (executorType != null) {
			return new SqlSessionTemplate(sqlSessionFactory, executorType);
		} else {
			return new SqlSessionTemplate(sqlSessionFactory);
		}
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer(Config config) {
		// 多包之间用逗号(,)分隔
		String basePackage = config.getSectionProperty("mybatis", "basePackage");
		logger.debug("use setting scan package :[{}]", basePackage);
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		// 指定要扫描的包
		mapperScannerConfigurer.setBasePackage(basePackage);
		// 指定扫描哪些注解
		mapperScannerConfigurer.setAnnotationClass(Mapper.class);
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//		mapperScannerConfigurer.setSqlSessionTemplateBeanName("sqlSessionTemplate");
		return mapperScannerConfigurer;
	}

}
