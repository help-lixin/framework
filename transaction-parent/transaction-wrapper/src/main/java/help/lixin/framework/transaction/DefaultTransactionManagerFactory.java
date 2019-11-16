package help.lixin.framework.transaction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.transaction.PlatformTransactionManager;

public class DefaultTransactionManagerFactory implements PlatformTransactionManagerFactory {

	private Map<String, PlatformTransactionManager> platformTransactionManagers = new HashMap<>();

	@Override
	public PlatformTransactionManager getTransactionManager(String transactionManagerName) {
		return platformTransactionManagers.get(transactionManagerName);
	}

	@Override
	public synchronized void setTransactionManager(String transactionManagerName,
			PlatformTransactionManager transactionManager) {
		PlatformTransactionManager platformTransactionManager = getTransactionManager(transactionManagerName);
		if (null == platformTransactionManager) {
			platformTransactionManagers.put(transactionManagerName, transactionManager);
		}
	}

	@Override
	public void setTransactionManager(Map<String, PlatformTransactionManager> transactionManagers) {
		if (null != transactionManagers && !transactionManagers.isEmpty()) {
			Iterator<Entry<String, PlatformTransactionManager>> it = transactionManagers.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, PlatformTransactionManager> entry = it.next();
				setTransactionManager(entry.getKey(), entry.getValue());
			}
		}
	}
}
