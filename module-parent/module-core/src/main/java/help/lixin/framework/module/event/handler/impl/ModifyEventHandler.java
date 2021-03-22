package help.lixin.framework.module.event.handler.impl;

import help.lixin.framework.module.event.handler.EventHandler;
import help.lixin.framework.module.model.PluginInfo;
import help.lixin.framework.module.service.Module;
import help.lixin.framework.module.service.ModuleConfig;
import help.lixin.framework.module.service.impl.SpringModule;

import java.nio.file.StandardWatchEventKinds;
import java.util.Optional;

public class ModifyEventHandler extends AbstractEventHandler implements EventHandler {

    @Override
    public boolean support(String eventName) {
        return StandardWatchEventKinds.ENTRY_MODIFY.name().equalsIgnoreCase(eventName);
    }

    @Override
    public void handler(PluginInfo pluginInfo) {
        // 从ModuleManager中清除(切记:不能再Hold住了,否则,会清除不干净)
        Module oldmodule = moduleManager.remove(pluginInfo.getModule(), pluginInfo.getVersion());
        Optional.ofNullable(oldmodule)
                .ifPresent(m -> {
                    // 1. 先销毁
                    m.destroy();

                    // 启用的状态下,进行注册
                    if (pluginInfo.getEnabled()) {
                        ModuleConfig moduleConfig = transformToModuleConfig(pluginInfo);
                        // 2. 重新注册
                        Module newModule = moduleLoader.load(moduleConfig);
                        moduleManager.register(newModule);
                    }
                });
    }
}
