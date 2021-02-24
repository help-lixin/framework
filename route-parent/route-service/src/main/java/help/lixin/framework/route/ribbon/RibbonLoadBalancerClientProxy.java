package help.lixin.framework.route.ribbon;

import java.io.IOException;
import java.net.URI;

import help.lixin.framework.route.model.RouteInfo;
import help.lixin.framework.route.context.RouteInfoHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient.RibbonServer;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

public class RibbonLoadBalancerClientProxy implements LoadBalancerClient {

    private Logger logger = LoggerFactory.getLogger(RibbonLoadBalancerClientProxy.class);

    private RibbonLoadBalancerClient ribbonLoadBalancerClient;

    public void setProxyTarget(RibbonLoadBalancerClient ribbonLoadBalancerClient) {
        this.ribbonLoadBalancerClient = ribbonLoadBalancerClient;
    }

    public RibbonLoadBalancerClient getProxyTarget() {
        return ribbonLoadBalancerClient;
    }

    public void setRibbonLoadBalancerClient(RibbonLoadBalancerClient ribbonLoadBalancerClient) {
        this.ribbonLoadBalancerClient = ribbonLoadBalancerClient;
    }

    public RibbonLoadBalancerClient getRibbonLoadBalancerClient() {
        return ribbonLoadBalancerClient;
    }

    @Override
    public ServiceInstance choose(String serviceId) {
        return ribbonLoadBalancerClient.choose(serviceId);
    }

    @Override
    public <T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException {
        if (RouteInfoHolder.isEnabled()) {
            // 1. 从ThreadLocal中获得用户定义的serviceId与IP:Port的关系.
            // 2. serviceId进行比较相同的话,直接构建:RibbonServer
            // 3. serviceId不同的话,直接放过(委托给代理对象进行处理).
            RouteInfo routeInfo = RouteInfoHolder.get().getRouteInfos().get(serviceId);
            if (null != routeInfo) {
                if (logger.isDebugEnabled()) {
                    logger.debug("proxy micro service name [{}] to rule [{}]", serviceId, routeInfo);
                }
                InstanceInfo instanceInfo = InstanceInfo.Builder.newBuilder()
                        .setAppName(routeInfo.getServiceId())
                        .build();
                Server server = new DiscoveryEnabledServer(instanceInfo, false);
                server.setHost(routeInfo.getIp());
                server.setPort(routeInfo.getPort());
                server.setSchemea("http");
                RibbonServer ribbonServer = new RibbonServer(serviceId, server, false, null);
                return this.execute(serviceId, ribbonServer, request);
            }
        }
        // 委托给代理类处理.
        return ribbonLoadBalancerClient.execute(serviceId, request);
    }

    @Override
    public <T> T execute(String serviceId, ServiceInstance serviceInstance, LoadBalancerRequest<T> request)
            throws IOException {
        return ribbonLoadBalancerClient.execute(serviceId, serviceInstance, request);
    }

    @Override
    public URI reconstructURI(ServiceInstance instance, URI original) {
        return ribbonLoadBalancerClient.reconstructURI(instance, original);
    }
}