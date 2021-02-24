package help.lixin.framework.route.gateway.filter;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import help.lixin.framework.route.constants.Constants;
import help.lixin.framework.route.model.RouteInfo;
import help.lixin.framework.route.model.RouteInfoCollection;
import help.lixin.framework.route.util.StringToRouteInfoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

public class LoadBalancerClientExtFilter extends LoadBalancerClientFilter {
    private Logger logger = LoggerFactory.getLogger(LoadBalancerClientExtFilter.class);

    public LoadBalancerClientExtFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
        super(loadBalancer, properties);
    }

    @Override
    protected ServiceInstance choose(ServerWebExchange exchange) {
        // 1. 从Http协议头中(x-route)获得用户定义的serviceId与IP:Port的关系.
        // 2. serviceId进行比较相同的话,直接构建:RibbonServer
        // 3. serviceId不同的话,直接放过(委托给代理对象进行处理).
        String serviceId = ((URI) exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR)).getHost().toLowerCase();
        List<String> headers = exchange.getRequest().getHeaders().get(Constants.HEADER_KEY);
        if (null != headers && !headers.isEmpty()) {
            String routeString = headers.get(0);
            if (null != routeString) {
                // 转换字符串信息到业务模型,如果转换出现了问题,也不要影响业务正常流转.
                RouteInfoCollection routeInfoCollection = null;
                try {
                    routeInfoCollection = StringToRouteInfoCollection.transform(routeString);
                } catch (Throwable e) {
                    logger.warn("parse route info error:[{}]", e);
                }
                if (null != routeInfoCollection && !routeInfoCollection.getRouteInfos().isEmpty()) {
                    RouteInfo routeInfo = routeInfoCollection.getRouteInfos().get(serviceId);
                    if (null != routeInfo) {
                        InstanceInfo instanceInfo = InstanceInfo.Builder.newBuilder()
                                .setAppName(routeInfo.getServiceId())
                                .build();
                        Server server = new DiscoveryEnabledServer(instanceInfo, false);
                        server.setHost(routeInfo.getIp());
                        server.setPort(routeInfo.getPort());
                        server.setSchemea("http");
                        RibbonLoadBalancerClient.RibbonServer ribbonServer = new RibbonLoadBalancerClient.RibbonServer(serviceId, server, false, null);
                        if (logger.isDebugEnabled()) {
                            logger.debug("proxy micro service name [{}] to rule [{}]", serviceId, routeInfo);
                        }
                        return ribbonServer;
                    }
                }
            }
        }
        return super.choose(exchange);
    }
}
