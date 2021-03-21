package help.lixin.framework.module;

import help.lixin.framework.module.event.ModuleWatchEventProcess;
import help.lixin.framework.module.event.handler.EventHandler;
import help.lixin.framework.module.event.handler.impl.CreateEventHandler;
import help.lixin.framework.module.event.handler.impl.DeleteEventHandler;
import help.lixin.framework.module.event.handler.impl.ModifyEventHandler;
import help.lixin.framework.module.event.handler.impl.ReloadAllPluginEventHandler;
import help.lixin.framework.module.service.ModuleLoader;
import help.lixin.framework.module.service.ModuleManager;
import help.lixin.framework.module.service.impl.ModuleLoaderImpl;
import help.lixin.framework.module.service.impl.ModuleManagerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ModuleConfig {

    @Bean
    public ModuleLoader moduleLoader() {
        return new ModuleLoaderImpl();
    }

    @Bean
    public ModuleManager moduleManager() {
        return new ModuleManagerImpl();
    }

    @Bean
    public ModuleInitController moduleLoadInit(ModuleLoader moduleLoader, ModuleManager moduleManager) {
        ModuleInitController moduleInitController = new ModuleInitController();
        moduleInitController.setPluginsDir("/Users/lixin/FwRepository/framework/module-parent/module-example/plugins");
        moduleInitController.setModuleLoader(moduleLoader);
        moduleInitController.setModuleManager(moduleManager);
        return moduleInitController;
    }


    @Bean
    public ModulesWatchController pluginDirWatchController() {
        ModulesWatchController modulesWatchController = new ModulesWatchController();
        modulesWatchController.setPluginsDir("/Users/lixin/FwRepository/framework/module-parent/module-example/plugins");
        return modulesWatchController;
    }


    @Bean
    public ModuleWatchEventProcess moduleWatchEventProcess(ModuleManager moduleManager, List<EventHandler> eventHandlers) {
        ModuleWatchEventProcess moduleWatchEventProcess = new ModuleWatchEventProcess();
        moduleWatchEventProcess.addHandlers(eventHandlers);
        moduleWatchEventProcess.setModuleManager(moduleManager);
        return moduleWatchEventProcess;
    }


    // ********************************* defin all event *********************************
    @Bean
    public EventHandler createEventHandler(ModuleLoader moduleLoader, ModuleManager moduleManager) {
        CreateEventHandler createEventHandler = new CreateEventHandler();
        createEventHandler.setModuleLoader(moduleLoader);
        createEventHandler.setModuleManager(moduleManager);
        return createEventHandler;
    }

    @Bean
    public EventHandler modifyEventHandler(ModuleLoader moduleLoader, ModuleManager moduleManager) {
        ModifyEventHandler modifyEventHandler = new ModifyEventHandler();
        modifyEventHandler.setModuleLoader(moduleLoader);
        modifyEventHandler.setModuleManager(moduleManager);
        return modifyEventHandler;
    }

    @Bean
    public EventHandler deleteEventHandler(ModuleLoader moduleLoader, ModuleManager moduleManager) {
        DeleteEventHandler deleteEventHandler = new DeleteEventHandler();
        deleteEventHandler.setModuleLoader(moduleLoader);
        deleteEventHandler.setModuleManager(moduleManager);
        return deleteEventHandler;
    }


    @Bean
    public EventHandler reloadAllPluginEventHandler(ModuleInitController moduleInitController, ModuleLoader moduleLoader, ModuleManager moduleManager) {
        ReloadAllPluginEventHandler reloadAllPluginEventHandler = new ReloadAllPluginEventHandler();
        reloadAllPluginEventHandler.setModuleLoader(moduleLoader);
        reloadAllPluginEventHandler.setModuleManager(moduleManager);
        reloadAllPluginEventHandler.setModuleInitController(moduleInitController);
        return reloadAllPluginEventHandler;
    }
}
