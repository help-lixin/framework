package help.lixin.framework.transaction;

import java.lang.reflect.UndeclaredThrowableException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.support.CallbackPreferringPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionOperations;

public class Executor extends DefaultTransactionDefinition implements TransactionOperations {
	private static final long serialVersionUID = 3718806550019417021L;

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private PlatformTransactionManager transactionManager;

	/**
	 * 定义遇到以下异常进行rollback
	 */
	private Class<? extends Throwable>[] rollbackFor = new Class[] {};

	/**
	 * 定义遇到以下异常不进行rollback
	 */
	private Class<? extends Throwable>[] noRollbackFor = new Class[] {};

	public Class<? extends Throwable>[] getRollbackFor() {
		return rollbackFor;
	}

	public void setRollbackFor(Class<? extends Throwable>[] rollbackFor) {
		this.rollbackFor = rollbackFor;
	}

	public Class<? extends Throwable>[] getNoRollbackFor() {
		return noRollbackFor;
	}

	public void setNoRollbackFor(Class<? extends Throwable>[] noRollbackFor) {
		this.noRollbackFor = noRollbackFor;
	}

	public Executor() {
	}

	public Executor(help.lixin.framework.transaction.TransactionDefinition transactionDefinition) {
		this.setTransactionManager(transactionDefinition.getTransactionManager());
		this.setIsolationLevel(transactionDefinition.getIsolation().value());
		this.setPropagationBehavior(transactionDefinition.getPropagation().value());
		this.setTimeout(transactionDefinition.getTimeout());
		this.setReadOnly(transactionDefinition.isReadOnly());
		this.setRollbackFor(transactionDefinition.getRollbackFor());
		this.setNoRollbackFor(transactionDefinition.getNoRollbackFor());
	}

	/**
	 * Set the transaction management strategy to be used.
	 */
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	/**
	 * Return the transaction management strategy to be used.
	 */
	public PlatformTransactionManager getTransactionManager() {
		return this.transactionManager;
	}

	public void checkValidator() {
		if (this.transactionManager == null) {
			throw new IllegalArgumentException("Property 'transactionManager' is required");
		}
	}

	/**
	 * TODO lixin 需要根据异常情况判断是否rollback.
	 */
	@Override
	public <T> T execute(TransactionCallback<T> action) throws TransactionException {
		// 先检查必要的参数
		checkValidator();
		if (this.transactionManager instanceof CallbackPreferringPlatformTransactionManager) {
			return ((CallbackPreferringPlatformTransactionManager) this.transactionManager).execute(this, action);
		} else {
			TransactionStatus status = this.transactionManager.getTransaction(this);
			T result;
			try {
				result = action.doInTransaction(status);
			} catch (RuntimeException ex) {
				// Transactional code threw application exception -> rollback
				rollbackOnException(status, ex);
				throw ex;
			} catch (Error err) {
				// Transactional code threw error -> rollback
				rollbackOnException(status, err);
				throw err;
			} catch (Throwable ex) {
				// Transactional code threw unexpected exception -> rollback
				rollbackOnException(status, ex);
				throw new UndeclaredThrowableException(ex, "TransactionCallback threw undeclared checked exception");
			}
			this.transactionManager.commit(status);
			return result;
		}
	}

	/**
	 * Perform a rollback, handling rollback exceptions properly.
	 * 
	 * @param status
	 *            object representing the transaction
	 * @param ex
	 *            the thrown application exception or error
	 * @throws TransactionException
	 *             in case of a rollback error
	 */
	private void rollbackOnException(TransactionStatus status, Throwable ex) throws TransactionException {
		logger.debug("Initiating transaction rollback on application exception", ex);
		try {
			this.transactionManager.rollback(status);
		} catch (TransactionSystemException ex2) {
			logger.error("Application exception overridden by rollback exception", ex);
			ex2.initApplicationException(ex);
			throw ex2;
		} catch (RuntimeException ex2) {
			logger.error("Application exception overridden by rollback exception", ex);
			throw ex2;
		} catch (Error err) {
			logger.error("Application exception overridden by rollback error", ex);
			throw err;
		}
	}

}
