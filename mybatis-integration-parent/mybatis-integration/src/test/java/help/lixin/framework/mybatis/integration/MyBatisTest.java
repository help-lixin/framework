package help.lixin.framework.mybatis.integration;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;

import help.lixin.framework.mybatis.interceptor.QueryContextInjectInterceptor;
import help.lixin.framework.mybatis.interceptor.UpdateContextInterceptor;
import help.lixin.framework.mybatis.mapper.LanguageMapper;
import help.lixin.framework.mybatis.model.Language;
import help.lixin.framework.test.BaseTest;

public class MyBatisTest extends BaseTest {

	@SuppressWarnings("rawtypes")
	@Test
	public void testHello() {
		DruidDataSource ds = getDataSource();
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment("development", transactionFactory, ds);
		
		Configuration configuration = new Configuration(environment);
		configuration.setLogImpl(null);
		configuration.addMapper(LanguageMapper.class);
		configuration.addInterceptor(new QueryContextInjectInterceptor());
		configuration.addInterceptor(new UpdateContextInterceptor());
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();  

		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		LanguageMapper languageMapper = sqlSession.getMapper(LanguageMapper.class);
//		 List<Map> list = languageMapper.query("1");
//		 System.out.println(list);
		
		Language lanugage = new Language(); 
		lanugage.setId(3000);
		lanugage.setName("TEST");
		lanugage.setCode("test");
		int result = languageMapper.save(lanugage);
		// 需要手动提交.否则看不到数据
		sqlSession.commit();
		sqlSession.close();
		System.out.println(result);
	}
}
