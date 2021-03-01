package help.lixin.framework.liquibase;

import help.lixin.framework.liquibase.config.LiquibaseConfig;
import help.lixin.framework.liquibase.config.LiquibaseShardingJdcbIntegrationConfig;
import help.lixin.framework.liquibase.properties.LiquibaseIntegrationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(LiquibaseIntegrationProperties.class)
@Import({LiquibaseConfig.class, LiquibaseShardingJdcbIntegrationConfig.class
})
public class LiquibaseAutoConfiguration {
    private Logger logger = LoggerFactory.getLogger(LiquibaseAutoConfiguration.class);

    private LiquibaseIntegrationProperties liquibaseIntegrationProperties;

    public LiquibaseAutoConfiguration() {
        if (logger.isDebugEnabled()) {
            logger.debug("enabled Module [{}] SUCCESS.", LiquibaseAutoConfiguration.class.getSimpleName());
        }
    }
}
