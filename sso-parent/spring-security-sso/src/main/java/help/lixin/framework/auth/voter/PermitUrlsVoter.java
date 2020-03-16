package help.lixin.framework.auth.voter;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;

/**
 * 允许通行的URL
 * 
 * @author lixin
 *
 */
public class PermitUrlsVoter implements AccessDecisionVoter<Object> {

	private AntPathMatcher pathMatcher = new AntPathMatcher();

	private String[] permitUrls = null;

	public void setPermitUrls(String[] permitUrls) {
		this.permitUrls = permitUrls;
	}

	public String[] getPermitUrls() {
		return permitUrls;
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class == clazz;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		if (!(object instanceof FilterInvocation)) {
			return ACCESS_ABSTAIN;
		}

		if (!supports(object.getClass())) {
			return ACCESS_ABSTAIN;
		}

		if (null == permitUrls) {
			return ACCESS_ABSTAIN;
		}

		FilterInvocation filterInvocation = (FilterInvocation) object;
		String requestUrl = filterInvocation.getRequest().getRequestURI();
		for (String permitUrl : permitUrls) {
			if (pathMatcher.match(permitUrl, requestUrl)) {
				return ACCESS_GRANTED;
			}
		}
		return ACCESS_ABSTAIN;
	}

}
