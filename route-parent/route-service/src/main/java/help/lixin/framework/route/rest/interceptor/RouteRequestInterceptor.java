package help.lixin.framework.route.rest.interceptor;

import help.lixin.framework.route.constants.Constants;
import help.lixin.framework.route.context.XRouteHolder;
import help.lixin.framework.route.servlet.filter.RouteFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * 把线程上下文(XRouteHolder),数据取出来,继续传递
 */
public class RouteRequestInterceptor implements ClientHttpRequestInterceptor {
    private Logger logger = LoggerFactory.getLogger(RouteRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (XRouteHolder.isEnabled()) {
            String xrouteValue = XRouteHolder.get();
            request.getHeaders().add(Constants.HEADER_KEY, xrouteValue);
            if (logger.isDebugEnabled()) {
                logger.debug("add [{}:{}] to request header", Constants.HEADER_KEY, xrouteValue);
            }
        }
        return execution.execute(request, body);
    }
}
