package help.lixin.framework.auth.metadatasource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import help.lixin.framework.auth.constant.Constants;
import help.lixin.framework.auth.service.IResourceService;

public class UrlAttributeMetadataSource implements FilterInvocationSecurityMetadataSource {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private IResourceService resourceService;

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		List<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
		// 到了这一步,代表上下文中应该要有对象:Authentication
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 获得Authentication中自定义的信息
		Object details = authentication.getDetails();
		if (null != details && details instanceof Map) {
			FilterInvocation fi = (FilterInvocation) object;
			Set<String> resources = resourceService.loadResources((Map) details);
			if (null != resources) {
				resources.forEach(resource -> {
					if (null != resource) {
						ConfigAttribute configAttribute = new SecurityConfig(resource);
						configAttributes.add(configAttribute);
					}
				});
			}
		}
		return configAttributes;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
}
