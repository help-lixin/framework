package help.lixin.framework.transaction;

import java.lang.reflect.Method;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

public class TransactionDefinitionParserDelegator {

	private PlatformTransactionManagerFactory platformTransactionManagerFactory;

	public TransactionDefinitionParserDelegator(PlatformTransactionManagerFactory platformTransactionManagerFactory) {
		this.platformTransactionManagerFactory = platformTransactionManagerFactory;
	}

	public void setPlatformTransactionManagerFactory(
			PlatformTransactionManagerFactory platformTransactionManagerFactory) {
		this.platformTransactionManagerFactory = platformTransactionManagerFactory;
	}

	public PlatformTransactionManagerFactory getPlatformTransactionManagerFactory() {
		return platformTransactionManagerFactory;
	}

	public TransactionDefinition parser(Method method) {
		boolean isParseClassAnnotation = Boolean.FALSE;
		Transactional transactional = method.getAnnotation(Transactional.class);
		// 1.获取方法上的注解进行解析
		if (null == transactional) {
			isParseClassAnnotation = Boolean.TRUE;
			// 2. 获取该方法对应的类上的注解进行解析
			transactional = method.getDeclaringClass().getAnnotation(Transactional.class);
		}
		if (null == transactional) {
			return null;
		}
		String transactionManagerName = transactional.transactionManager();
		if (StringUtils.isEmpty(transactionManagerName)) {
			transactionManagerName = transactional.value();
			if (StringUtils.isEmpty(transactionManagerName)) {
				transactionManagerName = "transactionManager";
			}
		}

		// 解析获得:PlatformTransactionManager
		// 把获得:PlatformTransactionManager交给业务自己定义.
		PlatformTransactionManager transactionManager = platformTransactionManagerFactory
				.getTransactionManager(transactionManagerName);
		
		// 建议优化:不要创建太多的:TransactionDefinition
		// TODO lixin
		TransactionDefinition transactionDefinition = new TransactionDefinition //
				.Builder().transactionManager(transactionManager) //
						.propagation(transactional.propagation()) //
						.isolation(transactional.isolation()) //
						.timeout(transactional.timeout()) //
						.readOnly(transactional.readOnly()) //
						.rollbackFor(transactional.rollbackFor()) //
						.noRollbackFor(transactional.noRollbackFor()) //
						.build();
		return transactionDefinition;
	}
}
