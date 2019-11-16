package help.lixin.framework.transaction;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import help.lixin.framework.transaction.TransactionDefinition;

/**
 * 事务定义中心
 * 
 * @author lixin
 *
 */
public class TransactionDefinitionMediator {

	private static final LoadingCache<String, TransactionDefinition> transactionDefinitions = CacheBuilder //
			.newBuilder() //
			.build(new CacheLoader<String, TransactionDefinition>() {
				@Override
				public TransactionDefinition load(String method) throws Exception {
					return null;
				}
			});

	/**
	 * 根据方法名称找到对应的事务定义信息
	 * 
	 * @param method
	 * @return
	 */
	public static TransactionDefinition getTransactionDefinition(String method) {
		try {
			return transactionDefinitions.get(method);
		} catch (ExecutionException e) {
			// TODO lixin
		}
		return null;
	}

	/**
	 * 根据key清除缓存信息
	 * 
	 * @param method
	 */
	public static void invalidate(String method) {
		transactionDefinitions.invalidate(method);
	}

	/**
	 * 清除缓存中的有的信息
	 */
	public static void invalidateAll() {
		transactionDefinitions.invalidateAll();
	}
}