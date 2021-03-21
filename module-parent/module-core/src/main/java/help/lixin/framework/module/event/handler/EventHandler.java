package help.lixin.framework.module.event.handler;

import help.lixin.framework.module.model.PluginInfo;

public interface EventHandler {
    boolean support(String eventName);

    void handler(PluginInfo pluginInfo);
}
