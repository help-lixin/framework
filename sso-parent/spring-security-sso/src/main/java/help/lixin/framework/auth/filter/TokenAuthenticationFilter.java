package help.lixin.framework.auth.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

/**
 * 鉴权管理
 * 
 * @author lixin
 */
public class TokenAuthenticationFilter extends GenericFilterBean {

	private static final String AUTHORIZATION = "authorization";

	@Autowired
	@Qualifier("loginAuthenticationEntryPoint")
	private AuthenticationEntryPoint authenticationEntryPoint;

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse resp = (HttpServletResponse) servletResponse;
		String jwtToken = req.getHeader(AUTHORIZATION);
		if (StringUtils.isEmpty(jwtToken)) {
			jwtToken = req.getParameter(AUTHORIZATION);
		}
		if (!StringUtils.isEmpty(jwtToken)) {
			try {
				Claims claims = Jwts.parser() //
						.setSigningKey("123456") // 设置密钥
						.parseClaimsJws(jwtToken.replace("Bearer", "")) // 替换协议头的前缀(Bearer)
						.getBody();
				String username = claims.getSubject();// 获取当前登录用户名
				List<GrantedAuthority> authorities = AuthorityUtils
						.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null,
						authorities);
				SecurityContextHolder.getContext().setAuthentication(token);
			} catch (ExpiredJwtException | SignatureException exception) {
				// JWT过期时进行统一的跳转处理
				authenticationEntryPoint.commence(req, resp,
						new org.springframework.security.authentication.AccountExpiredException(
								exception.getMessage()));
				return;
			}
		}
		filterChain.doFilter(req, servletResponse);
	}
}
