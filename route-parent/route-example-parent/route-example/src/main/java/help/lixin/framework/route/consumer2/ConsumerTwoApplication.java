package help.lixin.framework.route.consumer2;

import help.lixin.framework.route.annotation.EnableRoute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@EnableRoute
@SpringBootApplication(scanBasePackages = "help.lixin.framework.route.consumer2")
public class ConsumerTwoApplication {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active","consumer2");
        SpringApplication.run(ConsumerTwoApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
