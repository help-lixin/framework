package help.lixin.framework.module;

import java.io.Serializable;
import java.util.*;

public class Plugins implements Serializable {
    private Set<PluginDesc> plugins = new HashSet<>();

    public void addPlugin(PluginDesc plugin) {
        if (!plugins.contains(plugin)) {
            plugins.add(plugin);
        }
    }

    public Set<PluginDesc> getPlugins() {
        return plugins;
    }
}
