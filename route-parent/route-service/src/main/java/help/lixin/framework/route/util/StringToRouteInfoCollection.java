package help.lixin.framework.route.util;

import help.lixin.framework.route.model.RouteInfo;
import help.lixin.framework.route.model.RouteInfoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class StringToRouteInfoCollection {

    private static Logger logger = LoggerFactory.getLogger(StringToRouteInfoCollection.class);

    public static RouteInfoCollection transform(String xroute) {
        // "test-provider/127.0.0.1:8080,test-provider2/127.0.0.1:8081"
        RouteInfoCollection.Builder builder = RouteInfoCollection.newBuilder();
        // 1. 按","分隔.
        String[] items = xroute.split(",");
        for (String item : items) {
            // 2. 按"/"分隔
            String[] ruleItems = item.split("/");
            if (ruleItems.length == 2) {
                String serviceId = ruleItems[0];
                String address = ruleItems[1];
                if (null != serviceId && null != address) {
                    RouteInfo routeInfo = RouteInfo.newBuilder()
                            .serviceId(serviceId)
                            .address(address)
                            .build();
                    builder.addRouteInfo(routeInfo);
                } else {
                    logger.warn("ignore rule:[{}]", item);
                }
            } else {
                logger.warn("ignore rule:[{}]", item);
            }
        }
        return builder.build();
    }
}
