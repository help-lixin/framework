package help.lixin.framework.auth.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import help.lixin.framework.auth.constant.LoginMode;
import help.lixin.framework.auth.context.LoginModeContext;
import help.lixin.framework.auth.user.UserDetail;

/**
 * 用户表单登录
 * 
 * @author lixin
 *
 */
public class UserFormLoginFilter extends AbstractAuthenticationProcessingFilter {
	private Logger logger = LoggerFactory.getLogger(getClass());
	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;

	public UserFormLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
		setAuthenticationManager(authenticationManager);
	}

	public void setUsernameParameter(String usernameParameter) {
		this.usernameParameter = usernameParameter;
	}

	public String getUsernameParameter() {
		return usernameParameter;
	}

	public void setPasswordParameter(String passwordParameter) {
		this.passwordParameter = passwordParameter;
	}

	public String getPasswordParameter() {
		return passwordParameter;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp)
			throws AuthenticationException, IOException, ServletException {
		String username = req.getParameter(usernameParameter);
		String password = req.getParameter(passwordParameter);
		UserDetail user = UserDetail.newBuilder().username(username).password(password).build();
		Authentication authentication = null;
		try {
			logger.info("username:[{}] , request login:[{}]", username, req.getRequestURI());
			// 配置为通用模式登录(非第三方登录)
			LoginModeContext.setMode(LoginMode.GENERAL.name());
			authentication = getAuthenticationManager()
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		} finally {
			LoginModeContext.clean();
		}
		return authentication;
	}
}
