package help.lixin.framework.environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FrameworkAutoConfiguration {
    private Logger logger = LoggerFactory.getLogger(FrameworkAutoConfiguration.class);

    public FrameworkAutoConfiguration() {
        if (logger.isDebugEnabled()) {
            logger.debug("enabled Module [{}] SUCCESS.", FrameworkAutoConfiguration.class.getSimpleName());
        }
    }
}
