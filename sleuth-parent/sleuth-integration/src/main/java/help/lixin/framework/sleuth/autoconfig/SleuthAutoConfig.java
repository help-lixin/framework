package help.lixin.framework.sleuth.autoconfig;

import help.lixin.framework.sleuth.config.SleuthConfig;
import help.lixin.framework.sleuth.properties.CustomSamplerProperties;
import help.lixin.framework.sleuth.properties.SleuthLogProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.sleuth.autoconfig.TraceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SleuthConfig.class})
@EnableConfigurationProperties({SleuthLogProperties.class, CustomSamplerProperties.class})
@ConditionalOnProperty(name = "spring.sleuth.log.enabled", havingValue = "true", matchIfMissing = true)
public class SleuthAutoConfig {
    private Logger logger = LoggerFactory.getLogger(SleuthAutoConfig.class);

    private SleuthLogProperties sleuthLogProperties;

    {
        if (logger.isDebugEnabled()) {
            logger.debug("enabled Module [{}] SUCCESS.", SleuthAutoConfig.class.getSimpleName());
        }
    }
}
