package help.lixin.framework.route.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;

/**
 * 路由信息
 */
public class RouteInfo implements Serializable {
    // 微服务名称
    private String serviceId;
    // ip地址
    private String ip;
    // 端口
    private int port;

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Logger logger = LoggerFactory.getLogger(RouteInfo.class);

        // 微服务名称
        private String serviceId;
        // ip地址
        private String ip;
        // 端口
        private int port;

        public Builder serviceId(String serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        /**
         * ip:port
         *
         * @param address
         */
        public Builder address(String address) {
            String[] ipAndPort = address.split(":");
            if (ipAndPort.length == 2) {
                String ip = ipAndPort[0];
                int port = Integer.parseInt(ipAndPort[1]);
                this.ip = ip;
                this.port = port;
            } else {
                logger.warn("ignore address:[{}]", address);
            }
            return this;
        }

        public RouteInfo build() {
            RouteInfo routeInfo = new RouteInfo();
            routeInfo.setServiceId(serviceId);
            routeInfo.setIp(ip);
            routeInfo.setPort(port);
            return routeInfo;
        }
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteInfo routeInfo = (RouteInfo) o;
        return serviceId.equals(routeInfo.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId);
    }

    @Override
    public String toString() {
        return "RouteInfo{" +
                "serviceId='" + serviceId + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
