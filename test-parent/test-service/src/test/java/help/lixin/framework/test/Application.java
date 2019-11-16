package help.lixin.framework.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import help.lixin.framework.test.service.ILanguage;

@SpringBootApplication(scanBasePackages = "help.lixin")
public class Application {
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
		ILanguage language = ctx.getBean(ILanguage.class);
//		language.queryAll();
		
		language.save(null);
		
		System.out.println("====================");
	}
}
