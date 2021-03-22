package help.lixin.framework.module.event.handler.impl;

import help.lixin.framework.module.event.handler.EventHandler;
import help.lixin.framework.module.model.PluginInfo;
import help.lixin.framework.module.service.Module;
import help.lixin.framework.module.service.ModuleConfig;

import java.nio.file.StandardWatchEventKinds;

public class CreateEventHandler extends AbstractEventHandler implements EventHandler {

    @Override
    public boolean support(String eventName) {
        return StandardWatchEventKinds.ENTRY_CREATE.name().equalsIgnoreCase(eventName);
    }

    @Override
    public void handler(PluginInfo pluginInfo) {
        ModuleConfig moduleConfig = transformToModuleConfig(pluginInfo);
        
        // 直接注册
        Module module = moduleLoader.load(moduleConfig);
        moduleManager.register(module);
    }
}
