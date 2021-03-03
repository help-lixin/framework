package help.lixin.framework.liquibase.env;

import help.lixin.framework.environment.ConfigurableEnvironmentContext;
import help.lixin.framework.environment.service.EnvironmentConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

public class LiquibaseEnvironmentConfigService implements EnvironmentConfigService {
    private Logger logger = LoggerFactory.getLogger(LiquibaseEnvironmentConfigService.class);

    private String liquibaseEnabledKey = "spring.liquibase.enabled";
    private String liquibaseEnabledValue = "false";

    @Override
    public void config(Map<String, Object> map) {
        ConfigurableEnvironment environment = ConfigurableEnvironmentContext.getInstance().getConfigurableEnvironment();
        if (null != environment) {
            Boolean disabledSpringLiquibase = Boolean.parseBoolean(environment.getProperty("liquibase.disabledSpringLiquibase", "true"));
            if (disabledSpringLiquibase) {
                logger.warn("start disable spring liquibase....");
                map.put(liquibaseEnabledKey, liquibaseEnabledValue);
            }
        } else {
            logger.warn("ConfigurableEnvironmentContext.getInstance() == null, auto enabled  spring liquibase....");
        }
    }
}
