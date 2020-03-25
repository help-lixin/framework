package help.lixin.framework.power.meta.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("help.lixin.framework.power")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
