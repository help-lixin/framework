package help.lixin.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigConfiguration {

	@Bean
	public Config config() {
		return Config.fromResourcePath(ConfigConfiguration.class.getResource("/config.ini").getPath());
	}
}
