package help.lixin.framework.route.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 路由信息集合
 */
public class RouteInfoCollection {
    private Map<String, RouteInfo> routeInfos = new HashMap<>();

    public Map<String, RouteInfo> getRouteInfos() {
        return routeInfos;
    }

    public void setRouteInfos(Map<String, RouteInfo> routeInfos) {
        if (null != routeInfos) {
            this.routeInfos = routeInfos;
        }
    }

    public boolean isExists(String serviceId) {
        return routeInfos.containsKey(serviceId);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Map<String, RouteInfo> routeInfos = new HashMap<>();

        public Builder addRouteInfo(RouteInfo routeInfo) {
            if (null != routeInfo) {
                routeInfos.put(routeInfo.getServiceId(), routeInfo);
            }
            return this;
        }

        public Builder addRouteInfos(Map<String, RouteInfo> routeInfos) {
            if (null != routeInfos) {
                routeInfos.putAll(routeInfos);
            }
            return this;
        }

        public RouteInfoCollection build() {
            RouteInfoCollection routeInfoCollection = new RouteInfoCollection();
            routeInfoCollection.setRouteInfos(this.routeInfos);
            return routeInfoCollection;
        }
    }

    @Override
    public String toString() {
        return "RouteInfoCollection{" +
                "routeInfos=" + routeInfos +
                '}';
    }
}
