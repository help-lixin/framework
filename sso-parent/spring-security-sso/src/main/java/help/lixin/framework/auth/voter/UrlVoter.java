package help.lixin.framework.auth.voter;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;

public class UrlVoter implements AccessDecisionVoter<Object> {

	private AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	public boolean supports(ConfigAttribute attribute) {
		if ((attribute.getAttribute() != null)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class == clazz;
	}

	/**
	 * 
	 * @param attributes 根据用户,获取所拥有的URL信息.
	 */
	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		if (authentication == null) {
			return ACCESS_DENIED;
		}
		if (!(object instanceof FilterInvocation)) {
			return ACCESS_ABSTAIN;
		}
		if (!supports(object.getClass())) {
			return ACCESS_ABSTAIN;
		}
		FilterInvocation filterInvocation = (FilterInvocation) object;
		String requestUrl = filterInvocation.getRequest().getRequestURI();

		for (ConfigAttribute attribute : attributes) {
			if (this.supports(object.getClass())) {
				String url = attribute.getAttribute();
				if (pathMatcher.match(url, requestUrl)) {
					return ACCESS_GRANTED;
				}
			}
		}
		return ACCESS_ABSTAIN;
	}

	Collection<? extends GrantedAuthority> extractAuthorities(Authentication authentication) {
		return authentication.getAuthorities();
	}
}
