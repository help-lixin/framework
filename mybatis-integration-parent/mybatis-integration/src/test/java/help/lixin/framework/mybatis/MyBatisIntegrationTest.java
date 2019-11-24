package help.lixin.framework.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import help.lixin.framework.dynamic.datasource.context.DataSourceContext;
import help.lixin.framework.mybatis.mapper.LanguageMapper;

@SpringBootApplication(scanBasePackages = "help.lixin")
public class MyBatisIntegrationTest {
	@SuppressWarnings({ "unused", "rawtypes" })
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext cofig = SpringApplication.run(MyBatisIntegrationTest.class, args);
		
		// 测试时指定上下文信息
		DataSourceContext.datasource("test");
		// new VarContext.Builder()
		// .var("_db", "fw").var("_tablePrefix","fw_")
		// .bind();
		
		LanguageMapper languageMapper = cofig.getBean(LanguageMapper.class);
		List<Map> result = languageMapper.query("200");
		System.out.println(result);
	}
}
