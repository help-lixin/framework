package help.lixin.framework.sleuth.example.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "help.lixin.framework.sleuth.example.zipkin")
public class ZipkinApplication {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active","zipkin");
        SpringApplication.run(ZipkinApplication.class, args);
    }
}