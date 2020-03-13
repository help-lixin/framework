package help.lixin.framework.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.security.web.util.UrlUtils;

/**
 * 当在:ExceptionTranslationFilter中抛出异常(AccessDeniedException)时调用,包装成: <br/>
 * new InsufficientAuthenticationException("Full authentication is required to access this resource")
 * @author lixin
 */
public class LoginAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
	protected final Log logger = LogFactory.getLog(getClass());
	
	public LoginAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	protected String buildRedirectUrlToLoginPage(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) {
		String loginForm = determineUrlToUseForThisRequest(request, response, authException);
		if (UrlUtils.isAbsoluteUrl(loginForm)) {
			return loginForm;
		}

		String scheme = request.getScheme();
		int serverPort = request.getServerPort();
		
		// 定义向回来的URL
		RedirectUrlBuilder callbackUrlBuilder = new RedirectUrlBuilder();
		callbackUrlBuilder.setScheme(scheme);
		callbackUrlBuilder.setServerName(request.getServerName());
		callbackUrlBuilder.setPort(serverPort);
		callbackUrlBuilder.setContextPath(request.getContextPath());
		callbackUrlBuilder.setPathInfo(request.getRequestURI());
		
		RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
		urlBuilder.setScheme(scheme);
		urlBuilder.setServerName(request.getServerName());
		urlBuilder.setPort(serverPort);
		urlBuilder.setContextPath(request.getContextPath());
		urlBuilder.setPathInfo(loginForm);
		urlBuilder.setQuery("redirect_url="+callbackUrlBuilder.getUrl());
		return urlBuilder.getUrl();
	}
}
