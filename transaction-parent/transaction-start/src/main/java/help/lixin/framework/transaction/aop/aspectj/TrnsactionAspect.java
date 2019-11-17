package help.lixin.framework.transaction.aop.aspectj;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import help.lixin.framework.transaction.Executor;
import help.lixin.framework.transaction.TransactionDefinition;
import help.lixin.framework.transaction.TransactionDefinitionParserDelegator;

@Aspect
public class TrnsactionAspect {
	@Pointcut("@annotation(org.springframework.stereotype.Service)")
	public void serviceAnnotationPointcut() {
	}

	// 这个时候引入Dubbo注解是否合适?
	// TODO lixin
	@Pointcut("@annotation(org.apache.dubbo.config.annotation.Service)")
	public void dubboServiceAnnotationPointcut() {
	}

	@Pointcut("@annotation(org.springframework.stereotype.Repository)")
	public void repositoryAnnotationPointcut() {
	}

	@Pointcut("@annotation(org.springframework.stereotype.Component)")
	public void componentAnnotationPointcut() {
	}
	
	private TransactionDefinitionParserDelegator transactionDefinitionParserDelegator;

	
	public void setTransactionDefinitionParserDelegator(
			TransactionDefinitionParserDelegator transactionDefinitionParserDelegator) {
		this.transactionDefinitionParserDelegator = transactionDefinitionParserDelegator;
	}

	@Around("serviceAnnotationPointcut() || dubboServiceAnnotationPointcut() || repositoryAnnotationPointcut() || componentAnnotationPointcut()")
	public Object exec(final ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
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
					e.printStackTrace();
				}
				return null;
			}
		});
		return result;
	}
}
