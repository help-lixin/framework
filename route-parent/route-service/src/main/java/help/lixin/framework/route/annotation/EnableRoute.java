package help.lixin.framework.route.annotation;

import help.lixin.framework.route.config.RouteMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 这个注解,建议只在开发(测试)环境下使用,主要解决问题为:<br/>
 * 1. 拦截HTTP请求头中的x-route(x-route:test-provider/127.0.0.1:8081) <br/>
 * 2. 如果请求链路中有(test-provider)的请求,会被代理到:127.0.0.1:8081,而不再走负载均衡了.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RouteMarkerConfiguration.class)
public @interface EnableRoute {

}
