package help.lixin.framework.route.config;


import help.lixin.framework.route.gateway.filter.LoadBalancerClientExtFilter;
import help.lixin.framework.route.rest.interceptor.RouteRequestInterceptor;
import help.lixin.framework.route.ribbon.RibbonLoadBalancerClientProxy;
import help.lixin.framework.route.servlet.filter.RouteFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RouteConfig implements ApplicationContextAware, InitializingBean {
    private Logger logger = LoggerFactory.getLogger(RouteConfig.class);

    private ApplicationContext applicationContext;


    /**
     * 对LoadBalancerClient进行扩展.
     *
     * @param springClientFactory
     * @return
     */
    @Bean
    @ConditionalOnMissingClass("org.springframework.cloud.gateway.config.GatewayAutoConfiguration")
    public LoadBalancerClient loadBalancerClient(SpringClientFactory springClientFactory) {
        // 自定义逻辑的的:LoadBalancerClient
        RibbonLoadBalancerClientProxy proxy = new RibbonLoadBalancerClientProxy();
        // 要代理的目标对象
        RibbonLoadBalancerClient target = new RibbonLoadBalancerClient(springClientFactory);
        proxy.setProxyTarget(target);
        return proxy;
    }

    /**
     * 添加Servlet过滤器,负责把x-route信息转入到ThredLocal里.
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingClass("org.springframework.cloud.gateway.config.GatewayAutoConfiguration")
    @ConditionalOnWebApplication
    public RouteFilter routeFilter() {
        return new RouteFilter();
    }


    @Bean
    @ConditionalOnWebApplication
    @ConditionalOnMissingClass("org.springframework.cloud.gateway.config.GatewayAutoConfiguration")
    public FilterRegistrationBean registerRouteFilter(RouteFilter routeFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(routeFilter);
        registration.addUrlPatterns("/*");
        registration.setName("routeFilter");
        registration.setOrder(1);  //值越小，Filter越靠前。
        return registration;
    }

    /**
     * 要将路由信息继续往下传递
     *
     * @return
     */
    @Bean
    @ConditionalOnClass(RestTemplate.class)
    public ClientHttpRequestInterceptor routeRequestInterceptor() {
        return new RouteRequestInterceptor();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);
        if (null != restTemplate) {
            List<ClientHttpRequestInterceptor> list = new ArrayList<ClientHttpRequestInterceptor>(restTemplate.getInterceptors());
            list.add(routeRequestInterceptor());
            if (logger.isDebugEnabled()) {
                logger.debug("add [RouteRequestInterceptor] to [RestTemplate] SUCCESS");
            }
            restTemplate.setInterceptors(list);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}