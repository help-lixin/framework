package help.lixin.framework.route;

import help.lixin.framework.route.model.RouteInfo;
import help.lixin.framework.route.model.RouteInfoCollection;
import org.junit.Assert;
import org.junit.Test;

public class RouteInfoMapTest {

    @Test
    public void testRouteInfoCollection(){
        RouteInfo routeInfo = RouteInfo.newBuilder()
                .serviceId("test-provider")
                .address("127.0.0.1:8080")
                .build();

        RouteInfoCollection routeInfoMap = RouteInfoCollection.newBuilder()
                .addRouteInfo(routeInfo)
                .build();
        Assert.assertEquals(1 , routeInfoMap.getRouteInfos().size());
        Assert.assertEquals(routeInfo , routeInfoMap.getRouteInfos().get("test-provider"));
    }
}
