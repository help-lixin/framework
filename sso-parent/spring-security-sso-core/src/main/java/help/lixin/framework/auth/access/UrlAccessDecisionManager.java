package help.lixin.framework.auth.access;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;

public class UrlAccessDecisionManager implements AccessDecisionManager, InitializingBean, MessageSourceAware {
	protected final Log logger = LogFactory.getLog(getClass());
	private List<AccessDecisionVoter<? extends Object>> decisionVoters;
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private boolean allowIfAllAbstainDecisions = false;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		int deny = 0;
		for (AccessDecisionVoter voter : getDecisionVoters()) {
			int result = voter.vote(authentication, object, configAttributes);
			if (logger.isDebugEnabled()) {
				logger.debug("Voter: " + voter + ", returned: " + result);
			}
			switch (result) {
			case AccessDecisionVoter.ACCESS_GRANTED:
				return;
			case AccessDecisionVoter.ACCESS_DENIED:
				deny++;
				break;
			default:
				break;
			}
		}
		if (deny > 0) {
			throw new AccessDeniedException(
					messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
		}
		checkAllowIfAllAbstainDecisions();
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notEmpty(this.decisionVoters, "A list of AccessDecisionVoters is required");
		Assert.notNull(this.messages, "A message source must be set");
	}

	protected final void checkAllowIfAllAbstainDecisions() {
		if (!this.isAllowIfAllAbstainDecisions()) {
			throw new AccessDeniedException(
					messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
		}
	}

	public List<AccessDecisionVoter<? extends Object>> getDecisionVoters() {
		return this.decisionVoters;
	}
	
	public void setDecisionVoters(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
		this.decisionVoters = decisionVoters;
	}

	public boolean isAllowIfAllAbstainDecisions() {
		return allowIfAllAbstainDecisions;
	}

	public void setAllowIfAllAbstainDecisions(boolean allowIfAllAbstainDecisions) {
		this.allowIfAllAbstainDecisions = allowIfAllAbstainDecisions;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	public boolean supports(ConfigAttribute attribute) {
		for (AccessDecisionVoter voter : this.decisionVoters) {
			if (voter.supports(attribute)) {
				return true;
			}
		}

		return false;
	}

	public boolean supports(Class<?> clazz) {
		for (AccessDecisionVoter voter : this.decisionVoters) {
			if (!voter.supports(clazz)) {
				return false;
			}
		}

		return true;
	}
}
