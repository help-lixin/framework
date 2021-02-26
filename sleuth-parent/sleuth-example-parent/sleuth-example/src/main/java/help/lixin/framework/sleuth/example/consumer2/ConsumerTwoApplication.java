package help.lixin.framework.sleuth.example.consumer2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "help.lixin.framework.sleuth.example.consumer2")
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