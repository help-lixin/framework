package help.lixin.framework.power.meta.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import help.lixin.framework.power.meta.integration.plugin.impl.MenuPlugin;
import help.lixin.framework.power.meta.integration.plugin.impl.MenusPlugin;
import help.lixin.framework.power.meta.integration.plugin.impl.SystemsPlugin;

@Configuration
public class PluginConfiguration {
	/**
	 * 定义系统插件
	 * 
	 * @return
	 */
	@Bean
	public SystemsPlugin systemsPlugin() {
		return new SystemsPlugin();
	}

	/**
	 * 定义菜单插件
	 * 
	 * @return
	 */
	@Bean
	public MenusPlugin menusPlugin() {
		return new MenusPlugin();
	}

	/**
	 * 定义菜单插件
	 * 
	 * @return
	 */
	@Bean
	public MenuPlugin menuPlugin() {
		return new MenuPlugin();
	}
}
