package help.lixin.framework.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 认证处理器
 * 
 * @author lixin
 *
 */
public interface AuthenticationHandler {

	/**
	 * 成功处理
	 * 
	 * @param request
	 * @param response
	 * @param authentication
	 * @throws IOException
	 * @throws ServletException
	 */
	void success(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException;

	/**
	 * 失败处理
	 * 
	 * @param request
	 * @param response
	 * @param exception
	 * @throws IOException
	 * @throws ServletException
	 */
	void failure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException;
}
