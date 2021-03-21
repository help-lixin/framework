package help.lixin.framework.module.event.handler.impl;

import help.lixin.framework.module.ModuleInitController;
import help.lixin.framework.module.event.handler.EventHandler;
import help.lixin.framework.module.model.PluginInfo;
import help.lixin.framework.module.model.PluginType;
import help.lixin.framework.module.service.impl.CommonModuleLoader;

public class ReloadAllPluginEventHandler extends AbstractEventHandler implements EventHandler {

    private ModuleInitController moduleInitController;

    public void setModuleInitController(ModuleInitController moduleInitController) {
        this.moduleInitController = moduleInitController;
    }

    @Override
    public boolean support(String eventName) {
        return PluginType.COMMON.name().equalsIgnoreCase(eventName);
    }

    @Override
    public void handler(PluginInfo pluginInfo) {
        // 1. 先缷载所有的插件
        moduleManager
                .getModules()
                .stream()
                .peek(module -> {
                    module.destroy();
                    // 重点:
                    // 从ModuleManager中清除(切记:不能再Hold住了,否则,会清除不干净)
                    moduleManager.remove(module.getName(), module.getVersion());
                })
                .count();

        // 2. 再缷载公共模块
        CommonModuleLoader.getInstance().destroy();

        // 3. 重新加载所有的插件
        moduleInitController.setIsRunning(false);
        moduleInitController.start();
    }
}
