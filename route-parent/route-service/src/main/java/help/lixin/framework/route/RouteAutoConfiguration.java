package help.lixin.framework.route;

import help.lixin.framework.route.config.RouteConfig;
import help.lixin.framework.route.properties.RouteProperties;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration(RouteConfig.class)
@EnableConfigurationProperties(RouteProperties.class)
@ConditionalOnProperty(prefix = "customer.route", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RouteAutoConfiguration {
    private  RouteProperties routeProperties;


}
