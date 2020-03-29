package help.lixin.framework.power.meta.integration.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import help.lixin.framework.power.meta.integration.bootstrapper.CollectionPowerMetaBootstrapper;
import help.lixin.framework.power.meta.integration.model.DefaultPowerStore;
import help.lixin.framework.power.meta.integration.model.PowerStore;
import help.lixin.framework.power.meta.integration.plugin.Plugin;
import help.lixin.framework.power.meta.integration.plugin.PluginChain;
import help.lixin.framework.power.meta.integration.properties.PowerProperties;

@Configuration
@EnableConfigurationProperties(PowerProperties.class)
@ConditionalOnProperty(prefix = "power", name = "enable", havingValue = "true")
@ConditionalOnWebApplication
@ImportAutoConfiguration(classes = { PluginConfiguration.class })
public class PowerAutoConfiguration {

	@Bean
	public PowerStore powerStore() {
		return new DefaultPowerStore();
	}

	/**
	 * 插件链
	 * 
	 * @return
	 */
	@Bean
	public PluginChain pluginChain(ApplicationContext context) {
		List<Plugin> plugins = new ArrayList<>(0);
		try {
			Map<String, Plugin> pluginBeans = context.getBeansOfType(Plugin.class);
			plugins.addAll(pluginBeans.values());
		} catch (Exception e) {
		}
		PluginChain pluginChain = new PluginChain();
		pluginChain.setPlugins(plugins);
		return pluginChain;
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
			RequestMappingHandlerMapping requestMappingHandlerMapping, // Spring中所有的URL与请求信息
			PowerStore powerStore, // 数据最终落地存储对象
			PluginChain pluginChain // 插件责任链
	) {
		CollectionPowerMetaBootstrapper collectionPowerMetaBootstrapper = new CollectionPowerMetaBootstrapper();
		collectionPowerMetaBootstrapper.setRequestMappingHandlerMapping(requestMappingHandlerMapping);
		collectionPowerMetaBootstrapper.setPowerStore(powerStore);
		collectionPowerMetaBootstrapper.setPluginChain(pluginChain);
		return collectionPowerMetaBootstrapper;
	}
}
