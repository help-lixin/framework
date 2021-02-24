package help.lixin.framework.route;

import help.lixin.framework.route.config.RouteConfig;
import help.lixin.framework.route.config.RouteMarkerConfiguration;
import help.lixin.framework.route.properties.RouteProperties;
import help.lixin.framework.route.servlet.filter.RouteFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration(RouteConfig.class)
@EnableConfigurationProperties(RouteProperties.class)
@ConditionalOnBean(RouteMarkerConfiguration.Marker.class)
public class RouteAutoConfiguration {
    private Logger logger = LoggerFactory.getLogger(RouteAutoConfiguration.class);

    private RouteProperties routeProperties;

    {
        if (logger.isDebugEnabled()) {
            logger.debug("enabled Module [{}] SUCCESS.", RouteAutoConfiguration.class.getSimpleName());
        }
    }
}
