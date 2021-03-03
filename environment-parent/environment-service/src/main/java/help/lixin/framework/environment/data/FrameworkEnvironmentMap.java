package help.lixin.framework.environment.data;

import java.util.HashMap;
import java.util.Map;

public class FrameworkEnvironmentMap {
    private final Map<String, Object> frameworkEnvironment = new HashMap<>();

    public static class Holder {
        private static FrameworkEnvironmentMap instance = new FrameworkEnvironmentMap();
    }

    public static FrameworkEnvironmentMap getInstance() {
        return Holder.instance;
    }

    public Map<String, Object> getFrameworkEnvironment() {
        return frameworkEnvironment;
    }

    public void remove(String key) {
        frameworkEnvironment.remove(key);
    }
}
