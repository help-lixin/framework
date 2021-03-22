package help.lixin.framework.module.boot;

import help.lixin.framework.module.boot.properties.ModuleConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ModuleConfigProperties.class})
public class ModuleStarter {
    public static void main(String[] args) {
        SpringApplication.run(ModuleStarter.class, args);
    }
}
