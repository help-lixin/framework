package help.lixin.framework.transaction;

import org.junit.Test;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

public class TransactionManagerTest {
	@Test
	public void testTransactionManager() {
		TransactionDefinition transaction1 = new TransactionDefinition
		.Builder()
		.propagation(Propagation.REQUIRED)
		.isolation(Isolation.DEFAULT)
		.timeout(60000)
		.readOnly(Boolean.FALSE)
		.build();
		
		TransactionDefinition transaction2 = new TransactionDefinition
				.Builder()
				.propagation(Propagation.REQUIRED)
				.isolation(Isolation.DEFAULT)
				.timeout(60000)
				.readOnly(Boolean.TRUE)
				.build();
		
		System.out.println(transaction1 == transaction2);
	}
}
