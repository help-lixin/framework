package help.lixin.framework.event.config;

import help.lixin.framework.event.subscribe.SubscribeRegisterServiceStartEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestEventConfig {

    @Bean
    public SubscribeRegisterServiceStartEvent subscribeRegisterServiceStartEvent(){
        return new SubscribeRegisterServiceStartEvent();
    }
}
