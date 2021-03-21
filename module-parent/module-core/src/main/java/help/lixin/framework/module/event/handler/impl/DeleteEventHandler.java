package help.lixin.framework.module.event.handler.impl;

import help.lixin.framework.module.event.handler.EventHandler;
import help.lixin.framework.module.model.PluginInfo;
import help.lixin.framework.module.service.Module;

import java.nio.file.StandardWatchEventKinds;
import java.util.Optional;

public class DeleteEventHandler extends AbstractEventHandler implements EventHandler {
    @Override
    public boolean support(String eventName) {
        return StandardWatchEventKinds.ENTRY_DELETE.name().equalsIgnoreCase(eventName);
    }

    @Override
    public void handler(PluginInfo pluginInfo) {
        // 从ModuleManager中清除(切记:不能再Hold住了,否则,会清除不干净)
        Module oldModule = moduleManager.remove(pluginInfo.getModule(), pluginInfo.getVersion());
        Optional.ofNullable(oldModule).ifPresent(module -> {
            // 直接销毁即可
            module.destroy();
        });
    }
}
