package help.lixin.framework.auth.hanlder.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import help.lixin.framework.auth.filter.Constants;
import help.lixin.framework.auth.handler.AuthenticationHandler;
import help.lixin.framework.auth.user.UserDetail;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class LocalAuthenticationHandler implements AuthenticationHandler {

	@Override
	public void success(HttpServletRequest request, HttpServletResponse response, Authentication authResult)
			throws IOException, ServletException {
		UserDetail userDetail = (UserDetail) authResult.getPrincipal();
		
		// TODO 是否应该变成token?JWT到底是否适合?如何保证安全?以及退出如何处理?
		
		String jwt = Jwts.builder() //
				.claim(Constants.USER_INFO_ID_KEY, userDetail.getUserInfoId()) // 用户ID
				.setSubject(authResult.getName()) // 主体信息(账户名称)
				.setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 过期时间
				.signWith(SignatureAlgorithm.HS512, "123456") // 签名处理
				.compact();
		
		// response.setContentType("application/json;charset=utf-8");
		// Map<String, Object> datas = new LinkedHashMap<String, Object>(3);
		// datas.put("code", 200);
		// datas.put("msg", "登录成功");
		// datas.put("token", jwt);
		// PrintWriter out = response.getWriter();
		// out.write(new ObjectMapper().writeValueAsString(datas));
		// out.flush();
		// out.close();
		
		// 如果用户有指定:redirect_url,则重定义回到指定的:redirect_url请求.
		String redirectUrl = request.getParameter("redirect_url");
		if (null != redirectUrl) {
			String location = redirectUrl + "?authorization=" + jwt;
			response.sendRedirect(location);
		}
	}

	@Override
	public void failure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write("登录失败!");
		out.flush();
		out.close();
	}
}
