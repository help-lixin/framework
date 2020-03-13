package help.lixin.framework.auth.voter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.access.AccessDecisionVoter;

@SuppressWarnings("rawtypes")
public class AccessDecisionVoterFactoryBean
		implements FactoryBean<List<AccessDecisionVoter<? extends Object>>>, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@SuppressWarnings("unchecked")
	@Override
	public List<AccessDecisionVoter<? extends Object>> getObject() throws Exception {
		Map<String, AccessDecisionVoter> accessDecisionVoters = applicationContext
				.getBeansOfType(AccessDecisionVoter.class);
		if (null != accessDecisionVoters) {
			return (List) accessDecisionVoters.values();
		}
		return Collections.emptyList();
	}


	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}


	@Override
	public Class<?> getObjectType() {
		return List.class;
	}
}
