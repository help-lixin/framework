package help.lixin.framework.route.util;

import help.lixin.framework.route.model.RouteInfoCollection;
import org.junit.Assert;
import org.junit.Test;

public class StringToRouteInfoCollectionTest {
    @Test
    public void testTransform(){
        String xRoute = "test-provider/127.0.0.1:8080,test-provider2/127.0.0.1:8081";
        RouteInfoCollection routeInfoCollection = StringToRouteInfoCollection.transform(xRoute);
        Assert.assertEquals(true,routeInfoCollection.isExists("test-provider"));
        Assert.assertEquals(true,routeInfoCollection.isExists("test-provider2"));
        Assert.assertEquals(false,routeInfoCollection.isExists("test-provider3"));
        System.out.println(routeInfoCollection.getRouteInfos());
    }
}
