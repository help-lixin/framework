package help.lixin.framework.auth.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import help.lixin.framework.auth.LoginAuthenticationEntryPoint;
import help.lixin.framework.auth.access.UrlAccessDecisionManager;
import help.lixin.framework.auth.filter.Constants;
import help.lixin.framework.auth.filter.OverrideFilterSecurityInterceptor;
import help.lixin.framework.auth.filter.TokenAuthenticationFilter;
import help.lixin.framework.auth.filter.UserFormLoginFilter;
import help.lixin.framework.auth.metadatasource.UrlAttributeMetadataSource;
import help.lixin.framework.auth.properties.SecurityProperties;
import help.lixin.framework.auth.service.IResourceService;
import help.lixin.framework.auth.service.IUserDetailService;
import help.lixin.framework.auth.service.impl.CacheResourceService;
import help.lixin.framework.auth.service.impl.CacheUserDetailService;
import help.lixin.framework.auth.service.impl.UserDetailServiceImpl;
import help.lixin.framework.auth.user.UserDetail;
import help.lixin.framework.auth.voter.UrlVoter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@EnableConfigurationProperties(SecurityProperties.class)
@EnableWebSecurity // 这个注解必须加，开启Security
// @EnableGlobalMethodSecurity(prePostEnabled = true) // 保证post之前的注解可以使用
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Bean
	public SecurityProperties securityProperties() {
		return new SecurityProperties();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public IUserDetailService cacheUserDetailService() {
		CacheUserDetailService cacheUserDetailService = new CacheUserDetailService();
		cacheUserDetailService.setUserCacheExpireMinutes(securityProperties.getUserCacheExpireMinutes());
		IUserDetailService userDetailServiceImpl = getApplicationContext().getBean("userDetailService",
				IUserDetailService.class);
		cacheUserDetailService.setDelegatorUserDetailService(userDetailServiceImpl);
		return cacheUserDetailService;
	}

	@Bean
	public IResourceService cacheResourceService() {
		CacheResourceService cacheResourceService = new CacheResourceService();
		cacheResourceService.setResourceCacheExpireMinutes(securityProperties.getResourceCacheExpireMinutes());
		IResourceService delegatorResourceService = getApplicationContext().getBean("resourceService", IResourceService.class);
		cacheResourceService.setDelegatorResourceService(delegatorResourceService);
		return cacheResourceService;
	}

	@Autowired
	private FilterInvocationSecurityMetadataSource urlAttributeMetadataSource;

	/**
	 * 加载用户所有资源
	 * 
	 * @return
	 */
	@Bean
	public FilterInvocationSecurityMetadataSource urlAttributeMetadataSource() {
		UrlAttributeMetadataSource urlAttributeMetadataSource = new UrlAttributeMetadataSource();
		urlAttributeMetadataSource.setResourceService(cacheResourceService());
		return urlAttributeMetadataSource;
	}

	/**
	 * 登录切入点
	 * 
	 * @return
	 */
	@Autowired
	@Qualifier("loginAuthenticationEntryPoint")
	private AuthenticationEntryPoint loginAuthenticationEntryPoint;

	@Bean
	public AuthenticationEntryPoint loginAuthenticationEntryPoint() {
		return new LoginAuthenticationEntryPoint(securityProperties.getLoginPage());
	}

	/**
	 * 鉴权器
	 * 
	 * @param authenticationEntryPoint
	 * @return
	 */
	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter();
		tokenAuthenticationFilter.setAuthenticationEntryPoint(loginAuthenticationEntryPoint);
		return tokenAuthenticationFilter;
	}

	/**
	 * SpringSecurity Last Filter
	 * 
	 * @param urlSecurityMetadataSource
	 * @param accessDecisionVoters
	 * @return
	 */

	@Bean
	public OverrideFilterSecurityInterceptor overrideFilterSecurityInterceptor() {
		OverrideFilterSecurityInterceptor interceptor = new OverrideFilterSecurityInterceptor();
		// 设置认证决策管理器
		interceptor.setAccessDecisionManager(urlAccessDecisionManager);
		interceptor.setSecurityMetadataSource(urlAttributeMetadataSource);
		return interceptor;
	}

	@Autowired
	private AccessDecisionManager urlAccessDecisionManager;

	@Bean
	public AccessDecisionManager urlAccessDecisionManager() {
		UrlAccessDecisionManager accessDecisionManager = new UrlAccessDecisionManager();
		// TODO
		List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
		accessDecisionVoters.add(new UrlVoter());
		accessDecisionManager.setDecisionVoters(accessDecisionVoters);
		return accessDecisionManager;
	}

	/**
	 * 用户登录拦截处理器
	 * 
	 * @return
	 * @throws Exception
	 */
	@Autowired
	@Qualifier("userLoginFilter")
	private UserFormLoginFilter userLoginFilter;

	@Bean
	public UserFormLoginFilter userLoginFilter() throws Exception {
		UserFormLoginFilter userLoginFilter = new UserFormLoginFilter(securityProperties.getLoginProcessingUrl(),
				authenticationManager());
		userLoginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authResult) throws IOException, ServletException {
				UserDetail userDetail = (UserDetail) authResult.getPrincipal();
				String jwt = Jwts.builder() //
						.claim(Constants.TENANT_ID_KEY, userDetail.getTenantId()) // 租户ID
						.claim(Constants.USER_INFO_ID_KEY, userDetail.getUserInfoId()) // 用户ID
						.setSubject(authResult.getName()) // 主体信息(账户名称)
						.setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 过期时间
						.signWith(SignatureAlgorithm.HS512, "123456") // 签名处理
						.compact();
				response.setContentType("application/json;charset=utf-8");
				Map<String, Object> datas = new LinkedHashMap<String, Object>(3);
				datas.put("code", 200);
				datas.put("msg", "登录成功");
				datas.put("token", jwt);
				PrintWriter out = response.getWriter();
				out.write(new ObjectMapper().writeValueAsString(datas));
				out.flush();
				out.close();
			}
		});
		userLoginFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				response.setContentType("application/json;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.write("登录失败!");
				out.flush();
				out.close();
			}
		});
		return userLoginFilter;
	}

	/**
	 * 加载用户信息
	 * 
	 * @return
	 */
	@Bean
	public UserDetailsService userService() {
		return new UserDetailServiceImpl(cacheUserDetailService());
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService());
		// auth.inMemoryAuthentication() //
		// .withUser("admin").password("123").roles("admin") //
		// .and() //
		// .withUser("sang").password("456").roles("user");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http. // 异常处理配置
				exceptionHandling()
				// 自定义:authenticationEntryPoint
				.authenticationEntryPoint(loginAuthenticationEntryPoint) //
				.and()
				// 表单配置
				.formLogin() //
				.loginPage(securityProperties.getLoginPage()) // 自定义登录界面
				.loginProcessingUrl(securityProperties.getLoginProcessingUrl()) // 自定义登录处理
				.and() //
				// 拦截配置
				.authorizeRequests()
				// .antMatchers("/hello").hasRole("user") //
				// .antMatchers("/admin").hasRole("admin") //
				// .antMatchers("/hello").hasAuthority("user") //
				// .antMatchers("/admin").hasAuthority("admin") //
				.antMatchers(HttpMethod.POST, securityProperties.getLoginProcessingUrl()).permitAll() //
				.antMatchers(securityProperties.getLoginPage()).permitAll() //
				.antMatchers(securityProperties.getPermitUrls()).permitAll() //
				// 其他任何请求都要验证
				.anyRequest().authenticated() //
				.and() //
				// 过滤器配置
				.addFilterAfter(overrideFilterSecurityInterceptor(), FilterSecurityInterceptor.class) // 请求URL拦截器
				// 登录处理
				.addFilterBefore(userLoginFilter, UsernamePasswordAuthenticationFilter.class) //
				// 鉴权处理
				.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) //
				.sessionManagement().disable() //
				.csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	// 开启Spring Security注解功能
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
