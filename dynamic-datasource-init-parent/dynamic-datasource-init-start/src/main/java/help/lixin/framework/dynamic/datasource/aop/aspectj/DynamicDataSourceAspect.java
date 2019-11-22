package help.lixin.framework.dynamic.datasource.aop.aspectj;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import help.lixin.framework.dynamic.datasource.DataSourceCallback;
import help.lixin.framework.dynamic.datasource.context.DataSourceContext;

/**
 * 设置动态数据源的名称优先级应该是要比较高的
 * 
 * @author lixin
 */
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class DynamicDataSourceAspect {

	@Pointcut("@within(org.springframework.stereotype.Service)")
	public void dynamicDataSourceServiceAnnotationPointcut() {
	}

	@Pointcut("@within(org.springframework.stereotype.Repository)")
	public void dynamicDataSourceRepositoryAnnotationPointcut() {
	}

	// @annotation 拦截的是方法
	// @within 拦截的是类
	@Pointcut("@within(org.springframework.stereotype.Component)")
	public void dynamicDataSourceComponentAnnotationPointcut() {
	}

	// 业务自己实现具体取哪个数据源信息
	private DataSourceCallback dataSourceCallback;

	public DataSourceCallback getDataSourceCallback() {
		return dataSourceCallback;
	}

	public void setDataSourceCallback(DataSourceCallback dataSourceCallback) {
		this.dataSourceCallback = dataSourceCallback;
	}

	@Around("dynamicDataSourceServiceAnnotationPointcut() || dynamicDataSourceRepositoryAnnotationPointcut()")
	public Object exec(final ProceedingJoinPoint point) throws Throwable {
		// 先获取到原来的数据源名称
		String oldDataSourceName = DataSourceContext.datasource();
		Object target = point.getTarget();
		Class<?> targetClass = target.getClass();
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method targetMethod = signature.getMethod();
		// 获取到新的数据源名称
		String newDataSroueName = dataSourceCallback.datasource(targetClass, targetMethod);
		Object result = null;
		try {
			// 调用之前设置新的数据源名称
			DataSourceContext.datasource(newDataSroueName);
			result = point.proceed();
		} finally {
			// 调用之后还原老的数据源名称
			DataSourceContext.datasource(oldDataSourceName);
		}
		return result;
	}
}
