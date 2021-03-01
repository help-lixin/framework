package help.lixin.framework.eureka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import help.lixin.framework.eureka.config.EurekaLazyRegisterConfig;
import help.lixin.framework.eureka.properties.EurekaLazyRegisterProperties;

@Configuration
@EnableConfigurationProperties(EurekaLazyRegisterProperties.class)
@Import({EurekaLazyRegisterConfig.class})
@ConditionalOnProperty(prefix = "eureka.lazy", name = "enabled", havingValue = "true", matchIfMissing = true)
public class EurekaLazyRegisterAutoConfiguration {
    private Logger logger = LoggerFactory.getLogger(EurekaLazyRegisterAutoConfiguration.class);

    public EurekaLazyRegisterAutoConfiguration() {
        if (logger.isDebugEnabled()) {
            logger.debug("enabled Module [{}] SUCCESS.", EurekaLazyRegisterAutoConfiguration.class.getSimpleName());
        }
    }
}
