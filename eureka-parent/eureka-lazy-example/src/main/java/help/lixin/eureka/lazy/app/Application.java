package help.lixin.eureka.lazy.app;

import help.lixin.framework.event.RegisterServiceStartEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "help.lixin.eureka.lazy.app")
public class Application {
    public static void main(String[] args) throws Exception{
        System.setProperty("spring.profiles.active", "app");
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

        TimeUnit.SECONDS.sleep(30);

        // 1. 先启动App
        // 2. 再启动eureka-server
        // 3. 看能否会自动注册.
        ctx.publishEvent(new RegisterServiceStartEvent("RegisterServiceStartEvent"));
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
