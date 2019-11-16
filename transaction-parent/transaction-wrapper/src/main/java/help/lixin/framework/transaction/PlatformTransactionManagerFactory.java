package help.lixin.framework.transaction;

import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;

public interface PlatformTransactionManagerFactory {
	PlatformTransactionManager getTransactionManager(String transactionManagerName);

	void setTransactionManager(String transactionManagerName, PlatformTransactionManager transactionManager);

	void setTransactionManager(Map<String, PlatformTransactionManager> transactionManagers);
}
