package help.lixin.framework.route;

import help.lixin.framework.route.model.RouteInfo;
import org.junit.Assert;
import org.junit.Test;

public class RouteInfoTest {

    @Test
    public void testRouteInfo(){
        RouteInfo routeInfo = RouteInfo.newBuilder()
                .serviceId("test-provider")
                .address("127.0.0.1:8080")
                .build();
        Assert.assertEquals("127.0.0.1",routeInfo.getIp());
        Assert.assertEquals( 8080 ,routeInfo.getPort() );
        Assert.assertEquals(  "test-provider" , routeInfo.getServiceId() );
    }
}
