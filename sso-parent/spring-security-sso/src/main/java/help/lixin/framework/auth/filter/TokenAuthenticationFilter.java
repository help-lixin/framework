package help.lixin.framework.auth.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import help.lixin.framework.auth.constant.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

/**
 * jwt token 鉴权管理
 * 
 * @author lixin
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String AUTHORIZATION = "authorization";

	private AuthenticationEntryPoint authenticationEntryPoint;

	public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	public AuthenticationEntryPoint getAuthenticationEntryPoint() {
		return authenticationEntryPoint;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse resp = (HttpServletResponse) servletResponse;

		// 判断协议头或者请求参数中是否存在有:authorization
		String jwtToken = req.getHeader(AUTHORIZATION);
		if (StringUtils.isEmpty(jwtToken)) {
			jwtToken = req.getParameter(AUTHORIZATION);
		}
		if (!StringUtils.isEmpty(jwtToken)) {
			logger.info("start jwt token filter:[{}]", jwtToken);
			try {
				Claims claims = Jwts.parser() //
						.setSigningKey("123456") // 设置密钥
						.parseClaimsJws(jwtToken.replace("Bearer", "")) // 替换协议头的前缀(Bearer)
						.getBody();
				// 用户名
				String username = claims.getSubject();// 获取当前登录用户名
				// 用户信息ID
				Long userInfoId = claims.get(Constants.USER_INFO_ID_KEY, Long.class);
				// 租户ID
				Long tenantId = claims.get(Constants.TENANT_ID_KEY, Long.class);
				logger.info("userName:[{}] request access url:[{}]", username, req.getRequestURI());

				// 包装用户信息和租户ID,传递到:AccessDecisionManager处理.
				Map<String, Long> details = new HashMap<>(2);
				details.put(Constants.USER_INFO_ID_KEY, userInfoId);
				details.put(Constants.TENANT_ID_KEY, tenantId);

				// TODO
				// 注意:authorities不可以为Null.否则,拦截器会出现莫名情况,需要跟源码查看具体情况.
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null,
						authorities);
				token.setDetails(details);
				SecurityContextHolder.getContext().setAuthentication(token);
			} catch (ExpiredJwtException | SignatureException exception) {
				// JWT过期时进行统一的跳转处理
				authenticationEntryPoint.commence(req, resp, new AccountExpiredException(exception.getMessage()));
				return;
			}
		}
		filterChain.doFilter(req, servletResponse);
	}
}
