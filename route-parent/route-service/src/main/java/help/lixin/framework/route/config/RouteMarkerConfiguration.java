package help.lixin.framework.route.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteMarkerConfiguration {
    @Bean
    public Marker routeMarkerBean() {
        return new Marker();
    }

    public class Marker {
    }
}
