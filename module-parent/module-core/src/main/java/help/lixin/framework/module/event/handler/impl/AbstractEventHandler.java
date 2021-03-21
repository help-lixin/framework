package help.lixin.framework.module.event.handler.impl;

import help.lixin.framework.module.event.handler.EventHandler;
import help.lixin.framework.module.model.PluginInfo;
import help.lixin.framework.module.service.ModuleConfig;
import help.lixin.framework.module.service.ModuleLoader;
import help.lixin.framework.module.service.ModuleManager;
import help.lixin.framework.module.util.TransformToModuleConfigUtil;

public abstract class AbstractEventHandler implements EventHandler {

    protected ModuleManager moduleManager;

    protected ModuleLoader moduleLoader;


    public void setModuleManager(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    public void setModuleLoader(ModuleLoader moduleLoader) {
        this.moduleLoader = moduleLoader;
    }

    // 把PluginInfo转换成:ModuleConfig
    public ModuleConfig transformToModuleConfig(PluginInfo pluginInfo) {
        return TransformToModuleConfigUtil.transformToModuleConfig(pluginInfo);
    }
}
