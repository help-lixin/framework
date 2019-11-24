package help.lixin.framework.transaction;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import help.lixin.framework.transaction.aop.aspectj.TrnsactionAspect;

@Configuration
@ConditionalOnClass(value = { DataSource.class })
@ConditionalOnBean(value = { DataSource.class })
public class TransactionAutoConfiguration {
	// 设置事务管理器
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	// 配置事务管理集
	@Bean
	public PlatformTransactionManagerFactory platformTransactionManagerFactory(
			PlatformTransactionManager transactionManager) {
		PlatformTransactionManagerFactory platformTransactionManagerFactory = new DefaultTransactionManagerFactory();
		platformTransactionManagerFactory.setTransactionManager("transactionManager", transactionManager);
		return platformTransactionManagerFactory;
	}

	// 对@Transactional进行解析
	@Bean
	public TransactionDefinitionParserDelegator transactionDefinitionParserDelegator(
			PlatformTransactionManagerFactory platformTransactionManagerFactory) {
		TransactionDefinitionParserDelegator parserDelegator = new TransactionDefinitionParserDelegator(
				platformTransactionManagerFactory);
		return parserDelegator;
	}

	// 事务拦截器(对Service/Repository/Component进行拦截)
	@Bean
	public TrnsactionAspect trnsactionAspect(
			TransactionDefinitionParserDelegator transactionDefinitionParserDelegator) {
		TrnsactionAspect trnsactionAspect = new TrnsactionAspect();
		trnsactionAspect.setTransactionDefinitionParserDelegator(transactionDefinitionParserDelegator);
		return trnsactionAspect;
	}
}
