package help.lixin.framework.transaction;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionCallback;

public class TransactionTest extends BaseTest {

	public PlatformTransactionManager getPlatformTransactionManager() {
		DataSource ds = getDataSource();
		// 事务统一管理(需要DataSource)
		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(ds);
		return transactionManager;
	}

	@Test
	public void testTransaction() {
		DataSource ds = getDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

		// 事务管理依赖:数据源
		PlatformTransactionManager transactionManager = getPlatformTransactionManager();

		TransactionDefinition defition = new TransactionDefinition
				.Builder()   //
				.transactionManager(transactionManager) //
				.propagation(Propagation.REQUIRED) //
				.isolation(Isolation.DEFAULT) //
				.build();

		// 事务定义依赖:事务管理
		Executor executor = new Executor(defition);
		executor.execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus status) {
				// 业务处理
				for (int i = 0; i < 10; i++) {
					String sql = "INSERT INTO fw_language(name,code,enabled_flag) VALUES(?,?,?)";
					// 关闭自动commit
					int result = jdbcTemplate.update(sql, new Object[] { "lixin", "lixin", "Y" });
					System.out.println(result);
				}
				return null;
			}
		});
		System.out.println("==========================");
	}
}
