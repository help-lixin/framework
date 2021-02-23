package help.lixin.framework.route.servlet.filter;

import help.lixin.framework.route.model.RouteInfoCollection;
import help.lixin.framework.route.constants.Constants;
import help.lixin.framework.route.context.RouteInfoHolder;
import help.lixin.framework.route.context.XRouteHolder;
import help.lixin.framework.route.util.StringToRouteInfoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 拦截请求,获取协议头中的信息
 */
public class RouteFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(RouteFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean isEnabledRouteString = false;
        boolean isEnabledRouteInfoCollection = false;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            // 格式要求: 微服务名称/IP:PORT
            // 例如: test-provider/127.0.0.1:8080,test-provider/127.0.0.1:8081
            String routeString = httpRequest.getHeader(Constants.HEADER_KEY);
            if (null != routeString) {
                // 转换字符串信息到业务模型,如果转换出现了问题,也不要影响业务正常流转.
                RouteInfoCollection routeInfoCollection = null;
                try {
                    routeInfoCollection = StringToRouteInfoCollection.transform(routeString);
                } catch (Throwable e) {
                    logger.warn("parse route info error:[{}]", e);
                }
                if (null != routeInfoCollection && !routeInfoCollection.getRouteInfos().isEmpty()) {
                    isEnabledRouteString = true;
                    isEnabledRouteInfoCollection = true;
                    // 在线程上下文中绑定业务模型信息.
                    RouteInfoHolder.set(routeInfoCollection);
                    XRouteHolder.set(routeString);

                    if (logger.isDebugEnabled()) {
                        logger.debug("bind rules:[{}] to context", routeInfoCollection);
                    }
                }
            }
        }

        try {
            chain.doFilter(request, response);
        } finally {
            if (isEnabledRouteString) {
                XRouteHolder.clean();
            }
            if (isEnabledRouteInfoCollection) {
                RouteInfoHolder.clean();
            }
        }
    }
}
