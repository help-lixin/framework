package help.lixin.framework.environment;

import help.lixin.framework.environment.data.FrameworkEnvironmentMap;
import help.lixin.framework.environment.service.EnvironmentConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import sun.misc.Service;

import java.util.Iterator;
import java.util.Map;

public class FrameworkEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private Logger logger = LoggerFactory.getLogger(FrameworkEnvironmentPostProcessor.class);

    private static final String name = "_frameworkEnv";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        ConfigurableEnvironmentContext.getInstance().setConfigurableEnvironment(environment);
        Map<String, Object> frameworkEnvironment = FrameworkEnvironmentMap.getInstance().getFrameworkEnvironment();
        try {
            Iterator<EnvironmentConfigService> environmentConfigs = Service.providers(EnvironmentConfigService.class);
            while (environmentConfigs.hasNext()) {
                EnvironmentConfigService environmentConfigService = environmentConfigs.next();
                try {
                    environmentConfigService.config(frameworkEnvironment);
                } catch (Exception ignore) {
                    logger.warn("call [{}],exception:[{}]", environmentConfigService, ignore);
                }
            }
        } catch (Exception ignore) {
        }
        // 添加到环境变量中
        PropertySource<?> propertySource = new MapPropertySource(name, frameworkEnvironment);
        environment.getPropertySources().addFirst(propertySource);
    }
}
