package help.lixin.framework.test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import help.lixin.framework.test.service.ILanguageService;
import help.lixin.framework.test.service.impl.LanguageServiceMyBatis;

@SpringBootApplication(scanBasePackages = "help.lixin")
public class Application {
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
		// ILanguageService language = ctx.getBean(ILanguageService.class);
		// language.queryAll();
		// language.save(null);
		
		ILanguageService language = ctx.getBean(LanguageServiceMyBatis.class);
		for(int i=0;i<100;i++){
			Thread t = new Thread(){
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(8);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					List<Map> result = language.queryAll();
					System.out.println("====================" + result);
				}
			};
			t.start();
		}
	}
}
