package help.lixin.framework.dynamic.datasource;

import java.lang.reflect.Method;

public interface DataSourceCallback {
	/**
	 * @param targetClass
	 *            目标Class
	 * @param targetMethod
	 *            目标方法
	 * @return 数据源的名称
	 */
	String datasource(Class<?> targetClass, Method targetMethod);
}
