package help.lixin.framework.transaction.aop.aspectj;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import help.lixin.framework.transaction.Executor;
import help.lixin.framework.transaction.TransactionDefinition;
import help.lixin.framework.transaction.TransactionDefinitionParserDelegator;

@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class TrnsactionAspect {
	// @annotation 拦截的是方法
	// @within 拦截的是类
	@Pointcut("@within(org.springframework.transaction.annotation.Transactional) || @annotation(org.springframework.transaction.annotation.Transactional)")
	public void transactionalAnnotationPointcut() {
	}

	private TransactionDefinitionParserDelegator transactionDefinitionParserDelegator;

	public void setTransactionDefinitionParserDelegator(
			TransactionDefinitionParserDelegator transactionDefinitionParserDelegator) {
		this.transactionDefinitionParserDelegator = transactionDefinitionParserDelegator;
	}

	@Around("transactionalAnnotationPointcut()")
	public Object exec(final ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		//  TODO lixin 考虑要放到缓存里
		TransactionDefinition transactionDefinition = transactionDefinitionParserDelegator.parser(method);
		// 事务定义依赖:事务管理
		Executor executor = new Executor(transactionDefinition);
		Object result = null;
		result = executor.execute(new TransactionCallback<Object>() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					return joinPoint.proceed();
				} catch (Throwable e) {
					// TODO lixin
					e.printStackTrace();
				}
				return null;
			}
		});
		return result;
	}
}
