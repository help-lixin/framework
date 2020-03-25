package help.lixin.framework.power.meta.integration.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import help.lixin.framework.power.meta.integration.bootstrapper.CollectionPowerMetaBootstrapper;
import help.lixin.framework.power.meta.integration.properties.PowerProperties;

@Configuration
@EnableConfigurationProperties(PowerProperties.class)
@ConditionalOnProperty(prefix = "power", name = "enable", havingValue = "true")
@ConditionalOnWebApplication
public class PowerAutoConfiguration {

	/**
	 * 收集元数据处理
	 * @param requestMappingHandlerMapping
	 * @return
	 */
	@Bean
	public CollectionPowerMetaBootstrapper collectionPowerMetaBootstrapper(RequestMappingHandlerMapping requestMappingHandlerMapping) {
		CollectionPowerMetaBootstrapper collectionPowerMetaBootstrapper = new CollectionPowerMetaBootstrapper();
		collectionPowerMetaBootstrapper.setRequestMappingHandlerMapping(requestMappingHandlerMapping);
		return collectionPowerMetaBootstrapper;
	}
}
