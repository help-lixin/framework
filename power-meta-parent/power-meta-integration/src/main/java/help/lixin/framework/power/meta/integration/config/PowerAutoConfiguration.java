package help.lixin.framework.power.meta.integration.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import help.lixin.framework.power.meta.integration.bootstrapper.CollectionPowerMetaBootstrapper;
import help.lixin.framework.power.meta.integration.model.DefaultPowerStore;
import help.lixin.framework.power.meta.integration.model.PowerStore;
import help.lixin.framework.power.meta.integration.properties.PowerProperties;

@Configuration
@EnableConfigurationProperties(PowerProperties.class)
@ConditionalOnProperty(prefix = "power", name = "enable", havingValue = "true")
@ConditionalOnWebApplication
public class PowerAutoConfiguration {

	@Bean
	public PowerStore powerStore() {
		return new DefaultPowerStore();
	}

	/**
	 * 收集元数据处理
	 * 
	 * @param requestMappingHandlerMapping
	 * @param powerStore
	 * @return
	 */
	@Bean
	public CollectionPowerMetaBootstrapper collectionPowerMetaBootstrapper( //
			RequestMappingHandlerMapping requestMappingHandlerMapping, //
			PowerStore powerStore) {
		CollectionPowerMetaBootstrapper collectionPowerMetaBootstrapper = new CollectionPowerMetaBootstrapper();
		collectionPowerMetaBootstrapper.setRequestMappingHandlerMapping(requestMappingHandlerMapping);
		collectionPowerMetaBootstrapper.setPowerStore(powerStore);
		return collectionPowerMetaBootstrapper;
	}
}
