package help.lixin.framework.bean.definition;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 框架层定义统一的Bean注册.
 * 
 * @author lixin
 */
public class CommonBeanDefinitionRegistry implements ImportBeanDefinitionRegistrar, EnvironmentAware {

	private Environment environment;

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment, "bean.definition.registry.");
		String beanDefinitionCallbacks = propertyResolver.getProperty("class", String.class);
		if (StringUtils.isNotBlank(beanDefinitionCallbacks)) {
			String[] classNames = StringUtils.split(beanDefinitionCallbacks, ",");
			for (String className : classNames) {
				try {
					Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
					ICommonBeanDefinitionRegistry callback = (ICommonBeanDefinitionRegistry) clazz.newInstance();
					callback.registerBeanDefinitions(environment, importingClassMetadata, registry);
				} catch (Exception e) {
					// TODO logger
				}
			}
		}
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}
