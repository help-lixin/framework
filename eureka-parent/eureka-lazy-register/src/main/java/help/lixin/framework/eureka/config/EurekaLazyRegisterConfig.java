package help.lixin.framework.eureka.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import help.lixin.framework.eureka.properties.EurekaLazyRegisterProperties;
import help.lixin.framework.eureka.serviceregistry.EurekaAutoServiceRegistrationOverrideListener;
import help.lixin.framework.eureka.serviceregistry.EurekaAutoServiceRegistrationOverrideProcessor;

@Configuration
public class EurekaLazyRegisterConfig {
	private Logger logger = LoggerFactory.getLogger(EurekaLazyRegisterConfig.class);

	/**
	 * 1. 开启了延迟注册. <br/> 
	 * 2. Classpath下存:org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration类
	 * 只有两者条件都满足的情况下:<br/>
	 * 1. 会把Spring容器中的:EurekaAutoServiceRegistration类,从容器中移除<br/>
	 * 2.
	 * 重新添加:EurekaAutoServiceRegistrationOverride(EurekaAutoServiceRegistrationOverride对EurekaAutoServiceRegistration进行了代理).
	 * <br/>
	 * 3.
	 * 注意:EurekaAutoServiceRegistrationOverrideProcessor方法,做了Bean(EurekaAutoServiceRegistrationOverride)导入的事情,你不需要再把EurekaAutoServiceRegistrationOverride向Spring进行注册.<br/>
	 *
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "eureka.lazy", name = "enabled", havingValue = "true")
	@ConditionalOnClass(name = "org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration")
	@ConditionalOnBean(EurekaServiceRegistry.class)
	public EurekaAutoServiceRegistrationOverrideProcessor eurekaAutoServiceRegistrationOverrideProcessor() {
		return new EurekaAutoServiceRegistrationOverrideProcessor();
	}

	/**
	 * 对EurekaAutoServiceRegistration进行监听.
	 *
	 * @param eurekaAutoServiceRegistration
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "eureka.lazy", name = "enabled", havingValue = "true")
	@ConditionalOnBean(EurekaServiceRegistry.class)
	public EurekaAutoServiceRegistrationOverrideListener eurekaAutoServiceRegistrationOverrideListener(
			EurekaAutoServiceRegistration eurekaAutoServiceRegistration,
			EurekaLazyRegisterProperties eurekaLazyRegisterProperties) {
		return new EurekaAutoServiceRegistrationOverrideListener(eurekaAutoServiceRegistration,
				eurekaLazyRegisterProperties.getCheckSeconds());
	}
}
