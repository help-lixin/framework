package help.lixin.framework.auth.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import help.lixin.framework.auth.LoginAuthenticationEntryPoint;
import help.lixin.framework.auth.access.UrlAccessDecisionManager;
import help.lixin.framework.auth.filter.OverrideFilterSecurityInterceptor;
import help.lixin.framework.auth.filter.TokenAuthenticationFilter;
import help.lixin.framework.auth.filter.UserFormLoginFilter;
import help.lixin.framework.auth.handler.AuthenticationHandler;
import help.lixin.framework.auth.metadatasource.UrlAttributeMetadataSource;
import help.lixin.framework.auth.properties.SecurityProperties;
import help.lixin.framework.auth.service.IResourceService;
import help.lixin.framework.auth.service.IUserDetailService;
import help.lixin.framework.auth.service.impl.CacheResourceService;
import help.lixin.framework.auth.service.impl.CacheUserDetailService;
import help.lixin.framework.auth.service.impl.UserDetailServiceImpl;
import help.lixin.framework.auth.voter.PermitUrlsVoter;
import help.lixin.framework.auth.voter.UrlVoter;

@EnableConfigurationProperties(SecurityProperties.class)
@EnableWebSecurity // 这个注解必须加，开启Security
// @EnableGlobalMethodSecurity(prePostEnabled = true) // 保证post之前的注解可以使用
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private FilterInvocationSecurityMetadataSource urlAttributeMetadataSource;

	@Autowired
	@Qualifier("loginAuthenticationEntryPoint")
	private AuthenticationEntryPoint loginAuthenticationEntryPoint;

	@Autowired
	private AccessDecisionManager urlAccessDecisionManager;

	@Autowired
	@Qualifier("authenticationHandler")
	private AuthenticationHandler authenticationHandler;

	@Autowired
	@Qualifier("userLoginFilter")
	private UserFormLoginFilter userLoginFilter;

	@Bean
	public SecurityProperties securityProperties() {
		return new SecurityProperties();
	}

	/**
	 * 密码处理
	 * 
	 * @return
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	/**
	 * 用户录时,加载用户信息的具体实现
	 * 
	 * @return
	 */
	@Bean
	public IUserDetailService cacheUserDetailService() {
		CacheUserDetailService cacheUserDetailService = new CacheUserDetailService();
		cacheUserDetailService.setUserCacheExpireMinutes(securityProperties.getUserCacheExpireMinutes());
		IUserDetailService userDetailServiceImpl = getApplicationContext().getBean("userDetailService",
				IUserDetailService.class);
		cacheUserDetailService.setDelegatorUserDetailService(userDetailServiceImpl);
		return cacheUserDetailService;
	}

	/**
	 * 资源加载具体实现
	 * 
	 * @return
	 */
	@Bean
	public IResourceService cacheResourceService() {
		CacheResourceService cacheResourceService = new CacheResourceService();
		cacheResourceService.setResourceCacheExpireMinutes(securityProperties.getResourceCacheExpireMinutes());
		IResourceService delegatorResourceService = getApplicationContext().getBean("resourceService",
				IResourceService.class);
		cacheResourceService.setDelegatorResourceService(delegatorResourceService);
		return cacheResourceService;
	}

	/**
	 * 加载用户所有资源(元数据加载器)
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
	 * 对用户身份进行鉴权时,发现有错误时:进行的切入点(比如:发起重定向处理)
	 * 
	 * @return
	 */
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

	/**
	 * 匿名请求放过处理.
	 * @return
	 */
	@Bean
	public PermitUrlsVoter permitUrlsVoter() {
		PermitUrlsVoter permitUrlsVoter = new PermitUrlsVoter();
		permitUrlsVoter.setPermitUrls(securityProperties.getPermitUrls());
		return permitUrlsVoter;
	}

	/**
	 * 权限控制管理.通过AccessDecisionVoter进行投票处理
	 * 自定义:UrlVoter(通过AntPatchMatch进行URL比较).当资源拥有者的资源(URL)和请求的URL相同时,则可以调用.
	 * 
	 * @return
	 */
	@Bean
	public AccessDecisionManager urlAccessDecisionManager() {
		UrlAccessDecisionManager accessDecisionManager = new UrlAccessDecisionManager();
		// TODO
		List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
		accessDecisionVoters.add(permitUrlsVoter());
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
	@Bean
	public UserFormLoginFilter userLoginFilter() throws Exception {
		UserFormLoginFilter userLoginFilter = new UserFormLoginFilter(securityProperties.getLoginProcessingUrl(),
				authenticationManager());
		userLoginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				if (null != authenticationHandler) {
					// 回调给具体业务处理
					authenticationHandler.success(request, response, authentication);
				}
			}
		});
		userLoginFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				if (null != authenticationHandler) {
					authenticationHandler.failure(request, response, exception);
				}
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
