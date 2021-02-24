package help.lixin.framework.route.context;

import help.lixin.framework.route.model.RouteInfoCollection;

public abstract class RouteInfoHolder {
    protected static final ThreadLocal<RouteInfoCollection> ROUTE_CONTEXT = new InheritableThreadLocal<>();

    public static void set(RouteInfoCollection routeInfoCollections) {
        ROUTE_CONTEXT.set(routeInfoCollections);
    }

    public static RouteInfoCollection get() {
        return ROUTE_CONTEXT.get();
    }

    public static boolean isEnabled() {
        RouteInfoCollection routeInfoCollection = get();
        if (null != routeInfoCollection && !routeInfoCollection.getRouteInfos().isEmpty()) {
            return true;
        }
        return false;
    }

    public static void clean() {
        ROUTE_CONTEXT.remove();
    }
}
