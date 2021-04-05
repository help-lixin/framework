package help.lixin.framework.passport.config;

import help.lixin.framework.passport.service.impl.CustomAccessDeniedHandler;
import help.lixin.framework.passport.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return new UserDetailsServiceImpl(passwordEncoder);
    }


    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http.formLogin()
                .loginProcessingUrl("/login") //登录处到的action,Spring Security会创建一个Filter对应的URL为:/login
                // 自定义登录页面
                .loginPage("/login.html")
                .usernameParameter("userName")
                .passwordParameter("pwd")
                // 默认登录成功后的处理
                .successHandler((request, response, auth) -> {
                    response.sendRedirect("/main.html");
                })
                .failureHandler((request, response, exception) -> {
                    response.sendRedirect("/fail.html");
                });

        // 授权认证
        // antMatchers ant表达式规则:
        // ?   : 表示match一个字符
        // *   : 表示match0个或多个"字符"
        // **  : 表示match0个或多个"目录"

        // regexMatchers  正则表达式:

        // permitAll               :  允许访问所有的URL
        // denyAll                 :  不允许访问所有的URL
        // anonymous               :  可以允许匿名的URL
        // authenticated           :  所有的都需要认证,才可以访问URL
        // fullyAuthenticated      :  完全认证,才可以访问URL(必须通过账号和密码登录,而通过记住我的则不能访问)
        // rememberMe              :  记住我的用户,才可以访问URL
        http.authorizeRequests()
                // 指定静态资源进行放行
                // .antMatchers("/images/*").permitAll()
                // 指定后缀进行放行
                // .antMatchers("/**/*.jpeg").permitAll()

                // 通过正则,对指定的后缀文件进行放行.
                .regexMatchers(".+[.]jpeg").permitAll()

                // login.html不需要认证
                .antMatchers("/login.html").permitAll()
                // fail.html不需要认证
                .antMatchers("/fail.html").permitAll()

                // 仅允许admin可以访问:/admin.html
                // .antMatchers("/admin.html").hasAuthority("admin")
                // .antMatchers("/admin.html").hasAuthority("admin,adminN")

                // 仅允许ROLE_admin角色可以访问:/admin.html
                // .antMatchers("/admin.html").hasAnyRole("admiN","admin")

                // 仅允许指定的IP地址访问:/admin.html
                .antMatchers("/admin.html").hasIpAddress("127.0.0.1")

                // 允许未授权界面进行访问
                .antMatchers("/access_denied.html").permitAll()
                // 其余,所有的请求都需要认证.
                // .anyRequest().authenticated();
                // 自定义access
                .anyRequest().access("@myServiceImpl.hasPermission(request,authentication)");


        // 定义退出登录信息
        http.logout().logoutSuccessUrl("/login.html");

        // 定义记住我功能
        // http.rememberMe().userDetailsService(null).tokenValiditySeconds(60).tokenRepository(null);

        // 异常处理
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);

        http.csrf().disable();
    }
}
