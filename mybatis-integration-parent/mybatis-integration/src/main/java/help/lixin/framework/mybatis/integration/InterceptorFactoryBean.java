package help.lixin.framework.mybatis.integration;

import java.util.Map;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class InterceptorFactoryBean implements ApplicationContextAware, FactoryBean<Interceptor[]> {
	private ApplicationContext applicationContext;

	@Override
	public Interceptor[] getObject() throws Exception {
		if (null != applicationContext) {
			Map<String, Interceptor> interceptors = applicationContext.getBeansOfType(Interceptor.class);
			if (null != interceptors && !interceptors.isEmpty()) {
				return (Interceptor[]) interceptors.values().toArray();
			}
		}
		return null;
	}

	@Override
	public Class<?> getObjectType() {
		return Interceptor[].class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
