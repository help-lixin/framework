package help.lixin.framework.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TestService {

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void test1() {

	}

	public void test2() {

	}

}
