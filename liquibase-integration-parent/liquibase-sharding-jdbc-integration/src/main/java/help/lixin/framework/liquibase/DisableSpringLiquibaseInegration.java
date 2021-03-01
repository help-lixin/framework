package help.lixin.framework.liquibase;

import help.lixin.framework.liquibase.properties.LiquibaseIntegrationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

public class DisableSpringLiquibaseInegration implements EnvironmentPostProcessor {
    private Logger logger = LoggerFactory.getLogger(DisableSpringLiquibaseInegration.class);

    private String key = "spring.liquibase.enabled";
    private String value = "false";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Boolean disabledSpringLiquibase = Boolean.parseBoolean(environment.getProperty("liquibase.disabledSpringLiquibase", "true"));
        if (disabledSpringLiquibase) {
            logger.warn("start disable spring liquibase....");
            String disableSpringLiquibae = "disableSpringLiquibaeConfig";
            Map<String, Object> datas = new HashMap<>();
            datas.put(key, value);
            PropertySource<?> propertySource = new MapPropertySource(disableSpringLiquibae, datas);
            environment.getPropertySources().addFirst(propertySource);
        }
    }
}
