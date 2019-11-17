package help.lixin.framework.transaction;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import help.lixin.framework.test.BaseTest;

public class TransactionDefinitionParserDelegatorTest extends BaseTest {

	@Test
	public void testParser() {
		// 获得数据源
		DataSource ds = getDataSource();

		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(ds);
		// 创建事务管理器
		PlatformTransactionManagerFactory platformTransactionManagerFactory = new DefaultTransactionManagerFactory();
		platformTransactionManagerFactory.setTransactionManager("transactionManager", transactionManager);

		// 事务定义解析器
		TransactionDefinitionParserDelegator parserDelegator = new TransactionDefinitionParserDelegator(
				platformTransactionManagerFactory);

		Method[] methods = TestService.class.getMethods();
		for (Method method : methods) {
			if (method.getName().equals("test1") || method.getName().equals("test2")) {
				// 获取到类的名称
				// System.out.println(method.getDeclaringClass().getName());
				// 获取到类上的方法名称
				// System.out.println(method.getName());
				TransactionDefinition transactionDefinition = parserDelegator.parser(method);
				System.out.println(transactionDefinition);
			}
		}
	}
}
