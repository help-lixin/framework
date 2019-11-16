package help.lixin.framework.transaction;

import java.util.Arrays;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

class TransactionDefinition {
	// 默认的超时时间
	private static final int TIMEOUT_DEFAULT = -1;

	// 默认是否为只读
	private static final Boolean READONLY_DEFAULT = Boolean.FALSE;

	@SuppressWarnings("unchecked")
	// 默认异常rocllback
	private static final Class<? extends Throwable>[] ROLLBACK_FOR_DEFAULT_CLASS = new Class[] { RuntimeException.class,
			Exception.class };

	/**
	 * 事务管理器
	 */
	private PlatformTransactionManager transactionManager = null;

	/**
	 * 事务传播行为
	 */
	private Propagation propagation = Propagation.REQUIRED;

	/**
	 * 事务隔离级别
	 */
	private Isolation isolation = Isolation.DEFAULT;

	/**
	 * 超时时间
	 */
	private int timeout = TransactionDefinition.TIMEOUT_DEFAULT;

	/**
	 * 是否为只读
	 */
	private boolean readOnly = TransactionDefinition.READONLY_DEFAULT;

	/**
	 * 当遇到如下异常,进行事务的rollback
	 */
	private Class<? extends Throwable>[] rollbackFor = TransactionDefinition.ROLLBACK_FOR_DEFAULT_CLASS;

	private Class<? extends Throwable>[] noRollbackFor;

	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public Propagation getPropagation() {
		return propagation;
	}

	void setPropagation(Propagation propagation) {
		this.propagation = propagation;
	}

	public Isolation getIsolation() {
		return isolation;
	}

	void setIsolation(Isolation isolation) {
		this.isolation = isolation;
	}

	public int getTimeout() {
		return timeout;
	}

	void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Class<? extends Throwable>[] getRollbackFor() {
		return rollbackFor;
	}

	void setRollbackFor(Class<? extends Throwable>[] rollbackFor) {
		this.rollbackFor = rollbackFor;
	}

	public Class<? extends Throwable>[] getNoRollbackFor() {
		return noRollbackFor;
	}

	void setNoRollbackFor(Class<? extends Throwable>[] noRollbackFor) {
		this.noRollbackFor = noRollbackFor;
	}

	public static class Builder {
		private TransactionDefinition transactionDefinition = new TransactionDefinition();

		// 设置事务管理器名称
		public Builder transactionManager(PlatformTransactionManager transactionManager) {
			transactionDefinition.setTransactionManager(transactionManager);
			return this;
		}

		// 设置事务传播行为
		public Builder propagation(Propagation propagation) {
			transactionDefinition.setPropagation(propagation);
			return this;
		}

		// 设置隔离级别
		public Builder isolation(Isolation isolation) {
			transactionDefinition.setIsolation(isolation);
			return this;
		}

		// 设置超时时间
		public Builder timeout(int timeout) {
			transactionDefinition.setTimeout(timeout);
			return this;
		}

		// 设置是否只读
		public Builder readOnly(boolean readOnly) {
			transactionDefinition.setReadOnly(readOnly);
			return this;
		}

		// rollback
		public Builder rollbackFor(Class<? extends Throwable>[] rollbackFor) {
			transactionDefinition.setRollbackFor(rollbackFor);
			return this;
		}

		// 非rollbackFor
		public Builder noRollbackFor(Class<? extends Throwable>[] noRollbackFor) {
			transactionDefinition.setNoRollbackFor(noRollbackFor);
			return this;
		}

		public TransactionDefinition build() {
			if (null == this.transactionDefinition.getTransactionManager()) {
				throw new IllegalArgumentException("Property 'transactionManager' is required");
			}
			return transactionDefinition;
		}
	}

	@Override
	public String toString() {
		return "TransactionDefinition [transactionManager=" + transactionManager + ", propagation=" + propagation
				+ ", isolation=" + isolation + ", timeout=" + timeout + ", readOnly=" + readOnly + ", rollbackFor="
				+ Arrays.toString(rollbackFor) + ", noRollbackFor=" + Arrays.toString(noRollbackFor) + "]";
	}
}
